<template>
	<div class="system-user-container">
		<el-card shadow="hover">
			<div class="system-role-search mb15">
				<el-button @click="onOpenAddUser" class="mt15" size="small" type="primary" icon="iconfont icon-shuxingtu">新建用户</el-button>
				<el-input size="small" v-model="tableData.param.username" placeholder="请输入账号" style="max-width: 180px" class="ml10"></el-input>
				<el-input size="small" v-model="tableData.param.phone" placeholder="请输入手机号" style="max-width: 180px" class="ml10"></el-input>
				<el-button size="small" type="primary" class="ml10" @click="initTableData">查询</el-button>
			</div>
			<el-table :data="tableData.data" stripe style="width: 100%">
				<el-table-column prop="id" label="ID" show-overflow-tooltip></el-table-column>
				<el-table-column prop="nickName" label="用户名" show-overflow-tooltip></el-table-column>
				<!-- <el-table-column prop="email" label="邮箱" show-overflow-tooltip></el-table-column> -->
				<el-table-column prop="sourceStr" label="来源" show-overflow-tooltip></el-table-column>
				<el-table-column prop="created" label="加入时间" show-overflow-tooltip></el-table-column>
				<el-table-column prop="path" label="操作" width="90">
					<template #default="scope">
						<el-button v-if="scope.row.id != 1" size="mini" type="text" @click="onOpenEditUser(scope.row)">修改</el-button>
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
		<AddUser ref="addUserRef" @reloadTable="initTableData"/>
		<EditUser ref="editUserRef" @reloadTable="initTableData"/>
	</div>
</template>

<script lang="ts">
import { ElMessageBox, ElMessage } from 'element-plus';
import { ref, toRefs, reactive, onMounted } from 'vue';
import AddUser from '/@/views/user/component/addUser.vue';
import EditUser from '/@/views/user/component/editUser.vue';
import { getUserList, delUser } from '/@/api/user/index';
export default {
	name: 'systemUser',
	components: { AddUser, EditUser },
	setup() {
		const addUserRef = ref();
		const editUserRef = ref();
		const state: any = reactive({
			tableData: {
				data: [],
				total: 0,
				loading: false,
				param: {
					type: 2,
					username: '',
					phone: '',
					pageNum: 1,
					pageSize: 10,
				},
			},
		});
		
		const onOpenAddUser = () => {
			addUserRef.value.openDialog();
		};

		const onOpenEditUser = (row: object) => {
			editUserRef.value.openDialog(row);
		};

		// 初始化表格数据
		const initTableData = () => {
			getUserList(state.tableData.param).then((res) => {
				state.tableData.data = res.data.records;
				state.tableData.total = res.data.total;
			}).catch(() => {
			})
		};
		// 当前行删除
		const onRowDel = (row: object) => {
			ElMessageBox.confirm('此操作将永久删除用户, 是否继续?', '提示', {
				confirmButtonText: '删除',
				cancelButtonText: '取消',
				type: 'warning',
			}).then(() => {
				delUser(row.id).then(() => {
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
			state.tableData.param.pageNum = val;
			initTableData();
		};
		// 页面加载时
		onMounted(() => {
			initTableData();
		});
		return {
			addUserRef,
			editUserRef,
			onOpenEditUser,
			onOpenAddUser,
			onRowDel,
			onHandleSizeChange,
			onHandleCurrentChange,
			initTableData,
			...toRefs(state),
		};
	},
};
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