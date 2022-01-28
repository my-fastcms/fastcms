<template>
	<div class="system-menu-container">
		<el-dialog title="编辑分类" v-model="isShowDialog" width="769px">
			<el-form :model="ruleForm" size="small" label-width="80px" :rules="rules" ref="myRefForm">
				<el-row :gutter="35">
					<el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
						<el-form-item label="分类名称" prop="title">
							<el-input v-model="ruleForm.title" placeholder="请输入分类名称" clearable></el-input>
						</el-form-item>
					</el-col>
					<el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
						<el-form-item label="分类图标" prop="icon">
							<IconSelector placeholder="请输入分类图标" v-model="ruleForm.icon" />
						</el-form-item>
					</el-col>
					<el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
						<el-form-item label="访问路径" prop="path">
							<el-input v-model="ruleForm.path" placeholder="请输入访问路径" clearable></el-input>
						</el-form-item>
					</el-col>
					<el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
						<el-form-item label="模板" prop="suffix">
							<el-input v-model="ruleForm.suffix" placeholder="请输入模板后缀名称" clearable></el-input>
							<div class="sub-title">结合网站模板使用</div>
						</el-form-item>
					</el-col>
					<el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
						<el-form-item label="排序" prop="sortNum">
							<el-input type="number" v-model="ruleForm.sortNum" placeholder="排序" clearable></el-input>
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

<script lang="ts">
import { reactive, toRefs, getCurrentInstance } from 'vue';
import IconSelector from '/@/components/iconSelector/index.vue';
import { ElMessage } from 'element-plus';
import { addArticleCategory } from '/@/api/article/index';
export default {
	name: 'articleEditCategory',
	components: { IconSelector },
	setup(props, ctx) {
		const { proxy } = getCurrentInstance() as any;
		const state = reactive({
			isShowDialog: false,
			ruleForm: {
				id: '',
				parentId: '',
				title: '', 
				suffix: '', 
				path: '',
				icon: '',
				sortNum: '',
			},
			rules: {
				"title": { required: true, message: '请输入分类名称', trigger: 'blur' },
			},
		});
		// 打开弹窗
		const openDialog = (row: any) => {
			state.ruleForm.id = row.id
			state.ruleForm.parentId = row.parentId;
			state.ruleForm.title = row.title;
			state.ruleForm.suffix = row.suffix;
			state.ruleForm.path = row.path;
			state.ruleForm.icon = row.icon;
			state.ruleForm.sortNum = row.sortNum;
			state.isShowDialog = true;
		};
		// 关闭弹窗
		const closeDialog = (row?: object) => {
			// eslint-disable-next-line no-console
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
					addArticleCategory(state.ruleForm).then(() => {
						closeDialog(); // 关闭弹窗
						ctx.emit("reloadTable");
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
			state.ruleForm.title = '';
			state.ruleForm.icon = '';
			state.ruleForm.suffix = '';
			state.ruleForm.path = '';
			state.ruleForm.sortNum = '';
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
