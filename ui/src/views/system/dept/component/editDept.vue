<template>
	<div class="system-edit-dept-container">
		<el-dialog title="修改部门" v-model="isShowDialog" width="769px">
			<el-form :model="ruleForm" size="small" label-width="90px" :rules="rules" ref="myRefForm">
				<el-row :gutter="35">
					<el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
						<el-form-item label="部门名称">
							<el-input v-model="ruleForm.deptName" placeholder="请输入部门名称" clearable></el-input>
						</el-form-item>
					</el-col>
					<el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
						<el-form-item label="负责人">
							<el-input v-model="ruleForm.deptLeader" placeholder="请输入负责人" clearable></el-input>
						</el-form-item>
					</el-col>
					<el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24" class="mb20">
						<el-form-item label="部门描述">
							<el-input v-model="ruleForm.deptDesc" type="textarea" placeholder="请输入部门描述" maxlength="150"></el-input>
						</el-form-item>
					</el-col>
					<el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
						<el-form-item label="手机号">
							<el-input v-model="ruleForm.deptPhone" placeholder="请输入手机号" clearable></el-input>
						</el-form-item>
					</el-col>
					<el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
						<el-form-item label="地址">
							<el-input v-model="ruleForm.deptAddr" placeholder="请输入" clearable></el-input>
						</el-form-item>
					</el-col>
					<el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
						<el-form-item label="排序">
							<el-input-number v-model="ruleForm.sortNum" :min="0" :max="999" controls-position="right" placeholder="请输入排序" class="w100" />
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
				</el-row>
			</el-form>
			<template #footer>
				<span class="dialog-footer">
					<el-button @click="onCancel" size="small">取 消</el-button>
					<el-button type="primary" @click="onSubmit" size="small">修 改</el-button>
				</span>
			</template>
		</el-dialog>
	</div>
</template>

<script lang="ts">
import { reactive, toRefs, onMounted, defineComponent, getCurrentInstance } from 'vue';
import { ElMessage } from 'element-plus';
import { saveDept } from '/@/api/dept/index';

// 定义接口来定义对象的类型
interface RuleFormState {
	id: number,
	parentId: number;
	deptName: string;
	deptLeader: string;
	deptPhone: string | number;
	deptAddr: string;
	sortNum: number;
	status: string;
	deptDesc: string;
}
interface DeptSate {
	isShowDialog: boolean;
	ruleForm: RuleFormState;
}

export default defineComponent({
	name: 'systemEditDept',
	setup(props, ctx) {
		const { proxy } = getCurrentInstance() as any;
		const state = reactive<DeptSate>({
			isShowDialog: false,
			ruleForm: {
				parentId: 0, // 上级部门
				deptName: '', // 部门名称
				deptLeader: '', // 负责人
				deptPhone: '', // 手机号
				deptAddr: '', // 邮箱
				sortNum: 0, // 排序
				status: '1', // 部门状态
				deptDesc: '', // 部门描述
			},
			rules: {
				"deptName": { required: true, message: '请输入部门名称', trigger: 'blur' },
				"deptDesc": { required: true, message: '请输入部门描述', trigger: 'blur' },
			},
		});
		// 打开弹窗
		const openDialog = (row: RuleFormState) => {
			state.ruleForm = row;
			state.ruleForm.status = row.status + '';
			state.isShowDialog = true;
		};
		// 关闭弹窗
		const closeDialog = () => {
			state.isShowDialog = false;
		};
		// 取消
		const onCancel = () => {
			closeDialog();
		};
		// 修改
		const onSubmit = () => {
			proxy.$refs['myRefForm'].validate((valid: any) => {
				if (valid) {
					state.ruleForm.created = null;
					state.ruleForm.children = null;
					saveDept(state.ruleForm).then(() => {
						closeDialog(); // 关闭弹窗
						// initForm();
						ctx.emit("reloadTable");
					}).catch((res) => {
						ElMessage({showClose: true, message: res.message ? res.message : '系统错误' , type: 'error'});
					})
				}
			});
		};
		
		// 页面加载时
		onMounted(() => {
			
		});
		return {
			openDialog,
			closeDialog,
			onCancel,
			onSubmit,
			...toRefs(state),
		};
	},
});
</script>
