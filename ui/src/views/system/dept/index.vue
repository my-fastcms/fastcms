<template>
	<div>
		<el-card shadow="hover">
			<div class="system-dept-search mb15">
				<el-input size="default" placeholder="请输入部门名称" style="max-width: 180px"> </el-input>
				<el-button size="default" type="primary" class="ml10">
					<el-icon><ele-Search /></el-icon>查询
				</el-button>
				<el-button size="default" type="primary" class="ml10" @click="onOpenAddDept('add')">
					<el-icon><ele-Plus /></el-icon>新增部门
				</el-button>
			</div>
			<el-table
				:data="state.tableData.data"
				v-loading="state.tableData.loading"
				style="width: 100%"
				row-key="id"
				default-expand-all
				:tree-props="{ children: 'children', hasChildren: 'hasChildren' }"
			>
				<el-table-column prop="deptName" label="部门名称" show-overflow-tooltip></el-table-column>
				<el-table-column prop="sortNum" label="排序" show-overflow-tooltip width="80"></el-table-column>
				<el-table-column prop="status" label="部门状态" show-overflow-tooltip>
					<template #default="scope">
						<el-tag type="success" v-if="scope.row.status == 1">启用</el-tag>
						<el-tag type="info" v-else>禁用</el-tag>
					</template>
				</el-table-column>
				<el-table-column prop="deptDesc" label="部门描述" show-overflow-tooltip></el-table-column>
				<el-table-column prop="created" label="创建时间" show-overflow-tooltip></el-table-column>
				<el-table-column label="操作" show-overflow-tooltip width="140">
					<template #default="scope">
						<el-button size="small" text type="primary" @click="onOpenEditDept('add', scope.row)">新增</el-button>
						<el-button size="small" text type="primary" @click="onOpenEditDept('edit', scope.row)">修改</el-button>
						<el-button size="small" text type="primary" @click="onTabelRowDel(scope.row)">删除</el-button>
					</template>
				</el-table-column>
			</el-table>
		</el-card>
		<DeptDialog ref="deptDialogRef" :deptParentData="state.tableData.data" @refresh="getTableData()"/>
	</div>
</template>

<script setup lang="ts" name="systemDept">
import { defineAsyncComponent, ref, reactive, onMounted } from 'vue';
import { ElMessageBox, ElMessage } from 'element-plus';

import { DeptApi } from '/@/api/dept/index';

// 引入组件
const DeptDialog = defineAsyncComponent(() => import('/@/views/system/dept/dialog.vue'));

const deptApi = DeptApi();

// 定义变量内容
const deptDialogRef = ref();
const state = reactive({
	tableData: {
		data: [] as DeptTreeType[],
		total: 0,
		loading: false,
		param: {
			pageNum: 1,
			pageSize: 10,
		},
	},
});

// 初始化表格数据
const getTableData = () => {
	state.tableData.loading = true;
	deptApi.getDeptList().then(res => {
		state.tableData.data = res.data;
		state.tableData.loading = false;
		state.tableData.total = state.tableData.data.length;
	})
};
// 打开新增菜单弹窗
const onOpenAddDept = (type: string) => {
	deptDialogRef.value.openDialog(type);
};
// 打开编辑菜单弹窗
const onOpenEditDept = (type: string, row: DeptTreeType) => {
	deptDialogRef.value.openDialog(type, row);
};
// 删除当前行
const onTabelRowDel = (row: DeptTreeType) => {
	ElMessageBox.confirm(`此操作将永久删除部门：${row.deptName}, 是否继续?`, '提示', {
		confirmButtonText: '删除',
		cancelButtonText: '取消',
		type: 'warning',
	})
		.then(() => {
			deptApi.delDept(row.id).then(() => {
				getTableData();
				ElMessage.success('删除成功');
			})
		})
		.catch(() => {});
};
// 页面加载时
onMounted(() => {
	getTableData();
});
</script>