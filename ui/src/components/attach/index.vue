<template>
	<el-dialog title="选择附件" fullscreen v-model="isShowDialog">
		<div>
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
				<div v-if="tableData.data.length > 0">
					<el-row :gutter="15">
						<el-checkbox-group :max="max" v-model="checkedObjs">
							<el-col :xs="24" :sm="12" :md="8" :lg="6" :xl="4" class="mb15" v-for="(v, k) in tableData.data" :key="k" @click="onTableItemClick(v)">
								<el-card :body-style="{ padding: '0px' }">
									<img :src="v.path" class="image">
									<div style="padding: 14px;">
										<el-checkbox :label="v"><span>{{ v.fileName }}</span></el-checkbox>
									</div>
								</el-card>
							</el-col>
						</el-checkbox-group>
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
		<template #footer>
				<span class="dialog-footer">
					<el-button @click="closeDialog" size="small">取 消</el-button>
					<el-button type="primary" @click="onSubmit" size="small">确 定</el-button>
				</span>
			</template>
	</el-dialog>
</template>

<script lang="ts">
import { toRefs, reactive, onMounted } from 'vue';
import { getAttachList } from '/@/api/attach/index';
import { getAttachList as getClientAttachList } from '/@/api/attach/client';
import { ElMessage } from 'element-plus';
import { Local, Session } from '/@/utils/storage';
export default {
	emits: ["attachHandler"],
	name: 'attachDialog',
	props: {
		fileType: String,
		isClient: {
			type: Boolean,
			default: false
		},
	},
	setup(props, ctx) {

		let _uploadUrl = import.meta.env.VITE_API_URL + "/admin/attachment/upload";
		if(props.isClient && props.isClient == true) {
			_uploadUrl = import.meta.env.VITE_API_URL + "/client/attachment/upload";
		}

		const state = reactive({
			isShowDialog: false,
			queryParams: {},
			showSearch: true,
			max: 1,
			limit: 3,
			uploadUrl: _uploadUrl,
			headers: {"Authorization": Local.get('token')},
			checkedObjs: [],	//选中的图片元素
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

		const openDialog = (max: number) => {
			state.isShowDialog = true;
			state.max = max;
		};
		// 关闭弹窗
		const closeDialog = () => {
			state.isShowDialog = false;
			state.max = 1;
		};

		const initTableData = () => {
			if(props.fileType) {
				console.log("==>fileType:" + props.fileType);
				state.tableData.param.fileType = props.fileType
			}
			
			if(props.isClient && props.isClient == true) {
				getClientAttachList(state.tableData.param).then((res) => {
					state.tableData.data = res.data.records;
					state.tableData.total = res.data.total;
				});
			} else {
				getAttachList(state.tableData.param).then((res) => {
					state.tableData.data = res.data.records;
					state.tableData.total = res.data.total;
				});
			}

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
			console.log(v);
		};
		// 分页点击
		const onHandleSizeChange = (val: number) => {
			state.tableData.param.pageSize = val;
			initTableData();
		};
		// 分页点击
		const onHandleCurrentChange = (val: number) => {
			state.tableData.param.pageNum = val;
			initTableData();
		};
		
		const onSubmit = () => {
			//把选中的附件传递给父组件
			ctx.emit("attachHandler", state.checkedObjs);
			closeDialog();
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
			onSubmit,
			...toRefs(state),
		};
	},
};
</script>

<style scoped lang="scss">
.upload-btn {
	padding-bottom: 10px;
}
.bottom {
    margin-top: 13px;
    line-height: 12px;
}

.button {
	padding: 0;
	float: right;
}

.image {
    width: 100%;
	height: 200px;
    display: block;
}

.clearfix:before,
.clearfix:after {
    display: table;
    content: "";
}
  
.clearfix:after {
	clear: both
}
</style>