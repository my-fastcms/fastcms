<template>
	<div class="system-user-container">
		
		<el-card shadow="hover">
			<div class="system-dept-search mb15">
				<el-input v-model="state.tableData.param.username" placeholder="请输入账号" clearable style="max-width: 180px" class="ml10"></el-input>
				<el-input v-model="state.tableData.param.phone" placeholder="请输入手机号" clearable style="max-width: 180px" class="ml10"></el-input>
				<el-cascader v-model="state.tableData.param.deptId" :show-all-levels="true" :options="state.deptList" :props="{ checkStrictly: true, expandTrigger:'hover',label: 'label', value: 'id', children: 'children' }" placeholder="部门" clearable class="ml10"/>
				<el-button size="default" type="primary" class="ml10" @click="initTableData">
					<el-icon><ele-Search /></el-icon>查询
				</el-button>
				<el-button size="default" type="primary" class="ml10" @click="onOpenAddUser()">
					<el-icon><ele-Plus /></el-icon>新建员工
				</el-button>
			</div>

			<el-table :data="state.tableData.data" style="width: 100%">
				<el-table-column prop="id" label="ID" show-overflow-tooltip></el-table-column>
				<el-table-column prop="userName" label="账号" show-overflow-tooltip></el-table-column>
				<el-table-column prop="nickName" label="昵称" show-overflow-tooltip></el-table-column>
				<el-table-column prop="sourceStr" label="来源" show-overflow-tooltip></el-table-column>
				<el-table-column prop="created" label="加入时间" show-overflow-tooltip></el-table-column>
				<el-table-column prop="path" label="操作" show-overflow-tooltip width="240">
					<template #default="scope">
						<el-button size="small" text type="primary" v-if="scope.row.id != 1" @click="onOpenEditUser(scope.row)">修改</el-button>
						<el-button size="small" text type="primary" v-if="scope.row.id != 1" @click="onRowDel(scope.row)">删除</el-button>
						<el-button size="small" text type="primary" v-if="scope.row.id != 1" @click="onRestUserPassword(scope.row)">重置密码</el-button>
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

		<UserDialog ref="addUserRef" @reloadTable="initTableData"/>
	</div>
</template>

<script setup lang="ts" name="systemUser">
import { ElMessageBox, ElMessage } from 'element-plus';
import { ref, reactive, onMounted } from 'vue';
import UserDialog from '/@/views/system/user/dialog.vue';
import { UserApi } from '/@/api/user/index';
import { DeptApi } from '/@/api/dept/index';

const userApi = UserApi();
const deptApi = DeptApi();
const addUserRef = ref();
const state: any = reactive({
	deptList: [],
	tableData: {
		data: [],
		total: 0,
		loading: false,
		param: {
			type: 1,
			username: '',
			phone: '',
			pageNum: 1,
			pageSize: 10,
		},
	},
});

// const onClickTree = (data, node, elem) => {
// 	state.tableData.param.deptId = data.id;
// 	state.tableData.data = [];
// 	state.tableData.total = 0;
// 	state.tableData.param.pageNum = 1;
// 	initTableData();
// }

const onOpenAddUser = () => {
	addUserRef.value.openDialog();
};

const onOpenEditUser = (row: object) => {
	addUserRef.value.openDialog(row);
};

// 初始化表格数据
const initTableData = () => {

	const deptIdArray = state.tableData.param.deptId;
	if(deptIdArray != null && deptIdArray.length>0) {
		state.tableData.param.deptId = deptIdArray[deptIdArray.length-1];
	}
	state.tableData.data = [];
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
	ElMessageBox.confirm('确定重置员工['+row.userName+']账号密码吗?', '提示', {
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
	deptApi.getDeptList({status: 1}).then(res => {
		state.deptList = res.data
	})
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
