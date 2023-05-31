<template>
	<div>
		<el-card shadow="hover">
			<div class="mb15">
				<!-- <el-input size="default" placeholder="请输入菜单名称" style="max-width: 180px"> </el-input>
				<el-button size="default" type="primary" class="ml10">
					<el-icon><ele-Search /></el-icon>
					查询
				</el-button> -->
				<el-button size="default" type="primary" class="ml10" @click="onOpenAddMenu()">
					<el-icon><ele-Plus /></el-icon> 新增菜单
				</el-button>
			</div>
			<el-table
				:data="state.tableData.data"
				v-loading="state.tableData.loading"
				style="width: 100%"
				row-key="path"
				:tree-props="{ children: 'children', hasChildren: 'hasChildren' }"
			>
				<el-table-column label="菜单名称" show-overflow-tooltip>
					<template #default="scope">
						<SvgIcon :name="scope.row.meta.icon" />
						<span class="ml10">{{ $t(scope.row.meta.title) }}</span>
					</template>
				</el-table-column>
				<el-table-column prop="path" label="路由路径" show-overflow-tooltip></el-table-column>
				<el-table-column label="组件路径" show-overflow-tooltip>
					<template #default="scope">
						<span>{{ scope.row.component }}</span>
					</template>
				</el-table-column>
				<el-table-column prop="sortNum" label="排序" show-overflow-tooltip width="80"></el-table-column>
				<el-table-column label="操作" show-overflow-tooltip width="140">
					<template #default="scope">
						<el-button size="small" text type="primary" @click="onOpenMenuDialog('add', scope.row)">新增</el-button>
						<el-button size="small" text type="primary" @click="onOpenMenuDialog('edit', scope.row)">修改</el-button>
						<el-button size="small" text type="primary" @click="onTabelRowDel(scope.row)">删除</el-button>
					</template>
				</el-table-column>
			</el-table>
		</el-card>
		<MenuDialog ref="menuDialogRef" @refresh="getTableData()" />
	</div>
</template>

<script setup lang="ts" name="systemMenu">
import { defineAsyncComponent, ref, onMounted, reactive } from 'vue';
import { _RouteRecordBase } from 'vue-router';
import { ElMessageBox, ElMessage } from 'element-plus';
// import { storeToRefs } from 'pinia';
// import { useRoutesList } from '/@/stores/routesList';
import { MenuApi } from '/@/api/menu/index';
// import { setBackEndControlRefreshRoutes } from "/@/router/backEnd";

declare interface FastcmsRouteRecordRaw extends _RouteRecordBase {
	id: string
}

const menuApi = MenuApi();
// 引入组件
const MenuDialog = defineAsyncComponent(() => import('/@/views/system/menu/dialog.vue'));

// 定义变量内容
// const stores = useRoutesList();
// const { routesList } = storeToRefs(stores);
const menuDialogRef = ref();
const state = reactive({
	tableData: {
		data: [] as FastcmsRouteRecordRaw[],
		loading: true,
	},
});

// 获取路由数据，真实请从接口获取
const getTableData = () => {
	state.tableData.loading = true;
	menuApi.getMenuList().then(res => {
		state.tableData.data = res.data;
		state.tableData.loading = false;
	})
};

const onOpenAddMenu = () => {
	menuDialogRef.value.openDialog("add", null);
}
// 打开新增编辑菜单弹窗
const onOpenMenuDialog = (type: string, row: FastcmsRouteRecordRaw) => {
	menuDialogRef.value.openDialog(type, row);
};
// 删除当前行
const onTabelRowDel = (row: FastcmsRouteRecordRaw) => {
	ElMessageBox.confirm(`此操作将永久删除路由：${row.path}, 是否继续?`, '提示', {
		confirmButtonText: '删除',
		cancelButtonText: '取消',
		type: 'warning',
	})
		.then(() => {
			menuApi.delMenu(row.id).then(() => {
				// console.log(res);
				ElMessage.success('删除成功');
				getTableData();
			}).catch((error) => {
				ElMessage.error(error.message);
			});
			//await setBackEndControlRefreshRoutes() // 刷新菜单，未进行后端接口测试
		})
		.catch(() => {});
};
// 页面加载时
onMounted(() => {
	getTableData();
});
</script>