<template>
	<div>
		<el-dialog title="编辑评论" v-model="state.isShowDialog" width="769px">
			<el-form :model="state.ruleForm" size="small" label-width="80px" :rules="state.rules" ref="myRefForm">
				<el-row :gutter="35">
					<el-col class="mb20">
						<el-form-item label="评论内容" prop="title">
							<el-input v-model="state.ruleForm.content" type="textarea" :rows="3" placeholder="请输入评论内容" clearable></el-input>
						</el-form-item>
					</el-col>
					<el-col class="mb20">
						<el-form-item label="状态" prop="status">
							<el-select v-model="state.ruleForm.status" placeholder="请选择状态" clearable class="w100">
								<el-option label="发布" value="public"></el-option>
								<el-option label="隐藏" value="hidden"></el-option>
								<el-option label="待审核" value="unaudited"></el-option>
							</el-select>
						</el-form-item>
					</el-col>
				</el-row>
			</el-form>
			<template #footer>
				<span class="dialog-footer">
					<el-button @click="onCancel" size="small">取 消</el-button>
					<el-button type="primary" @click="onSubmit" size="small">编 辑</el-button>
				</span>
			</template>
		</el-dialog>
	</div>
</template>

<script lang="ts" setup name="articleEditComment">
import { reactive, ref } from 'vue';
import { ElMessage } from 'element-plus';
import { PageApi } from '/@/api/page/index';


const emit = defineEmits(["reloadTable"]) 
const pageApi = PageApi();
const myRefForm = ref();
const state = reactive({
	isShowDialog: false,
	ruleForm: {
		id: '',
		parentId: '',
		content: '', 
		status: '', 
	},
	rules: {
		"content": { required: true, message: '请输入评论内容', trigger: 'blur' },
	},
});
// 打开弹窗
const openDialog = (row: any) => {
	state.ruleForm.id = row.id
	state.ruleForm.parentId = row.parentId;
	state.ruleForm.content = row.content;
	state.ruleForm.status = row.status;
	state.isShowDialog = true;
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
	myRefForm.value.validate((valid: boolean) => {
		if (valid) {
			pageApi.updatePageComment(state.ruleForm).then(() => {
				closeDialog();
				emit("reloadTable");
			}).catch((res) => {
				ElMessage({showClose: true, message: res.message ? res.message : '系统错误' , type: 'error'});
			})
		}
	});
};

// 表单初始化，方法：`resetFields()` 无法使用
const initForm = () => {
	state.ruleForm.id = '',
	state.ruleForm.parentId = '';
	state.ruleForm.content = '';
	state.ruleForm.status = '';
};

defineExpose({
	openDialog
})

</script>
