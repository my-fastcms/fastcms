<template>
	<el-dialog title="选择附件" fullscreen v-model="state.isShowDialog" :append-to-body="true">
		<div style="width:100%">			
			<el-upload 
				class="upload-btn"
				:action="state.uploadUrl"
				name="files"
				multiple
				:headers="state.headers"
				:show-file-list="false"
				:on-success="uploadSuccess"
				:on-exceed="onHandleExceed"
				:on-error="onHandleUploadError"
				:limit="state.limit">
				<el-button type="primary"><el-icon><ele-Plus /></el-icon>上传附件</el-button>
			</el-upload>
			<el-card shadow="hover">
			<template v-if="state.tableData.data.length > 0">
					<el-checkbox-group :max="state.max" v-model="state.checkedObjs" class="imgWrap">
						<div class="mb10" v-for="(v, k) in state.tableData.data" :key="k" @click="onTableItemClick(v)">
							<el-card :body-style="{ padding: '6px' }">
								<img :src="v.path" :fit="state.fit" class="image">
								<div style="padding: 14px;">
									<el-checkbox :label="v" style="width:100%">
										<el-tooltip class="item" effect="dark" :content="v.fileName" placement="top-start">
											<div class="filename">{{ v.fileName }}</div>
    									</el-tooltip>
									</el-checkbox>
								</div>
							</el-card>/
						</div>
					</el-checkbox-group>
			</template>
			<el-empty v-else description="暂无数据"></el-empty>
			<template v-if="state.tableData.data.length > 0">
				<el-pagination
					style="text-align: right"
					background
					@size-change="onHandleSizeChange"
					@current-change="onHandleCurrentChange"
					:page-sizes="[10, 20, 30]"
					:current-page="state.tableData.param.pageNum"
					:page-size="state.tableData.param.pageSize"
					layout="total, sizes, prev, pager, next, jumper"
					:total="state.tableData.total"
				>
				</el-pagination>
			</template>
			</el-card>
		</div>
		<template #footer>
				<span class="dialog-footer">
					<el-button @click="closeDialog">取 消</el-button>
					<el-button type="primary" @click="onSubmit">确 定</el-button>
				</span>
		</template>
	</el-dialog>
</template>

<script setup lang="ts" name="attachDialog">
import { reactive, onMounted } from 'vue';
import { AttachApi } from '/@/api/attach/index';
import { ClientAttachApi } from '/@/api/attach/client';
import { ElMessage } from 'element-plus';
import { Local } from '/@/utils/storage';

const props = defineProps({
	isClient: {
		type: Boolean
	},
	fileType: {
		type: String
	}
}) 

let _uploadUrl = import.meta.env.VITE_API_URL + "/admin/attachment/upload";
if(props.isClient && props.isClient == true) {
	_uploadUrl = import.meta.env.VITE_API_URL + "/client/attachment/upload";
}

const attachApi = AttachApi();
const clientAttachApi = ClientAttachApi();

const emit = defineEmits(['attachHandler']);

const state = reactive({
	fit: "fill",
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
		clientAttachApi.getAttachList(state.tableData.param).then((res) => {
			state.tableData.data = res.data.records;
			state.tableData.total = res.data.total;
		});
	} else {
		attachApi.getAttachList(state.tableData.param).then((res) => {
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
	emit("attachHandler", state.checkedObjs);
	closeDialog();
};

// 暴露变量
defineExpose({
	openDialog,
});

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
.imgWrap {
	width: 100%;
	display: grid;
	// grid-template-columns: repeat(5,1fr);
	grid-template-columns: repeat(auto-fit, minmax(260px, 1fr));
	grid-template-rows:auto;
	grid-row-gap: 10px;
	grid-column-gap: 20px;
	justify-content: space-between;
}

.filename{
	white-space:nowrap;
	overflow:hidden;
	text-overflow:ellipsis;
}
:deep .el-checkbox__label {
	white-space: nowrap;
	overflow: hidden;
	text-overflow: ellipsis;
	line-height: 30px;
}
</style>