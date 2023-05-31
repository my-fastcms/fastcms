<template>
	<div >
		<el-dialog title="新增角色" v-model="state.isShowDialog" width="769px">
			<el-form :model="state.ruleForm" size="default" label-width="80px" :rules="state.rules" ref="myRefForm">
				<el-row :gutter="35">
					<el-col class="mb20">
						<el-form-item label="角色名称" prop="roleName">
							<el-input v-model="state.ruleForm.roleName" placeholder="请输入角色名称" clearable></el-input>
						</el-form-item>
					</el-col>
					<el-col class="mb20">
						<el-form-item label="角色描述" prop="roleDesc">
							<el-input v-model="state.ruleForm.roleDesc" placeholder="请输入角色描述" clearable></el-input>
						</el-form-item>
					</el-col>
					<el-col class="mb20">
						<el-form-item label="是否启用">
							<el-select v-model="state.ruleForm.active" placeholder="请选择是否启用" clearable class="w100">
								<el-option label="是" value="1"></el-option>
								<el-option label="否" value="0"></el-option>
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

<script setup lang="ts" name="systemAddRoleDialog">
import { reactive, ref } from 'vue';
import { ElMessage } from 'element-plus';
import { RoleApi } from '/@/api/role/index';

const emit = defineEmits(['reloadTable']);
const myRefForm = ref();
const roleApi = RoleApi();
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
const openDialog = (row?: any) => {
	state.isShowDialog = true;
	if(row) {
		initForm(row);
	} else {
		state.ruleForm.id = null;
		myRefForm.value.resetFields()
	}
};
// 关闭弹窗
const closeDialog = () => {
	state.isShowDialog = false;
};
// 取消
const onCancel = () => {
	closeDialog();
};

// 新增
const onSubmit = () => {

	myRefForm.value.validate((valid: any) => {
		if (valid) {
			roleApi.saveRole(state.ruleForm).then(() => {
				closeDialog(); // 关闭弹窗
				emit("reloadTable");
			}).catch((res) => {
				ElMessage({showClose: true, message: res.message ? res.message : '系统错误' , type: 'error'});
			})
		}
	});

};

// 表单初始化，方法：`resetFields()` 无法使用
const initForm = (row: any) => {
	state.ruleForm.id = row.id;
	state.ruleForm.roleName = row.roleName;
	state.ruleForm.roleDesc = row.roleDesc;
};

defineExpose({
	openDialog,
});
</script>
