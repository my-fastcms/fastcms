<template>
	<div  >
		<el-dialog title="新增标签" v-model="state.isShowDialog" width="769px">
			<el-form :model="state.ruleForm" size="small" label-width="80px" :rules="state.rules" ref="myRefForm">
				<el-row :gutter="35">
					<el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
						<el-form-item label="标签名称" prop="tagName">
							<el-input v-model="state.ruleForm.tagName" placeholder="请输入标签名称" clearable></el-input>
						</el-form-item>
					</el-col>
					<el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
						<el-form-item label="标签图标" prop="icon">
							<IconSelector placeholder="请输入标签图标" v-model="state.ruleForm.icon" />
						</el-form-item>
					</el-col>
					<el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
						<el-form-item label="模板" prop="suffix">
							<el-input v-model="state.ruleForm.suffix" placeholder="请输入模板后缀名称" clearable></el-input>
							<div class="sub-title">结合网站模板使用</div>
						</el-form-item>
					</el-col>
					<el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
						<el-form-item label="排序" prop="sortNum">
							<el-input type="number" v-model="state.ruleForm.sortNum" placeholder="排序" clearable></el-input>
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

<script lang="ts" setup name="articleAddTag">
import { ref, reactive } from 'vue';
import { ElMessage } from 'element-plus';
import IconSelector from '/@/components/iconSelector/index.vue';
import { ArticleApi } from '/@/api/article/index';

const emit = defineEmits(["reloadTable"])
const articleApi = ArticleApi();
const myRefForm = ref();
const state = reactive({
	isShowDialog: false,
	ruleForm: {
		id: '',
		tagName: '',
		suffix: '', 
		icon: '',
		sortNum: '',
		parentId: 0,
	},
	rules: {
		"tagName": { required: true, message: '请输入标签名称', trigger: 'blur' },
	},
});
// 打开弹窗
const openDialog = (row?: any) => {
	console.log(row);
	state.isShowDialog = true;
	if(row) {
		state.ruleForm.parentId=row.id;
	}
};
// 关闭弹窗
const closeDialog = (row?: any) => {
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
			articleApi.addArticleTag(state.ruleForm).then(() => {
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
	state.ruleForm.id = '',
	state.ruleForm.tagName = '';
	state.ruleForm.icon = '';
	state.ruleForm.suffix = '';
	state.ruleForm.sortNum = '';
};

defineExpose({
	openDialog
})
</script>
