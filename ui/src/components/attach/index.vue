<template>
	<el-dialog title="选择附件" fullscreen v-model="isShowDialog">
		<div class="list-adapt-container">
			<el-upload 
				class="upload-btn"
				:action="uploadUrl"
				name="files"
				multiple
				:headers="headers"
				:show-file-list="false"
				:on-success="uploadSuccess"
				:on-exceed="onHandleExceed"
				:on-error="onHandleUploadError"
				:limit="limit">
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
	</el-dialog>
</template>

<script lang="ts">
import { toRefs, reactive, onMounted } from 'vue';
import { getAttachList } from '/@/api/attach/index';
import { ElMessage } from 'element-plus';
import { Session } from '/@/utils/storage';
export default {
	name: 'attachDialog',
	setup() {
		const state = reactive({
			isShowDialog: false,
			queryParams: {},
			showSearch: true,
			limit: 3,
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

		const openDialog = () => {
			state.isShowDialog = true;
		};
		// 关闭弹窗
		const closeDialog = () => {
			state.isShowDialog = false;
		};

		const initTableData = () => {
			getAttachList().then((res) => {
				state.tableData.data = res.data.records;
				state.tableData.total = res.data.total;
			});
		};

		const uploadSuccess = () => {
			initTableData();
		}

		const onHandleExceed = () => {
			ElMessage.error("上传文件数量不能超过 "+state.limit+" 个!");
		}
		// 上传失败
		const onHandleUploadError = () => {
			ElMessage.error("上传失败");
		}

		onMounted(() => {
			initTableData();
		});

		// 当前列表项点击
		const onTableItemClick = (v: object) => {
			
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
			openDialog,
			closeDialog,
			initTableData,
			onTableItemClick,
			onHandleSizeChange,
			onHandleCurrentChange,
			onHandleExceed,
			onHandleUploadError,
			uploadSuccess,
			...toRefs(state),
		};
	},
};
</script>

<style scoped lang="scss">
.upload-btn {
	padding-bottom: 10px;
}
.list-adapt-container {
	.attach-search {
		text-align: right;
	}
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
				}
			}
		}
	}
}
</style>