<template>
	<div class="home-container">
		<el-row :gutter="15">
			<el-col :sm="6" class="mb15">
				<div class="home-card-item home-card-first">
					<div class="flex-margin flex">
						<img :src="getUserInfos.photo" />
						<div class="home-card-first-right ml15">
							<div class="flex-margin">
								<div class="home-card-first-right-title">
									{{ currentTime }}，{{ getUserInfos.username === '' ? '游客' : getUserInfos.username }}！
								</div>
								<div class="home-card-first-right-msg mt5">{{ getUserInfos.username === 'admin' ? '超级管理' : '' }}</div>
							</div>
						</div>
					</div>
				</div>
			</el-col>
			<el-col :sm="6" class="mb15" v-for="(v, k) in topCardItemList" :key="k">
				<div class="home-card-item home-card-item-box" :style="{ background: v.color }">
					<div class="home-card-item-flex">
						<div class="home-card-item-title pb3">{{ v.title }}</div>
						<div class="home-card-item-title-num pb6" :id="`titleNum${k + 1}`"></div>
						<div class="home-card-item-tip pb3">{{ v.tip }}</div>
						<div class="home-card-item-tip-num" :id="`tipNum${k + 1}`"></div>
					</div>
					<i :class="v.icon" :style="{ color: v.iconColor }"></i>
				</div>
			</el-col>
		</el-row>
		<el-row :gutter="15">
			<el-col :xs="24" :sm="14" :md="14" :lg="16" :xl="16" class="home-warning-media">
				<el-card shadow="hover" :header="$t('message.card.title3')" class="home-warning-card">
					<el-table :data="articleListData" style="width: 100%;height: 300px;" stripe>
						<el-table-column prop="title" min-width="80%" :label="$t('message.table.th1')">
						<template #default="scope">
							<a :href="scope.row.url" target="_blank">
								{{scope.row.title}}
							</a></template>
						</el-table-column>
						<el-table-column prop="viewCount" min-width="20%" :label="$t('message.table.th2')"></el-table-column>
					</el-table>
				</el-card>
			</el-col>
			<el-col :xs="24" :sm="10" :md="10" :lg="8" :xl="8" class="home-dynamic-media">
				<el-card shadow="hover" :header="$t('message.card.title4')">
					<div class="home-dynamic">
						<el-scrollbar>
							<div class="home-dynamic-item" v-for="(v, k) in activitiesList" :key="k">
								<div class="home-dynamic-item-left">
									<div class="home-dynamic-item-left-time1 mb5">{{ v.time1 }}</div>
									<div class="home-dynamic-item-left-time2">{{ v.time2 }}</div>
								</div>
								<div class="home-dynamic-item-line">
									<i class="iconfont icon-fangkuang"></i>
								</div>
								<div class="home-dynamic-item-right">
									<div class="home-dynamic-item-right-title mb5">
										<i class="el-icon-s-comment"></i>
										<span>{{ v.title }}</span>
									</div>
									<div class="home-dynamic-item-right-label">{{ v.label }}</div>
								</div>
							</div>
						</el-scrollbar>
					</div>
				</el-card>
			</el-col>
		</el-row>
	</div>
</template>

<script lang="ts">
import { toRefs, reactive, onMounted, nextTick, computed, getCurrentInstance, watch, onActivated } from 'vue';
import { CountUp } from 'countup.js';
import { formatAxis } from '/@/utils/formatTime';
import { useStore } from '/@/store/index';
import { getIndexData } from '/@/api/home/index';
import { topCardItemList, activitiesList } from './data';
export default {
	name: 'home',
	setup() {
		const { proxy } = getCurrentInstance() as any;
		const store = useStore();
		const state = reactive({
			topCardItemList,
			activitiesList,
			articleListData: [],
			myCharts: [],
		});
		// 获取用户信息 vuex
		const getUserInfos = computed(() => {
			return store.state.userInfos.userInfos;
		});
		// 当前时间提示语
		const currentTime = computed(() => {
			return formatAxis(new Date());
		});
		
		// 批量设置 echarts resize
		const initEchartsResizeFun = () => {
			nextTick(() => {
				for (let i = 0; i < state.myCharts.length; i++) {
					state.myCharts[i].resize();
				}
			});
		};
		// 批量设置 echarts resize
		const initEchartsResize = () => {
			window.addEventListener('resize', initEchartsResizeFun);
		};

		const getHomeData = () => {
			getIndexData().then((res) => {
				let articleStat = res.data.articleStatData;
				let orderStat = res.data.orderStatData;
				new CountUp('titleNum1', 0).update(articleStat.todayCount);
				new CountUp('titleNum2', 0).update(articleStat.todayViewCount);
				new CountUp('titleNum3', 0).update(orderStat.todayCount);
				new CountUp('tipNum1', 0).update(articleStat.totalCount);
				new CountUp('tipNum2', 0).update(articleStat.totalViewCount);
				new CountUp('tipNum3', 0).update(orderStat);
				state.articleListData = res.data.newArticleList;
			});
		}
		// 页面加载时
		onMounted(() => {
			getHomeData();
			initEchartsResize();
		});
		// 由于页面缓存原因，keep-alive
		onActivated(() => {
			initEchartsResizeFun();
		});
		// 监听 vuex 中的 tagsview 开启全屏变化，重新 resize 图表，防止不出现/大小不变等
		watch(
			() => store.state.tagsViewRoutes.isTagsViewCurrenFull,
			() => {
				initEchartsResizeFun();
			}
		);
		return {
			getUserInfos,
			currentTime,
			...toRefs(state),
		};
	},
};
</script>

<style scoped lang="scss">
.home-container {
	overflow-x: hidden;
	.home-card-item {
		width: 100%;
		height: 103px;
		background: var(--el-text-color-secondary);
		border-radius: 4px;
		transition: all ease 0.3s;
		&:hover {
			box-shadow: 0 2px 12px 0 rgb(0 0 0 / 10%);
			transition: all ease 0.3s;
		}
	}
	.home-card-item-box {
		display: flex;
		align-items: center;
		position: relative;
		overflow: hidden;
		&:hover {
			i {
				right: 0px !important;
				bottom: 0px !important;
				transition: all ease 0.3s;
			}
		}
		i {
			position: absolute;
			right: -10px;
			bottom: -10px;
			font-size: 70px;
			transform: rotate(-30deg);
			transition: all ease 0.3s;
		}
		.home-card-item-flex {
			padding: 0 20px;
			color: var(--color-whites);
			.home-card-item-title,
			.home-card-item-tip {
				font-size: 13px;
			}
			.home-card-item-title-num {
				font-size: 18px;
			}
			.home-card-item-tip-num {
				font-size: 13px;
			}
		}
	}
	.home-card-first {
		background: var(--el-color-white);
		border: 1px solid var(--el-border-color-light, #ebeef5);
		display: flex;
		align-items: center;
		img {
			width: 60px;
			height: 60px;
			border-radius: 100%;
			border: 2px solid var(--color-primary-light-5);
		}
		.home-card-first-right {
			flex: 1;
			display: flex;
			flex-direction: column;
			.home-card-first-right-title {
				color: var(--el-color-black);
			}
			.home-card-first-right-msg {
				font-size: 13px;
				color: var(--el-text-color-secondary);
			}
		}
	}
	.home-monitor {
		height: 200px;
		.flex-warp-item {
			width: 50%;
			height: 100px;
			display: flex;
			.flex-warp-item-box {
				margin: auto;
				height: auto;
				text-align: center;
				color: var(--el-text-color-primary);
			}
		}
	}
	.home-warning-card {
		height: 292px;
		::v-deep(.el-card) {
			height: 100%;
		}
	}
	.home-dynamic {
		height: 200px;
		.home-dynamic-item {
			display: flex;
			width: 100%;
			height: 60px;
			overflow: hidden;
			&:first-of-type {
				.home-dynamic-item-line {
					i {
						color: orange !important;
					}
				}
			}
			.home-dynamic-item-left {
				text-align: right;
				.home-dynamic-item-left-time1 {
				}
				.home-dynamic-item-left-time2 {
					font-size: 13px;
					color: var(--el-text-color-secondary);
				}
			}
			.home-dynamic-item-line {
				height: 60px;
				border-right: 2px dashed var(--el-border-color-light, #ebeef5);
				margin: 0 20px;
				position: relative;
				i {
					color: var(--color-primary);
					font-size: 12px;
					position: absolute;
					top: 1px;
					left: -6px;
					transform: rotate(46deg);
					background: var(--el-color-white);
				}
			}
			.home-dynamic-item-right {
				flex: 1;
				.home-dynamic-item-right-title {
					i {
						margin-right: 5px;
						border: 1px solid var(--el-border-color-light, #ebeef5);
						width: 20px;
						height: 20px;
						border-radius: 100%;
						padding: 3px 2px 2px;
						text-align: center;
						color: var(--color-primary);
					}
				}
				.home-dynamic-item-right-label {
					font-size: 13px;
					color: var(--el-text-color-secondary);
				}
			}
		}
	}
}
</style>
