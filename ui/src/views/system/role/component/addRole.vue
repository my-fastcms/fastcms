<template>
	<div class="system-menu-container">
		<el-dialog title="新增角色" v-model="isShowDialog" width="769px">
			<el-form :model="ruleForm" size="small" label-width="80px" :rules="rules" ref="myRefForm">
				<el-row :gutter="35">
					<el-col class="mb20">
						<el-form-item label="角色名称" prop="roleName">
							<el-input v-model="ruleForm.roleName" placeholder="请输入角色名称" clearable></el-input>
						</el-form-item>
					</el-col>
					<el-col class="mb20">
						<el-form-item label="角色描述" prop="roleDesc">
							<el-input v-model="ruleForm.roleDesc" placeholder="请输入角色描述" clearable></el-input>
						</el-form-item>
					</el-col>
					<el-col class="mb20">
						<el-form-item label="是否启用">
							<el-select v-model="ruleForm.active" placeholder="请选择是否启用" clearable class="w100">
								<el-option label="是" value="1"></el-option>
								<el-option label="否" value="0"></el-option>
							</el-select>
						</el-form-item>
					</el-col>
				</el-row>
			</el-form>
			<template #footer>
				<span class="dialog-footer">
					<el-button @click="onCancel" size="small">取 消</el-button>
					<el-button type="primary" @click="onSubmit" size="small">保 存</el-button>
				</span>
			</template>
		</el-dialog>
	</div>
</template>

<script lang="ts">
import { reactive, toRefs, getCurrentInstance } from 'vue';
import { ElMessage } from 'element-plus';
import { saveRole } from '/@/api/role/index';

export default {
	name: 'systemAddRole',
	setup(props, ctx) {
		const { proxy } = getCurrentInstance() as any;
		const state = reactive({
			isShowDialog: false,
			ruleForm: {
				id: null,
				roleName: '', // 角色名称
				roleDesc: '',
				active: '1', // 是否启用
			},
			rules: {
				"roleName": { required: true, message: '请输入角色名称', trigger: 'blur' },
				"roleDesc": { required: true, message: '请输入角色描述', trigger: 'blur' },
			},
		});
		// 打开弹窗
		const openDialog = (row?: object) => {
			state.isShowDialog = true;
			if(row.id) {
				initForm(row);
			}
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
					saveRole(state.ruleForm).then(() => {
						closeDialog(); // 关闭弹窗
						initForm();
						ctx.emit("reloadTable");
					}).catch((res) => {
						ElMessage({showClose: true, message: res.message ? res.message : '系统错误' , type: 'error'});
					})
				}
			});

		};

		// 表单初始化，方法：`resetFields()` 无法使用
		const initForm = (row) => {
			if(row && row.id) {
				state.ruleForm.id = row.id;
				state.ruleForm.roleName = row ? row.roleName : '';
				state.ruleForm.roleDesc = row ? row.roleDesc : '';
				state.ruleForm.active = row ? row.active + "" : '1';
			}
		};
		return {
			openDialog,
			closeDialog,
			onCancel,
			onSubmit,
			...toRefs(state),
		};
	},
};
</script>
