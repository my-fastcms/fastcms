<template>
	<div class="system-role-container">
		<el-card shadow="hover">
			<div class="system-role-search mb15">
				<el-button @click="onOpenAddRole" class="mt15" size="small" type="primary" icon="iconfont icon-shuxingtu">新建角色</el-button>
			</div>
			<el-table :data="tableData.data" stripe style="width: 100%">
				<el-table-column prop="roleName" label="角色名" show-overflow-tooltip></el-table-column>
				<el-table-column prop="roleDesc" label="描述" show-overflow-tooltip></el-table-column>
				<el-table-column prop="created" label="创建时间" show-overflow-tooltip></el-table-column>
				<el-table-column prop="path" label="操作" width="200">
					<template #default="scope">
						<el-button v-if="scope.row.id != 1" size="mini" type="text" @click="onOpenAddRole(scope.row)">修改</el-button>
						<el-button v-if="scope.row.id != 1" size="mini" type="text" @click="onOpenAddPermission(scope.row)">权限设置</el-button>
						<el-button v-if="scope.row.id != 1" size="mini" type="text" @click="onRowDel(scope.row)">删除</el-button>
					</template>
				</el-table-column>
			</el-table>
			<el-pagination
				@size-change="onHandleSizeChange"
				@current-change="onHandleCurrentChange"
				class="mt15"
				:pager-count="5"
				:page-sizes="[10, 20, 30]"
				v-model:current-page="tableData.param.pageNum"
				background
				v-model:page-size="tableData.param.pageSize"
				layout="total, sizes, prev, pager, next, jumper"
				:total="tableData.total"
			>
			</el-pagination>
		</el-card>
		<AddRole ref="addRoleRef" @reloadTable="initTableData"/>
		<AddPermission ref="addPermissionRef"/>
	</div>
</template>

<script lang="ts">
import { toRefs, reactive, onMounted, ref } from 'vue';
import { ElMessageBox, ElMessage } from 'element-plus';
import { getRoleList, delRole } from '/@/api/role/index';
import AddRole from '/@/views/system/role/component/addRole.vue';
import AddPermission from '/@/views/system/role/component/addPermission.vue';

export default {
	name: 'systemRole',
	components: { AddRole, AddPermission },
	setup() {
		const addRoleRef = ref();
		const addPermissionRef = ref();

		const state: any = reactive({
			tableData: {
				data: [],
				total: 0,
				loading: false,
				param: {
					page: 1,
					pageSize: 10,
				},
			},
		});

		const onOpenAddRole = (row: object) => {
			addRoleRef.value.openDialog(row);
		};

		//角色权限设置弹出框
		const onOpenAddPermission = (row: object) => {
			addPermissionRef.value.openDialog(row);
		}

		// 初始化表格数据
		const initTableData = () => {
			getRoleList(state.tableData.param).then((res) => {
				state.tableData.data = res.data.records;
				state.tableData.total = res.data.total;
			}).catch(() => {
			})
		};
		// 当前行删除
		const onRowDel = (row: object) => {
			ElMessageBox.confirm('此操作将永久删除角色, 是否继续?', '提示', {
				confirmButtonText: '删除',
				cancelButtonText: '取消',
				type: 'warning',
			}).then(() => {
				delRole(row.id).then(() => {
					ElMessage.success("删除成功");
					initTableData();
				}).catch((res) => {
					ElMessage.error(res.message);
				});
			})
			.catch(() => {});
		};
		// 分页改变
		const onHandleSizeChange = (val: number) => {
			state.tableData.param.pageSize = val;
			initTableData();
		};
		// 分页改变
		const onHandleCurrentChange = (val: number) => {
			state.tableData.param.page = val;
			initTableData();
		};
		// 页面加载时
		onMounted(() => {
			initTableData();
		});
		return {
			addRoleRef,
			addPermissionRef,
			onRowDel,
			onHandleSizeChange,
			onHandleCurrentChange,
			onOpenAddRole,
			onOpenAddPermission,
			initTableData,
			...toRefs(state),
		};
	},
};
</script>

<style scoped lang="scss">
.system-role-container {
	.system-role-search {
		text-align: left;
	}
	.system-role-photo {
		width: 40px;
		height: 40px;
		border-radius: 100%;
	}
}
</style>
