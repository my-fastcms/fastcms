<template>
	<div >
		<el-dialog title="图片详情" v-model="state.isShowDialog" width="769px">
			<el-form :model="state.ruleForm" label-width="80px" :rules="state.rules" ref="myRefForm">
				<el-row :gutter="35">
                    <el-col class="mb20">
						<img alt="fastcms" :src="state.ruleForm.path">
					</el-col>
					<el-col class="mb20">
						<el-form-item label="文件名称" prop="fileName">
							<el-input v-model="state.ruleForm.fileName" readonly></el-input>
						</el-form-item>
					</el-col>
					<el-col class="mb20">
						<el-form-item label="文件路径" prop="filePath">
							<el-input v-model="state.ruleForm.filePath" readonly></el-input>
						</el-form-item>
					</el-col>
                    <el-col class="mb20">
						<el-form-item label="文件大小" prop="size">
							<el-input v-model="state.ruleForm.fileSize" readonly></el-input>
						</el-form-item>
					</el-col>
					<el-col class="mb20">
						<el-form-item label="上传时间" prop="size">
							<el-input v-model="state.ruleForm.created" readonly></el-input>
						</el-form-item>
					</el-col>
				</el-row>
			</el-form>
			<template #footer>
				<span class="dialog-footer">
                    <el-button type="danger" @click="deleteAttach">删 除</el-button>
					<el-button @click="onCancel">取 消</el-button>
				</span>
			</template>
		</el-dialog>
	</div>
</template>

<script lang="ts" name="attachDetail" setup>
import { reactive, onUpdated } from 'vue';
import { ElMessageBox, ElMessage } from 'element-plus';
import { ClientAttachApi } from '/@/api/attach/client';

const emit = defineEmits(["reloadTable"])

const clientArticleApi = ClientAttachApi();
const state = reactive({
	isShowDialog: false,
	ruleForm: {
		id: null,
	},
	rules: {
		"fileName": { required: true, message: '请输入文件名称', trigger: 'blur' },
	},
});
// 打开弹窗
const openDialog = (id?: any) => {
	state.isShowDialog = true;
	state.ruleForm.id=id;
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

//删除附件
const deleteAttach = () => {
	ElMessageBox.confirm('此操作将永久删除附件, 是否继续?', '提示', {
		confirmButtonText: '删除',
		cancelButtonText: '取消',
		type: 'warning',
	}).then(() => {
		clientArticleApi.delAttach(state.ruleForm.id).then(() => {
			closeDialog(); // 关闭弹窗
			ElMessage.success("删除成功");
			emit("reloadTable");
		}).catch((res) => {
			ElMessage.error(res.message);
		});
	})
	.catch(() => {});
}

const getFile = () => {
	if(state.ruleForm.id && state.ruleForm.id != null) {
		clientArticleApi.getAttach(state.ruleForm.id).then((res) => {
			state.ruleForm = res.data;
		})
	}
}

onUpdated(() => {
	getFile();
});

// 表单初始化，方法：`resetFields()` 无法使用
const initForm = () => {
	state.ruleForm = {};
};

defineExpose({
	openDialog
})
</script>
