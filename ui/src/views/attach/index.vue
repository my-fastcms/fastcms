<template>
	<div class="list-adapt-container">
		<el-card shadow="hover">
			<el-upload 
				ref="uploadRef"
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
			<div v-if="state.tableData.data.length > 0">
				<el-row :gutter="15">
					<el-col :xs="24" :sm="12" :md="8" :lg="6" :xl="4" class="" v-for="(v, k) in state.tableData.data" :key="k" @click="onTableItemClick(v)">
						<el-card :body-style="{ padding: '0px', width:120 }">
							<img :src="v.typePath" :fit="state.fit" class="image">
							<div style="padding: 14px;">
								<span>{{ v.fileName }}</span>
							</div>
						</el-card>
					</el-col>
				</el-row>
			</div>
			<el-empty v-else description="暂无数据"></el-empty>
			<template v-if="state.tableData.data.length > 0">
				<el-pagination
					style="text-align: right; padding: 15px;"
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
		<Detail ref="detailRef" @reloadTable="initTableData"/>
	</div>
</template>

<script setup lang="ts" name="attachManager">
import { ref, reactive, onMounted } from 'vue';
import { AttachApi } from '/@/api/attach/index';
import { ElMessage } from 'element-plus';
import { Local } from '/@/utils/storage';
import Detail from '/@/views/attach/detail.vue';

const attachApi = AttachApi();
const detailRef = ref();
const uploadRef = ref();
const state = reactive({
	fit: "fill",
	queryParams: {},
	showSearch: true,
	limit: 5,
	uploadUrl: import.meta.env.VITE_API_URL + "/admin/attachment/upload",
	headers: {"Authorization": Local.get('token')},
	tableData: {
		data: [],
		total: 0,
		loading: false,
		param: {
			pageNum: 1,
			pageSize: 10,
		},
	},
});

const initTableData = () => {
	attachApi.getAttachList(state.tableData.param).then((res) => {
		state.tableData.data = res.data.records;
		state.tableData.total = res.data.total;
	});
};

const uploadSuccess = (res: any) => {
	if (res.code == 200) {
		uploadRef.value!.clearFiles();
		initTableData();
	}else {
		ElMessage.error(res.message);	
	}
	
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
	detailRef.value.openDialog(v.id);
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
