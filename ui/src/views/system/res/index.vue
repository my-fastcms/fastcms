<template>
	<div class="system-user-container">
		<el-card shadow="hover">
			<div class="system-role-search mb15">
				<el-button @click="onSyncRes" class="mt15" size="default" type="primary"><el-icon><ele-Plus /></el-icon>同步资源</el-button>
			</div>
			<el-table :data="state.tableData.data" stripe style="width: 100%">
				<el-table-column prop="resourceName" label="资源名称" show-overflow-tooltip></el-table-column>
				<el-table-column prop="resourcePath" label="权限标识" show-overflow-tooltip></el-table-column>
				<el-table-column prop="created" label="同步时间" show-overflow-tooltip></el-table-column>
			</el-table>
		</el-card>
	</div>
</template>

<script setup lang="ts" name="systemRes">
import { ElMessageBox, ElMessage } from 'element-plus';
import { reactive, onMounted } from 'vue';
import { ResApi } from '/@/api/res/index';

const resApi = ResApi();

const state: any = reactive({
tableData: {
	data: [],
	loading: false,
},
});

const onSyncRes = () => {
	ElMessageBox.confirm('此操作将覆盖所有资源数据, 是否继续?', '提示', {
		confirmButtonText: '确认',
		cancelButtonText: '取消',
		type: 'warning',
	}).then(() => {
		resApi.syncRes().then(() => {
			initTableData();
			ElMessage.success("同步成功");
		})
	}).catch(()=> {})
};

// 初始化表格数据
const initTableData = () => {
	resApi.getResList(state.tableData.param).then((res) => {
		state.tableData.data = res.data;
	}).catch(() => {
	})
};
// 页面加载时
onMounted(() => {
	initTableData();
});

</script>

<style scoped lang="scss">
.system-user-container {
	.system-user-search {
		text-align: right;
	}
	.system-user-photo {
		width: 40px;
		height: 40px;
		border-radius: 100%;
	}
}
</style>
