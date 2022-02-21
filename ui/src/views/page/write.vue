<template>
<div class="container">
    <el-card>
        <el-form :model="ruleForm" size="small" label-width="100px" :rules="rules" ref="myRefForm">
            <el-row :gutter="35">
                <el-col class="mb20">
                    <el-form-item label="标题" prop="title">
                        <el-input v-model="ruleForm.title" placeholder="请输入页面标题" clearable></el-input>
                    </el-form-item>
                </el-col>
                <el-col class="mb20">
                    <el-form-item label="访问路径" prop="path">
                        <el-input v-model="ruleForm.path" placeholder="请输入页面访问路径" clearable></el-input>
                    </el-form-item>
                </el-col>
                <el-col class="mb20">
                    <el-form-item label="页面详情" prop="contentHtml">
                        <ckeditor v-model="ruleForm.contentHtml"></ckeditor>
                    </el-form-item>
                </el-col>
                <el-col class="mb20">
                    <el-form-item label="缩略图" prop="thumbnail">
                        <el-image
                            style="width: 100px; height: 100px"
                            :src="ruleForm.thumbnail"
                            :fit="fit"></el-image>
                    </el-form-item>
                    <el-form-item>
                        <el-link type="primary" @click="onAttachDialogOpen">选择图片</el-link>
                    </el-form-item>
                </el-col>
                <el-col class="mb20">
                    <el-form-item label="页面摘要" prop="summary">
                        <el-input v-model="ruleForm.summary" type="textarea" :rows="2" placeholder="请输入文章摘要" clearable></el-input>
                    </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
                    <el-form-item label="SEO关键词" prop="seoKeywords">
                        <el-input type="textarea" :rows="2" v-model="ruleForm.seoKeywords" placeholder="请输入seo关键词" clearable></el-input>
                    </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
                    <el-form-item label="SEO描述" prop="seoDescription">
                        <el-input type="textarea" :rows="2" v-model="ruleForm.seoDescription" placeholder="请输入SEO描述" clearable></el-input>
                    </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
                    <el-form-item label="模板" prop="suffix">
                        <el-input v-model="ruleForm.suffix" placeholder="请输入页面模板后缀" clearable></el-input>
                        <div class="sub-title">结合网站模板使用，不正确填写，访问页面会出现404</div>
                    </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
                    <el-form-item label="允许评论" prop="commentEnable">
                        <el-switch
                            v-model="ruleForm.commentEnable"
                            active-color="#13ce66">
                        </el-switch>
                    </el-form-item>
                </el-col>
                <el-col class="mb20">
                    <el-form-item label="状态" prop="status">
                        <el-select v-model="ruleForm.status" placeholder="请选择状态" clearable class="w100">
                            <el-option label="发布" value="publish"></el-option>
                            <el-option label="草稿" value="draft"></el-option>
                        </el-select>
                    </el-form-item>
                </el-col>            
            </el-row>
            <el-row>
                <el-form-item>
                    <el-button type="primary" @click="onSubmit" size="small">保 存</el-button>
                </el-form-item>
            </el-row>
        </el-form>
    </el-card>
    <AttachDialog ref="attachDialogRef" @attachHandler="getSelectAttach" :fileType="fileType"/>
</div>
</template>

<script lang="ts">
import { toRefs, ref, reactive, getCurrentInstance, onMounted } from 'vue';
import { ElMessage } from 'element-plus';
import { useRoute } from 'vue-router';
import AttachDialog from '/@/components/attach/index.vue';
import { addPage, getPage } from '/@/api/page/index';
import qs from 'qs';
import CKEditor from "/@/components/ckeditor/index.vue";

export default {
	name: 'pageWrite',
    components: {
        AttachDialog,
        ckeditor: CKEditor
    },
	setup() {
        const attachDialogRef = ref();
        const route = useRoute();
        const { proxy } = getCurrentInstance() as any;
		const state = reactive({
            fileType: "image",
            fit: "fill",
            params: {},
            ruleForm: {
                title: '',
                path: '',
                commentEnable: 1,
                contentHtml: '',
                status: 'publish'
            },
            rules: {
				"title": { required: true, message: '请输入页面标题', trigger: 'blur' },
                "path": { required: true, message: '请输入页面访问路径', trigger: 'blur' },
                "contentHtml": { required: true, message: '请输入页面详情', trigger: 'blur' },
                "seoKeywords": { required: true, message: '请输入SEO关键词', trigger: 'blur' },
                "seoDescription": { required: true, message: '请输入SEO描述', trigger: 'blur' },
                "status": { required: true, message: '请选择发布状态', trigger: 'blur' },
			},
		});

        const onSubmit = () => {
            proxy.$refs['myRefForm'].validate((valid: any) => {
				if (valid) {
					let params = qs.stringify(state.ruleForm, {arrayFormat: 'repeat'});
					addPage(params).then((res) => {
                        state.ruleForm.id = res.data;
						ElMessage.success("保存成功");
					}).catch((res) => {
						ElMessage({showClose: true, message: res.message ? res.message : '系统错误' , type: 'error'});
					})
				}
			});
        };

        const getPageInfo = (id: string) => {
            getPage(id).then((res) => {
                state.ruleForm = res.data;
            })
        }

        //打开附件弹出框
		const onAttachDialogOpen = () => {
			attachDialogRef.value.openDialog(1);
		};

        //获取弹出框选中的附件
		const getSelectAttach = (value) => {
			state.ruleForm.thumbnail = value[0].path;
		};

        onMounted(() => {
            state.params = route;
            let pageId = state.params.query.id;
            if(pageId) {
                getPageInfo(pageId);
            }
        });

		return {
            attachDialogRef,
            onSubmit,
            onAttachDialogOpen,
            getSelectAttach,
            getPageInfo,
        	...toRefs(state),
		};
	},
};

</script>

<style lang="scss">
.ck-content { height:500px; }
</style>