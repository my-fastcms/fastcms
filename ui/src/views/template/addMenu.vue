<template>
	<div >
		<el-dialog title="新增菜单" v-model="state.isShowDialog" width="769px">
			<el-form :model="state.ruleForm" size="small" label-width="80px" :rules="state.rules" ref="myRefForm">
				<el-row :gutter="35">
					<el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
						<el-form-item label="菜单名称" prop="menuName">
							<el-input v-model="state.ruleForm.menuName" placeholder="请输入菜单名称" clearable></el-input>
						</el-form-item>
					</el-col>
					<el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
						<el-form-item label="菜单" prop="menuIcon">
							<IconSelector placeholder="请输入菜单图标" v-model="state.ruleForm.menuIcon" />
						</el-form-item>
					</el-col>
					<el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
						<el-form-item label="关联" prop="urlType">
							<el-select v-model="state.ruleForm.urlType" placeholder="请选择" clearable class="w100">
								<el-option label="网址" value="0"></el-option>
								<el-option label="文章" value="1"></el-option>
								<el-option label="页面" value="2"></el-option>
								<el-option label="分类" value="3"></el-option>
							</el-select>
						</el-form-item>
					</el-col>
					<el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
						<el-form-item label="菜单地址" prop="menuUrl">
							<el-input v-model="state.ruleForm.menuUrl" placeholder="请输入菜单地址" clearable></el-input>
						</el-form-item>
					</el-col>
					<el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
						<el-form-item label="排序" prop="sortNum">
							<el-input type="number" v-model="state.ruleForm.sortNum" placeholder="排序" clearable></el-input>
						</el-form-item>
					</el-col>
					<el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
						<el-form-item label="打开方式">
							<el-select v-model="state.ruleForm.target" placeholder="请选择打开方式" clearable class="w100">
								<el-option label="本窗口" value="_self"></el-option>
								<el-option label="新开窗口" value="_blank"></el-option>
							</el-select>
						</el-form-item>
					</el-col>
				</el-row>
			</el-form>
			<template #footer>
				<span class="dialog-footer">
					<el-button @click="onCancel">取 消</el-button>
					<el-button type="primary" @click="onSubmit">新 增</el-button>
				</span>
			</template>
		</el-dialog>
	</div>
</template>

<script lang="ts" name="templateAddMenu" setup>
import { ref, reactive } from 'vue';
import { ElMessage } from 'element-plus';
import IconSelector from '/@/components/iconSelector/index.vue';
import { TemplateApi } from '/@/api/template/index';

const emit = defineEmits(["reloadTable"]);
const myRefForm = ref();
const templateApi = TemplateApi();
const state = reactive({
	isShowDialog: false,
	ruleForm: {
		id: '',
		parentId: '',
		menuName: '', 
		menuUrl: '',
		menuIcon: '', 
		sortNum: '',
		target: '_self',
		urlType: '',
	},
	rules: {
		"menuName": { required: true, message: '请输入菜单名称', trigger: 'blur' },
		"menuUrl": { required: true, message: '请输入菜单地址', trigger: 'blur' },
	},
});
// 打开弹窗
const openDialog = (row?: object) => {
	console.log(row);
	state.isShowDialog = true;
	if(row) {
		state.ruleForm.parentId=row.id;
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

	myRefForm.value.validate((valid: any) => {
		if (valid) {
			templateApi.saveTemplateMenu(state.ruleForm).then(() => {
				closeDialog(); // 关闭弹窗
				// 刷新菜单，未进行后端接口测试
				initForm();
				emit("reloadTable");
			}).catch((res) => {
				ElMessage({showClose: true, message: res.message ? res.message : '系统错误' , type: 'error'});
			})
		}
	});

};

// 表单初始化，方法：`resetFields()` 无法使用
const initForm = () => {
	state.ruleForm.menuName = '';
	state.ruleForm.menuUrl = '';
	state.ruleForm.menuIcon = '';
	state.ruleForm.target = '';
	state.ruleForm.sortNum = '';
};

defineExpose({
	openDialog
})
</script>
