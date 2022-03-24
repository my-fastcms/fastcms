<template>
	<div class="system-add-dept-container">
		<el-dialog title="新增部门" v-model="isShowDialog" width="769px">
			<el-form :model="ruleForm" size="small" label-width="90px" :rules="rules" ref="myRefForm">
				<el-row :gutter="35">
					<el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
						<el-form-item label="部门名称" prop="deptName">
							<el-input v-model="ruleForm.deptName" placeholder="请输入部门名称" clearable></el-input>
						</el-form-item>
					</el-col>
					<el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
						<el-form-item label="负责人" prop="deptLeader">
							<el-input v-model="ruleForm.deptLeader" placeholder="请输入负责人" clearable></el-input>
						</el-form-item>
					</el-col>
					<el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24" class="mb20">
						<el-form-item label="部门描述" prop="deptDesc">
							<el-input v-model="ruleForm.deptDesc" type="textarea" placeholder="请输入部门描述" maxlength="150"></el-input>
						</el-form-item>
					</el-col>
					<el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
						<el-form-item label="联系电话" prop="deptPhone">
							<el-input v-model="ruleForm.deptPhone" placeholder="请输入手机号" clearable></el-input>
						</el-form-item>
					</el-col>
					<el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
						<el-form-item label="地址" prop="deptAddr">
							<el-input v-model="ruleForm.deptAddr" placeholder="请输入部门地址" clearable></el-input>
						</el-form-item>
					</el-col>
					<el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
						<el-form-item label="排序" prop="sortNum">
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
					<el-button type="primary" @click="onSubmit" size="small">新 增</el-button>
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
interface DeptSate {
	isShowDialog: boolean;
	ruleForm: {
		id: number;
		parentId: number;
		deptName: string;
		deptLeader: string;
		deptAddr: string;
		deptPhone: string | number;
		sortNum: number;
		status: string;
		deptDesc: string;
	};
}

export default defineComponent({
	name: 'systemAddDept',
	setup(props, ctx) {
		const { proxy } = getCurrentInstance() as any;
		const state = reactive<DeptSate>({
			isShowDialog: false,
			ruleForm: {
				parentId: 0, // 上级部门
				deptName: '', // 部门名称
				deptLeader: '', // 负责人
				deptAddr: '', // 手机号
				deptPhone: '', // 邮箱
				sortNum: 0, // 排序
				status: "1", // 部门状态
				deptDesc: '', // 部门描述
			},
			rules: {
				"deptName": { required: true, message: '请输入部门名称', trigger: 'blur' },
				"deptDesc": { required: true, message: '请输入部门描述', trigger: 'blur' },
			},
		});
		// 打开弹窗
		const openDialog = (row: object) => {
			state.isShowDialog = true;
			if(row) {
				state.ruleForm.parentId = row.id;
			}
		};
		// 关闭弹窗
		const closeDialog = () => {
			state.isShowDialog = false;
			state.ruleForm = {
				sortNum: 0, // 排序
				status: "1", // 部门状态
			};
		};
		// 取消
		const onCancel = () => {
			closeDialog();
		};
		// 新增
		const onSubmit = () => {
			proxy.$refs['myRefForm'].validate((valid: any) => {
				if (valid) {
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
