<template>
	<div class="system-menu-container">
		<el-dialog title="编辑菜单" v-model="isShowDialog" width="769px">
			<el-form :model="ruleForm" size="small" label-width="80px" :rules="rules" ref="myRefForm">
				<el-row :gutter="35">
					<el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
						<el-form-item label="菜单名称" prop="menuName">
							<el-input v-model="ruleForm.menuName" placeholder="请输入菜单名称" clearable></el-input>
						</el-form-item>
					</el-col>
					<el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
						<el-form-item label="菜单" prop="menuIcon">
							<IconSelector placeholder="请输入菜单图标" v-model="ruleForm.menuIcon" />
						</el-form-item>
					</el-col>
					<el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
						<el-form-item label="关联">
							<el-select v-model="ruleForm.urlType" placeholder="请选择" clearable class="w100">
								<el-option label="网址" value="0"></el-option>
								<el-option label="文章" value="1"></el-option>
								<el-option label="页面" value="2"></el-option>
								<el-option label="分类" value="3"></el-option>
							</el-select>
						</el-form-item>
					</el-col>
					<el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
						<el-form-item label="菜单地址" prop="menuUrl">
							<el-input v-model="ruleForm.menuUrl" placeholder="请输入菜单地址" clearable></el-input>
						</el-form-item>
					</el-col>
					<el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
						<el-form-item label="排序" prop="sortNum">
							<el-input v-model="ruleForm.sortNum" placeholder="排序" type="number" clearable></el-input>
						</el-form-item>
					</el-col>
					<el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
						<el-form-item label="打开方式">
							<el-select v-model="ruleForm.target" placeholder="请选择打开方式" clearable class="w100">
								<el-option label="本窗口" value="_self"></el-option>
								<el-option label="新开窗口" value="_blank"></el-option>
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

<script lang="ts">
import { reactive, toRefs, getCurrentInstance } from 'vue';
import IconSelector from '/@/components/iconSelector/index.vue';
import { ElMessage } from 'element-plus';
import { saveTemplateMenu } from '/@/api/template/index';
export default {
	name: 'templateEditMenu',
	components: { IconSelector },
	setup(props, ctx) {
		const { proxy } = getCurrentInstance() as any;
		const state = reactive({
			isShowDialog: false,
			ruleForm: {
				id: '',
				parentId: '',
				menuName: '', 
				menuUrl: '',
				urlType: '0',
				menuIcon: '', 
				sortNum: '',
				target: '',
			},
			rules: {
				"menuName": { required: true, message: '请输入菜单名称', trigger: 'blur' },
				"menuUrl": { required: true, message: '请输入菜单地址', trigger: 'blur' },
			},
		});
		// 打开弹窗
		const openDialog = (row: any) => {
			state.ruleForm.id = row.id
			state.ruleForm.parentId = row.parentId;
			state.ruleForm.menuName = row.menuName;
			state.ruleForm.menuUrl = row.menuUrl;
			state.ruleForm.menuIcon = row.menuIcon;
			state.ruleForm.target = row.target;
			state.ruleForm.urlType = row.urlType.toString();
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
					saveTemplateMenu(state.ruleForm).then(() => {
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
			state.ruleForm.menuName = '';
			state.ruleForm.menuUrl = '';
			state.ruleForm.urlType = '0',
			state.ruleForm.menuIcon = '';
			state.ruleForm.target = '';
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
