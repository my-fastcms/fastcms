<template>
	<div class="list-adapt-container">

		<el-upload
			class="upload-demo"
			:action="uploadUrl"
			name="files"
			multiple
			:headers="headers"
			:show-file-list="false"
			:on-success="uploadSuccess"
			:limit="3">
			<el-button size="small" type="primary">上传附件</el-button>
		</el-upload>

		<el-card shadow="hover">
			<div class="flex-warp" v-if="tableData.data.length > 0">
				<el-row :gutter="15">
					<el-col :xs="24" :sm="12" :md="8" :lg="6" :xl="4" class="mb15" v-for="(v, k) in tableData.data" :key="k" @click="onTableItemClick(v)">
						<div class="flex-warp-item">
							<div class="flex-warp-item-box">
								<div class="item-img">
									<img :src="v.path" />
								</div>
								<div class="item-txt">
									<div class="item-txt-title">{{ v.fileName }}</div>
								</div>
							</div>
						</div>
					</el-col>
				</el-row>
			</div>
			<el-empty v-else description="暂无数据"></el-empty>
			<template v-if="tableData.data.length > 0">
				<el-pagination
					style="text-align: right"
					background
					@size-change="onHandleSizeChange"
					@current-change="onHandleCurrentChange"
					:page-sizes="[10, 20, 30]"
					:current-page="tableData.param.pageNum"
					:page-size="tableData.param.pageSize"
					layout="total, sizes, prev, pager, next, jumper"
					:total="tableData.total"
				>
				</el-pagination>
			</template>
		</el-card>
	</div>
</template>

<script lang="ts">
import { toRefs, reactive, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import { getAttachList } from '/@/api/attach/index';
import { Session } from '/@/utils/storage';
export default {
	name: 'attachManager',
	setup() {
		const router = useRouter();
		const state = reactive({
			uploadUrl: import.meta.env.VITE_API_URL + "/admin/attachment/upload",
			headers: {"Authorization": Session.get('token')},
			tableData: {
				data: [],
				total: 99,
				loading: false,
				param: {
					pageNum: 1,
					pageSize: 10,
				},
			},
		});

		const initTableData = () => {
			getAttachList().then((res) => {
				state.tableData.data = res.data.records;
				state.tableData.total = res.data.total;
			});
		};

		const uploadSuccess = () => {
			initTableData();
		}

		onMounted(() => {
			initTableData();
		});

		// 当前列表项点击
		const onTableItemClick = (v: object) => {
			router.push({
				path: '/pages/filteringDetails',
				query: { id: v.id },
			});
		};
		// 分页点击
		const onHandleSizeChange = (val: number) => {
			state.tableData.param.pageSize = val;
		};
		// 分页点击
		const onHandleCurrentChange = (val: number) => {
			state.tableData.param.pageNum = val;
		};
		return {
			initTableData,
			onTableItemClick,
			onHandleSizeChange,
			onHandleCurrentChange,
			uploadSuccess,
			...toRefs(state),
		};
	},
};
</script>

<style scoped lang="scss">
.upload-demo {
	padding-bottom: 10px;
}
.flex-warp {
	display: flex;
	flex-wrap: wrap;
	align-content: flex-start;
	margin: 0 -5px;
	.flex-warp-item {
		padding: 5px;
		width: 100%;
		.flex-warp-item-box {
			border: 1px solid #ebeef5;
			width: 100%;
			height: 100%;
			border-radius: 2px;
			display: flex;
			flex-direction: column;
			transition: all 0.3s ease;
			&:hover {
				cursor: pointer;
				border: 1px solid var(--color-primary);
				transition: all 0.3s ease;
				box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.03);
				.item-txt-title {
					color: var(--color-primary) !important;
					transition: all 0.3s ease;
				}
				.item-img {
					img {
						transition: all 0.3s ease;
						transform: translateZ(0) scale(1.05);
					}
				}
			}
			.item-img {
				width: 100%;
				height: 215px;
				overflow: hidden;
				img {
					transition: all 0.3s ease;
					width: 100%;
					height: 100%;
				}
			}
			.item-txt {
				flex: 1;
				padding: 15px;
				display: flex;
				flex-direction: column;
				overflow: hidden;
				.item-txt-title {
					text-overflow: ellipsis;
					overflow: hidden;
					-webkit-line-clamp: 2;
					-webkit-box-orient: vertical;
					display: -webkit-box;
					color: #666666;
					transition: all 0.3s ease;
					&:hover {
						color: var(--color-primary);
						text-decoration: underline;
						transition: all 0.3s ease;
					}
				}
				.item-txt-other {
					flex: 1;
					align-items: flex-end;
					display: flex;
					.item-txt-msg {
						font-size: 12px;
						color: #8d8d91;
					}
					.item-txt-price {
						display: flex;
						justify-content: space-between;
						align-items: center;
						.font-price {
							color: #ff5000;
							.font {
								font-size: 22px;
							}
						}
					}
				}
			}
		}
	}
}
</style>