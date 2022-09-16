<template>
	<div class="system-menu-container">
		<el-dialog title="编辑员工" v-model="isShowDialog" width="769px">
			<el-form :model="ruleForm" size="small" label-width="80px" :rules="rules" ref="myRefForm">
				<el-row :gutter="35">
					<el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
						<el-form-item label="账号" prop="userName">
							<el-input v-model="ruleForm.userName" readonly placeholder="请输入账号"></el-input>
						</el-form-item>
					</el-col>
					<el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
						<el-form-item label="昵称" prop="nickName">
							<el-input v-model="ruleForm.nickName" placeholder="请输入昵称" clearable></el-input>
						</el-form-item>
					</el-col>
					<el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
						<el-form-item label="手机号码" prop="mobile">
							<el-input v-model="ruleForm.mobile" placeholder="请输入手机号码" clearable></el-input>
						</el-form-item>
					</el-col>
					<el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
						<el-form-item label="电子邮箱" prop="email">
							<el-input v-model="ruleForm.email" placeholder="请输入电子邮箱" clearable></el-input>
						</el-form-item>
					</el-col>
					<el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
						<el-form-item label="公司" prop="company">
							<el-input v-model="ruleForm.company" placeholder="请输入公司名称" clearable></el-input>
						</el-form-item>
					</el-col>
					<el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
						<el-form-item label="地址" prop="address">
							<el-input v-model="ruleForm.address" placeholder="请输入地址" clearable></el-input>
						</el-form-item>
					</el-col>
					<el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
						<el-form-item label="性别">
							<el-select v-model="ruleForm.sex" placeholder="请选择性别" clearable class="w100">
								<el-option label="男" value="1"></el-option>
								<el-option label="女" value="0"></el-option>
							</el-select>
						</el-form-item>
					</el-col>
					<el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
						<el-form-item label="状态">
							<el-select v-model="ruleForm.status" placeholder="请选择状态" clearable class="w100">
								<el-option label="启用" value="1"></el-option>
								<el-option label="禁用" value="0"></el-option>
							</el-select>
						</el-form-item>
					</el-col>
					<el-col class="mb20">
						<el-form-item label="用户标签">
							<el-select
								v-model="ruleForm.tagList"
								class="w100"
								multiple
								filterable
								allow-create
								default-first-option
								placeholder="可直接输入标签名称">
								<el-option v-for="item in tags" :key="item.id" :label="item.title" :value="item.title"></el-option>
							</el-select>
						</el-form-item>
					</el-col>
					<el-col class="mb20">
						<el-form-item label="分配部门">
							<el-cascader v-model="ruleForm.deptList" :options="deptList" placeholder="请选择部门" :props="{ checkStrictly: true, multiple: true, label: 'label', value: 'id', children: 'children' }" class="w100" clearable/>
						</el-form-item>
					</el-col>
					<el-col class="mb20">
						<el-form-item label="分配角色">
							<el-select v-model="ruleForm.roleList" multiple placeholder="请选择角色" clearable class="w100">
								<el-option v-for="(item,index) in roleList" :key="index" :label="item.roleName" :value="item.id" />
							</el-select>
						</el-form-item>
					</el-col>
				</el-row>
			</el-form>
			<template #footer>
				<span class="dialog-footer">
					<el-button @click="onCancel" size="small">取 消</el-button>
					<el-button :loading="loading" @click="onRestUserPassword" size="small">重置密码</el-button>
					<el-button type="primary" @click="onSubmit" size="small">保 存</el-button>
				</span>
			</template>
		</el-dialog>
	</div>
</template>

<script lang="ts">
import { reactive, toRefs, getCurrentInstance, onMounted, onUpdated } from 'vue';
import { ElMessageBox, ElMessage } from 'element-plus';
import { saveUser, getUserInfo, getTagList, resetPassword } from '/@/api/user/index';
import { getRoleSelectList } from '/@/api/role/index';
import { getDeptList } from '/@/api/dept/client';
import qs from 'qs';

export default {
	name: 'systemEditUser',
	setup(props, ctx) {
		const { proxy } = getCurrentInstance() as any;
		const state = reactive({
			loading: false,
			isShowDialog: false,
			deptList: [],
			tags: [],
			roleList: [],
			ruleForm: {
				id: null,
				userName: '',
				nickName: '',
				mobile: '', 
				email: '',
				company: '',
				address: '',
				sex: '1',
				status: '1',
				tagList:'',
				roleList: '',
			},
			rules: {
				"userName": { required: true, message: '请输入账号', trigger: 'blur' },
			},
		});
		// 打开弹窗
		const openDialog = (row?: object) => {
			console.log(row);
			state.isShowDialog = true;
			state.ruleForm.id=row.id;
		};
		// 关闭弹窗
		const closeDialog = (row?: object) => {
			console.log(row);
			state.isShowDialog = false;
		};
		// 取消
		const onCancel = () => {
			closeDialog();
			initForm();
		};

		// 新增
		const onSubmit = () => {

			proxy.$refs['myRefForm'].validate((valid: any) => {
				if (valid) {
					let params = qs.stringify(state.ruleForm, {arrayFormat: 'repeat'});
					saveUser(params).then(() => {
						closeDialog(); // 关闭弹窗
						// 刷新菜单，未进行后端接口测试
						initForm();
						ctx.emit("reloadTable");
					}).catch((res) => {
						ElMessage({showClose: true, message: res.message ? res.message : '系统错误' , type: 'error'});
					})
				}
			});

		};

		//重置密码
		const onRestUserPassword = () => {
			ElMessageBox.confirm('确定重置员工['+state.ruleForm.userName+']账号密码吗?', '提示', {
				confirmButtonText: '确定',
				cancelButtonText: '取消',
				type: 'warning',
			}).then(() => {
				state.loading = true;
				let params = qs.stringify({"userId": state.ruleForm.id}, {arrayFormat: 'repeat'});
				resetPassword(params).then(() => {
					state.loading = false;
					ElMessage.success("操作成功");
					closeDialog(); // 关闭弹窗
					// 刷新菜单，未进行后端接口测试
					initForm();
					ctx.emit("reloadTable");
				}).catch((res) => {
					state.loading = false;
					ElMessage.error(res.message);
				});
			})
			.catch(() => {});
		}

		const loadRoleList = () => {
			getRoleSelectList().then((res) => {
				state.roleList = res.data;
			}).catch((err) => {
				console.log(err);
			});
			getTagList().then((res) => {
				state.tags = res.data;
			});
			getDeptList().then((res) => {
				state.deptList = res.data;
			});
		}

		const getUser = () => {
			if(state.ruleForm.id && state.ruleForm.id != null) {
				getUserInfo(state.ruleForm.id).then((res) => {
					state.ruleForm.id = res.data.id,
					state.ruleForm.userName = res.data.userName,
					state.ruleForm.nickName = res.data.nickName,
					state.ruleForm.mobile = res.data.mobile, 
					state.ruleForm.email = res.data.email,
					state.ruleForm.company = res.data.company,
					state.ruleForm.address = res.data.address,
					state.ruleForm.sex = res.data.sex + '',
					state.ruleForm.status = res.data.status + '',
					state.ruleForm.tagList = res.data.tagList,
					state.ruleForm.roleList = res.data.roleList,
					state.ruleForm.deptList = res.data.deptList
				})
			}
		}

		onMounted(() => {
			loadRoleList();
		});

		onUpdated(() => {
			getUser();
		})

		// 表单初始化，方法：`resetFields()` 无法使用
		const initForm = () => {
			state.ruleForm.id = null,
			state.ruleForm.userName = '',
			state.ruleForm.nickName = '',
			state.ruleForm.mobile = '', 
			state.ruleForm.email = '',
			state.ruleForm.company = '',
			state.ruleForm.address = '',
			state.ruleForm.sex = '',
			state.ruleForm.status = '',
			state.ruleForm.roleList = '',
			state.ruleForm.deptList = ''
		};
		return {
			openDialog,
			closeDialog,
			onCancel,
			onSubmit,
			onRestUserPassword, 
			loadRoleList,
			...toRefs(state),
		};
	},
};
</script>
