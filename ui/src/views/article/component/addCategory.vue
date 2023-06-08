<template>
	<div  >
		<el-dialog title="新增分类" v-model="state.isShowDialog" width="769px">
			<el-form :model="state.ruleForm" size="default" label-width="80px" :rules="state.rules" ref="myRefForm">
				<el-row :gutter="35">
					<el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
						<el-form-item label="分类名称" prop="title">
							<el-input v-model="state.ruleForm.title" placeholder="请输入分类名称" clearable></el-input>
						</el-form-item>
					</el-col>
					<el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
						<el-form-item label="分类图片" prop="icon" >
							<div style="display:flex;flex-direction: column;">
								<el-image
                            style="width: 100px; height: 100px;margin-bottom: 10px;"
                            :src="state.ruleForm.thumbnailUrl"
                            :fit="state.fit"
							:preview-src-list="[state.ruleForm.thumbnailUrl]"
							></el-image>
						
							<el-button style="" type="primary" size="mini" plain @click="onThumbnailDialogOpen">选择图片</el-button>
							</div>
							<!-- <IconSelector placeholder="请输入分类图标" v-model="state.ruleForm.icon" /> -->
							
						</el-form-item>
					</el-col>
					<el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
						<el-form-item label="访问路径" prop="path">
							<el-input v-model="state.ruleForm.path" placeholder="请输入访问路径" clearable></el-input>
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
					<el-button @click="onCancel" size="default">取 消</el-button>
					<el-button type="primary" @click="onSubmit" size="default">新 增</el-button>
				</span>
			</template>
			<AttachDialog ref="thumbnailDialogRef" @attachHandler="getSelectThumbnail" :fileType="state.fileType"/>
		</el-dialog>
	</div>
</template>

<script lang="ts" setup name="articleAddCategory">
import { reactive, ref } from 'vue';
import { ElMessage } from 'element-plus';
import IconSelector from '/@/components/iconSelector/index.vue';
import { ArticleApi } from '/@/api/article/index';
import AttachDialog from '/@/components/attach/index.vue';


const emit = defineEmits(["reloadTable"])
const articleApi = ArticleApi();
const myRefForm = ref();

const thumbnailDialogRef = ref();

const state = reactive({
	isShowDialog: false,
	ruleForm: {
		id: '',
		parentId: '',
		title: '', 
		path: '',
		suffix: '', 
		icon: '',
		sortNum: '',
	},
	rules: {
		"title": { required: true, message: '请输入分类名称', trigger: 'blur' },
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

//打开缩略图弹出框
const onThumbnailDialogOpen = () => {
    thumbnailDialogRef.value.openDialog(1);
};

//获取弹出框选中的图片
const getSelectThumbnail = (value) => {
    state.ruleForm.thumbnail = value[0].filePath;
    state.ruleForm.thumbnailUrl = value[0].path;
};
// 新增
const onSubmit = () => {

	myRefForm.value.validate((valid: any) => {
		if (valid) {
			articleApi.addArticleCategory(state.ruleForm).then(() => {
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
	state.ruleForm.parentId = '';
	state.ruleForm.title = '';
	state.ruleForm.icon = '';
	state.ruleForm.suffix = '';
	state.ruleForm.path = '';
	state.ruleForm.sortNum = '';
};

defineExpose({
	openDialog
})
</script>
