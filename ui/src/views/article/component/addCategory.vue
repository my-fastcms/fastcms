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
                            :src="state.ruleForm.iconUrl"
                            :fit="state.fit"
							:preview-src-list="[state.ruleForm.iconUrl]"
							></el-image>
						
							<el-link  style="" type="primary" size="small" plain @click="onThumbnailDialogOpen">选择图片</el-link>
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
					<el-button type="primary" @click="onSubmit" size="default">保 存</el-button>
				</span>
			</template>
			<AttachDialog ref="thumbnailDialogRef" @attachHandler="getSelectThumbnail" :fileType="state.fileType"/>
		</el-dialog>
	</div>
</template>

<script lang="ts" setup name="articleAddCategory">
import { reactive, ref, nextTick } from 'vue';
import { ElMessage } from 'element-plus';
// import IconSelector from '/@/components/iconSelector/index.vue';
import { ArticleApi } from '/@/api/article/index';
import AttachDialog from '/@/components/attach/index.vue';


const emit = defineEmits(["reloadTable"])
const articleApi = ArticleApi();
const myRefForm = ref();

const thumbnailDialogRef = ref();

const state = reactive({
	isShowDialog: false,
	title: '',
	fit: 'fill',
	ruleForm: {
		id: '',
		parentId: '',
		title: '', 
		path: '',
		suffix: '', 
		icon: '',
		sortNum: '',
		iconUrl: '',
	},
	rules: {
		"title": { required: true, message: '请输入分类名称', trigger: 'blur' },
	},
});
// 打开弹窗
const openDialog = (type: string, row?: any) => {
	if (type === 'edit') {
		state.title = '修改分类';
		articleApi.getArticleCategory(row.id).then(res => {
			delete res.data.created;
			delete res.data.updated;
			state.ruleForm = res.data;
		})
	} else {
		state.title = '新增分类';
		nextTick(() => {
			// 清空表单，此项需加表单验证才能使用
			myRefForm.value.resetFields();
			state.ruleForm.id = '';
			// 设置菜单上级
			state.ruleForm.parentId = row == null ? 0 : (row.id || 0);
		});
	}
	state.isShowDialog = true;
};
// 关闭弹窗
const closeDialog = () => {
	state.isShowDialog = false;
	initForm();
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
const getSelectThumbnail = (value: any) => {
    state.ruleForm.icon = value[0].filePath;
    state.ruleForm.iconUrl = value[0].path;
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
	state.ruleForm.iconUrl = '',
	nextTick(()=> {
		myRefForm.value.resetFields();
	})
};

defineExpose({
	openDialog
})
</script>
