<template>
	<div class="system-user-container">
		<el-card shadow="hover">
			<div class="system-role-search mb15">
				<el-input size="default" v-model="state.tableData.param.username" placeholder="请输入账号" style="max-width: 180px" class="ml10"></el-input>
				<el-input size="default" v-model="state.tableData.param.phone" placeholder="请输入手机号" style="max-width: 180px" class="ml10"></el-input>
				<el-button size="default" type="primary" class="ml10" @click="initTableData">
					<el-icon><ele-Search /></el-icon>查询
				</el-button>
				<el-button size="default" type="primary" class="ml10" @click="onOpenAddUser()">
					<el-icon><ele-Plus /></el-icon>新建用户
				</el-button>
			</div>
			<el-table :data="state.tableData.data" stripe style="width: 100%">
				<el-table-column prop="id" label="ID" show-overflow-tooltip></el-table-column>
				<el-table-column prop="userName" label="登录账号" show-overflow-tooltip></el-table-column>
				<el-table-column prop="nickName" label="用户昵称" show-overflow-tooltip></el-table-column>
				<el-table-column prop="sourceStr" label="来源" show-overflow-tooltip></el-table-column>
				<el-table-column prop="statusStr" label="状态" show-overflow-tooltip></el-table-column>
				<el-table-column prop="created" label="加入时间" show-overflow-tooltip></el-table-column>
				<el-table-column prop="path" label="操作" width="290">
					<template #default="scope">
						<el-button size="small" text type="primary" @click="onOpenEditUser(scope.row)">修改</el-button>
						<el-button size="small" text type="primary" @click="onRowDel(scope.row)">删除</el-button>
						<el-button size="small" text type="primary" @click="onRestUserPassword(scope.row)">重置密码</el-button>
						<el-button size="small" text type="primary" @click="onSetSystemUser(scope.row)">设为员工</el-button>
					</template>
				</el-table-column>
			</el-table>
			<el-pagination
				@size-change="onHandleSizeChange"
				@current-change="onHandleCurrentChange"
				class="mt15"
				:pager-count="5"
				:page-sizes="[10, 20, 30]"
				v-model:current-page="state.tableData.param.pageNum"
				background
				v-model:page-size="state.tableData.param.pageSize"
				layout="total, sizes, prev, pager, next, jumper"
				:total="state.tableData.total"
			>
			</el-pagination>
		</el-card>
		<AddUser ref="addUserRef" @reloadTable="initTableData"/>
	</div>
</template>

<script lang="ts" setup name="systemClientUser">
import { ElMessageBox, ElMessage } from 'element-plus';
import { ref, reactive, onMounted } from 'vue';
import AddUser from '/@/views/user/addUser.vue';
import { UserApi } from '/@/api/user/index';

const userApi = UserApi();
const addUserRef = ref();
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
	addUserRef.value.openDialog(row);
};

// 初始化表格数据
const initTableData = () => {
	userApi.getUserList(state.tableData.param).then((res) => {
		state.tableData.data = res.data.records;
		state.tableData.total = res.data.total;
	}).catch(() => {
	})
};
// 当前行删除
const onRowDel = (row: any) => {
	ElMessageBox.confirm('此操作将永久删除用户, 是否继续?', '提示', {
		confirmButtonText: '删除',
		cancelButtonText: '取消',
		type: 'warning',
	}).then(() => {
		userApi.delUser(row.id).then(() => {
			ElMessage.success("删除成功");
			initTableData();
		}).catch((res) => {
			ElMessage.error(res.message);
		});
	})
	.catch(() => {});
};
//重置密码
const onRestUserPassword = (row: any) => {
	ElMessageBox.confirm('确定重置['+row.userName+']账号密码吗?', '提示', {
		confirmButtonText: '确定',
		cancelButtonText: '取消',
		type: 'warning',
	}).then(() => {
		state.loading = true;
		userApi.resetPassword(row.id).then(() => {
			state.loading = false;
			ElMessage.success("操作成功");
			initTableData();
		}).catch((res) => {
			state.loading = false;
			ElMessage.error(res.message);
		});
	})
	.catch(() => {});
};
// 设置用户为系统用户
const onSetSystemUser = (row: any) => {
	ElMessageBox.confirm('确定将用户['+row.userName+']设置为员工吗?', '提示', {
		confirmButtonText: '确定',
		cancelButtonText: '取消',
		type: 'warning',
	}).then(() => {
		state.loading = true;
		let params = {userId: row.id, userType: 1};
		userApi.changeUserType(params).then(() => {
			state.loading = false;
			ElMessage.success("设置成功");
			initTableData();
		}).catch((res) => {
			state.loading = false;
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
