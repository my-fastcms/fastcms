<template>
	<div class="system-user-container">
		<el-dialog title="新增用户" v-model="state.isShowDialog" width="769px">
			<el-form :model="state.ruleForm" label-width="80px" :rules="state.rules" ref="myRefForm">
				<el-row :gutter="35">
					<el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
						<el-form-item label="账号" prop="userName">
							<el-input v-model="state.ruleForm.userName" :readonly="!state.isAdd" :display="!state.isAdd" placeholder="请输入账号" clearable></el-input>
						</el-form-item>
					</el-col>
					<el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20" v-if="state.isAdd">
						<el-form-item label="密码" prop="password">
							<el-input v-model="state.ruleForm.password" type="password" placeholder="请输入密码" clearable></el-input>
						</el-form-item>
					</el-col>
					<el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
						<el-form-item label="昵称" prop="nickName">
							<el-input v-model="state.ruleForm.nickName" placeholder="请输入昵称" clearable></el-input>
						</el-form-item>
					</el-col>
					<el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
						<el-form-item label="手机号码" prop="mobile">
							<el-input v-model="state.ruleForm.mobile" placeholder="请输入手机号码" clearable></el-input>
						</el-form-item>
					</el-col>
					<el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
						<el-form-item label="电子邮箱" prop="email">
							<el-input v-model="state.ruleForm.email" placeholder="请输入电子邮箱" clearable></el-input>
						</el-form-item>
					</el-col>
					<el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
						<el-form-item label="公司" prop="company">
							<el-input v-model="state.ruleForm.company" placeholder="请输入公司名称" clearable></el-input>
						</el-form-item>
					</el-col>
					<el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
						<el-form-item label="地址" prop="address">
							<el-input v-model="state.ruleForm.address" placeholder="请输入地址" clearable></el-input>
						</el-form-item>
					</el-col>
					<el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
						<el-form-item label="性别">
							<el-select v-model="state.ruleForm.sex" placeholder="请选择性别" clearable class="w100">
								<el-option label="男" :value=1></el-option>
								<el-option label="女" :value=0></el-option>
							</el-select>
						</el-form-item>
					</el-col>
					<el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
						<el-form-item label="状态">
							<el-select v-model="state.ruleForm.status" placeholder="请选择状态" clearable class="w100">
								<el-option label="启用" :value=1></el-option>
								<el-option label="禁用" :value=0></el-option>
							</el-select>
						</el-form-item>
					</el-col>
					<el-col class="mb20">
						<el-form-item label="用户标签">
							<el-select
								v-model="state.ruleForm.tagList"
								class="w100"
								multiple
								filterable
								allow-create
								default-first-option
								placeholder="可直接输入标签名称">
								<el-option v-for="item in state.tags" :key="item.id" :label="item.title" :value="item.title"></el-option>
							</el-select>
						</el-form-item>
					</el-col>
				</el-row>
			</el-form>
			<template #footer>
				<span class="dialog-footer">
					<el-button @click="onCancel">取 消</el-button>
					<el-button type="primary" @click="onSubmit">保 存</el-button>
				</span>
			</template>
		</el-dialog>
	</div>
</template>

<script lang="ts" setup name="systemClientAddUser">
import { ref, reactive, onMounted, nextTick } from 'vue';
import { ElMessage } from 'element-plus';
import { UserApi } from '/@/api/user/index';
import qs from 'qs';

const userApi = UserApi();

const myRefForm = ref();
const emit = defineEmits(['reloadTable']);

const state = reactive({
	isShowDialog: false,
	isAdd: true,
	deptList: [],
	tags: [],
	roleList: [],
	ruleForm: {
		id: null,
		userName: '',
		userType: 2,	//表示网站或小程序客户端用户
		password: '',
		nickName: '',
		mobile: '', 
		email: '',
		company: '',
		address: '',
		sex: 1,
		status: 1,
		tagList:'',
		roleList: '',
		source: 'admin_create'
	} as any,
	rules: {
		"userName": { required: true, message: '请输入账号', trigger: 'blur' },
		"nickName": { required: true, message: '请输入昵称', trigger: 'blur' },
		"password": { required: true, message: '请输入密码', trigger: 'blur' },
	},
});
// 打开弹窗
const openDialog = (row: any) => {
	state.isShowDialog = true;
	if(row) {
		state.isAdd = false;
		userApi.getUserInfo(row.id).then((res) => {
			delete res.data.loginTime;
			delete res.data.created;
			delete res.data.updated;
			state.ruleForm = res.data;
		})
	}else {
		nextTick(() => {
			state.isAdd = true;
			state.ruleForm.id = null;
			state.ruleForm.tagList = '',
			myRefForm.value.resetFields();
		})
	}
};
// 关闭弹窗
const closeDialog = () => {
	state.isShowDialog = false;
};
// 取消
const onCancel = () => {
	closeDialog();
	initForm();
};

// 新增
const onSubmit = () => {

	myRefForm.value.validate((valid: any) => {
		if (valid) {
			let params = qs.stringify(state.ruleForm, {arrayFormat: 'repeat'});
			userApi.saveUser(params).then(() => {
				closeDialog(); // 关闭弹窗
				// 刷新菜单，未进行后端接口测试
				initForm();
				emit("reloadTable");
			}).catch((res) => {
				ElMessage({showClose: true, message: res.message ? res.message : '系统错误' , type: 'error'});
			})
		}
	});

};

const loadRoleList = () => {
	userApi.getTagList().then((res) => {
		state.tags = res.data;
	});
}

onMounted(() => {
	loadRoleList();
})

// 表单初始化，方法：`resetFields()` 无法使用
const initForm = () => {
	state.ruleForm.id = null,
	state.ruleForm.userName = '',
	state.ruleForm.password = '',
	state.ruleForm.nickName = '',
	state.ruleForm.mobile = '', 
	state.ruleForm.email = '',
	state.ruleForm.company = '',
	state.ruleForm.address = '',
	state.ruleForm.sex = '',
	state.ruleForm.status = '',
	state.ruleForm.roleList = '',
	state.ruleForm.tagList = '',
	state.ruleForm.userType = 2,
	state.ruleForm.source = 'admin_create'
};

// 暴露变量
defineExpose({
	openDialog,
});
</script>
