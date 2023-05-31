<template>
	<div class="system-dept-dialog-container">
		<el-dialog :title="state.dialog.title" v-model="state.dialog.isShowDialog" width="769px">
			<el-form ref="deptDialogFormRef" :model="state.ruleForm" :rules="state.rules" size="default" label-width="90px">
				<el-row :gutter="35">
					<el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24" class="mb20">
						<el-form-item label="上级部门">
							<el-cascader
								ref="parentDeptCascader"
								:options="props.deptParentData"
								:props="{ checkStrictly: true, value: 'id', label: 'deptName' }"
								placeholder="请选择部门"
								clearable
								class="w100"
								v-model="state.ruleForm.parentId"
								:show-all-levels="false"
								@change="onParentDeptChange"
							>
								<template #default="{ node, data }">
									<span>{{ data.deptName }}</span>
									<span v-if="!node.isLeaf"> ({{ data.children.length }}) </span>
								</template>
							</el-cascader>
						</el-form-item>
					</el-col>
					<el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
						<el-form-item label="部门名称" prop="deptName">
							<el-input v-model="state.ruleForm.deptName" placeholder="请输入部门名称" clearable></el-input>
						</el-form-item>
					</el-col>
					<el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
						<el-form-item label="负责人" prop="deptLeader">
							<el-input v-model="state.ruleForm.deptLeader" placeholder="请输入负责人" clearable></el-input>
						</el-form-item>
					</el-col>
					<el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
						<el-form-item label="手机号" prop="deptPhone">
							<el-input v-model="state.ruleForm.deptPhone" placeholder="请输入手机号" clearable></el-input>
						</el-form-item>
					</el-col>
					<el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
						<el-form-item label="部门地址" prop="deptAddr">
							<el-input v-model="state.ruleForm.deptAddr" placeholder="请输入部门地址" clearable></el-input>
						</el-form-item>
					</el-col>
					<el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
						<el-form-item label="排序">
							<el-input-number v-model="state.ruleForm.sortNum" :min="0" :max="999" controls-position="right" placeholder="请输入排序" class="w100" />
						</el-form-item>
					</el-col>
					<el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
						<el-form-item label="部门状态">
							<el-switch v-model="state.ruleForm.status" :active-value="1" :inactive-value="0" inline-prompt active-text="启" inactive-text="禁"></el-switch>
						</el-form-item>
					</el-col>
					<el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24" class="mb20">
						<el-form-item label="部门描述" prop="deptDesc">
							<el-input v-model="state.ruleForm.deptDesc" type="textarea" placeholder="请输入部门描述" maxlength="150"></el-input>
						</el-form-item>
					</el-col>
				</el-row>
			</el-form>
			<template #footer>
				<span class="dialog-footer">
					<el-button @click="onCancel" size="default">取 消</el-button>
					<el-button type="primary" @click="onSubmit" size="default">{{ state.dialog.submitTxt }}</el-button>
				</span>
			</template>
		</el-dialog>
	</div>
</template>

<script setup lang="ts" name="systemDeptDialog">
import { nextTick, reactive, ref } from 'vue';
import { DeptApi } from '/@/api/dept/index';

const props = defineProps({
	deptParentData: {
		type: Array<DeptTreeType>
	}
}) 

// 定义子组件向父组件传值/事件
const emit = defineEmits(['refresh']);
const deptApi = DeptApi();
const parentDeptCascader = ref();

// 定义变量内容
const deptDialogFormRef = ref();
const state = reactive({
	ruleForm: {
		id: null,
		parentId: 0,
		deptName: '', // 部门名称
		deptLeader: '', // 负责人
		deptPhone: '', // 手机号
		deptAddr: '',
		sortNum: 0, // 排序
		status: 1, // 部门状态
		deptDesc: '', // 部门描述
	},
	rules: {
		deptName: { required: true, message: '请输入部门名称', trigger: 'blur' },
		deptLeader: { required: true, message: '请输入部门负责人', trigger: 'blur' },
		deptPhone: { required: true, message: '请输入部门负责人手机号', trigger: 'blur' },
	},
	deptData: [], // 部门数据
	dialog: {
		isShowDialog: false,
		type: '',
		title: '',
		submitTxt: '',
	},
});

// 打开弹窗
const openDialog = (type: string, row: RowDeptType) => {
	if (type === 'edit') {
		state.dialog.title = '修改部门';
		state.dialog.submitTxt = '修 改';

		deptApi.getDept(row.id).then(res => {
			delete res.data.created;
			delete res.data.updated;
			state.ruleForm = res.data;
		})

	} else {
		state.dialog.title = '新增部门';
		state.dialog.submitTxt = '新 增';
		// 清空表单，此项需加表单验证才能使用
		nextTick(() => {
			deptDialogFormRef.value.resetFields();
			state.ruleForm.id = null;
			// 设置菜单上级
			state.ruleForm.parentId = row == null ? 0 : (row.id || 0);
		});
	}
	// console.log("length:" + this.deptParentData.length)
	state.dialog.isShowDialog = true;
};
// 关闭弹窗
const closeDialog = () => {
	state.dialog.isShowDialog = false;
};
// 取消
const onCancel = () => {
	closeDialog();
};
const onParentDeptChange = () => {
	const p = parentDeptCascader.value.getCheckedNodes();
	state.ruleForm.parentId = p[0].value;
};
// 提交
const onSubmit = () => {
	
	new Promise((resolve) => {
		deptDialogFormRef.value.validate((valid: boolean) => {
			if (valid) {
				resolve(valid);
				deptApi.saveDept(state.ruleForm).then(() => {
					closeDialog(); // 关闭弹窗
					emit('refresh');
				})
			}
		});
	});

};

// 暴露变量
defineExpose({
	openDialog,
});
</script>