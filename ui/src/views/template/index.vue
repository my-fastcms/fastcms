<template>
	<div>
		<el-card shadow="hover">
			<div class="mb15">
				<el-upload 
					:action="state.uploadUrl"
					name="file"
					:headers="state.headers"
					:show-file-list="false"
					:on-success="uploadSuccess"
					:on-error="onHandleUploadError"
					:on-exceed="onHandleExceed"
					accept=".zip">
					<el-button class="mt15" type="primary" size="default"><el-icon><ele-Plus /></el-icon>安装模板</el-button>
				</el-upload>
			</div>
			<el-table :data="state.tableData" stripe style="width: 100%">
				<el-table-column prop="id" label="ID" show-overflow-tooltip></el-table-column>
				<el-table-column prop="name" label="模板名称" show-overflow-tooltip></el-table-column>
				<el-table-column prop="path" label="模板路径" show-overflow-tooltip></el-table-column>
				<el-table-column prop="version" label="版本" show-overflow-tooltip></el-table-column>
				<el-table-column prop="provider" label="作者" show-overflow-tooltip></el-table-column>
				<el-table-column prop="description" label="描述" show-overflow-tooltip></el-table-column>
				<el-table-column label="操作" width="160">
					<template #default="scope">
						<el-tag v-if="scope.row.active == true" type="success">使用中</el-tag>
						<el-button v-if="scope.row.active == false" size="small" text type="primary" @click="onRowEnable(scope.row)">启用</el-button>
						<el-button v-if="scope.row.active == false" size="small" text type="primary" @click="onRowUnInstall(scope.row)">卸载</el-button>
					</template>
				</el-table-column>
			</el-table>
		</el-card>
	</div>
</template>

<script lang="ts" name="articleManager" setup>
import { ElMessageBox, ElMessage } from 'element-plus';
import { reactive, onMounted } from 'vue';
import { TemplateApi } from '/@/api/template/index';
import { Local } from '/@/utils/storage';

const templateApi = TemplateApi();
const state = reactive({
	tableData: [],
	limit: 1,
	uploadUrl: import.meta.env.VITE_API_URL + "/admin/template/install",
	headers: {"Authorization": Local.get('token')},
});

// 初始化表格数据
const initTableData = () => {
	templateApi.getTemplateList().then((res) => {
		state.tableData = res.data;
	}).catch(() => {
	})
};
// 当前行删除
const onRowUnInstall = (row: object) => {
	ElMessageBox.confirm('此操作将卸载此['+row.id+']模板, 是否继续?', '提示', {
		confirmButtonText: '卸载',
		cancelButtonText: '取消',
		type: 'warning',
	}).then(() => {
		templateApi.unInstallTemplate(row.id).then(() => {
			ElMessage.success("卸载成功");
			initTableData();
		}).catch((res) => {
			ElMessage.error(res.message);
		})
	})
	.catch(() => {
		
	});
};
const onRowEnable = (row: object) => {
	ElMessageBox.confirm('确认启用['+row.id+']模板吗?', '提示', {
		confirmButtonText: '启用',
		cancelButtonText: '取消',
		type: 'warning',
	}).then(() => {
		templateApi.enableTemplate(row.id).then(() => {
			ElMessage.success("启用成功");
			initTableData();
		}).catch((res) => {
			ElMessage.error(res.message);
		})
	})
	.catch(() => {
		
	});
}

const uploadSuccess = (res :any) => {
	if(res.code == 200) {
		ElMessage.success("上传成功");
		initTableData();
	}else {
		ElMessage.error(res.message);
	}
}
const onHandleUploadError = (error: any) => {
	ElMessage.error("上传失败," + error);
}
const onHandleExceed = () => {

}

// 页面加载时
onMounted(() => {
	initTableData();
});

</script>

