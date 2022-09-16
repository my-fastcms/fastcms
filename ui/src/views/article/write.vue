<template>
<div class="container">
    <el-card>
        <el-form :model="ruleForm" size="small" label-width="100px" :rules="rules" ref="myRefForm">
            <el-row :gutter="35">
                <el-col class="mb20">
                    <el-form-item label="标题" prop="title">
                        <el-input v-model="ruleForm.title" placeholder="请输入文章标题" clearable></el-input>
                    </el-form-item>
                </el-col>
                <el-col class="mb20">
                    <el-form-item label="文章详情" prop="contentHtml">
                        <ckeditor v-model="ruleForm.contentHtml"></ckeditor>
                    </el-form-item>
                </el-col>
                <el-col class="mb20">
                    <el-form-item label="缩略图" prop="thumbnail">
                        <el-image
                            style="width: 100px; height: 100px"
                            :src="ruleForm.thumbnailUrl"
                            :fit="fit"></el-image>
                    </el-form-item>
                    <el-form-item>
                        <el-link type="primary" @click="onThumbnailDialogOpen">选择图片</el-link>
                    </el-form-item>
                </el-col>
                <el-col class="mb20">
                    <el-form-item label="文章摘要" prop="summary">
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
                    <el-form-item label="排序" prop="sortNum">
                        <el-input v-model="ruleForm.sortNum" type="number" placeholder="请输入排序序号" clearable></el-input>
                    </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
                    <el-form-item label="外链" prop="outLink">
                        <el-input v-model="ruleForm.outLink" placeholder="请输入外链" clearable></el-input>
                    </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
                    <el-form-item label="模板" prop="suffix">
                        <el-input v-model="ruleForm.suffix" placeholder="请输入文章模板后缀" clearable></el-input>
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
                <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
                    <el-form-item label="分类" prop="categories">
                        <el-cascader ref="categoryCascader" v-model="ruleForm.articleCategory" :options="categories" :props="{ multiple: true, label: 'label', value: 'id', children: 'children' }" collapse-tags clearable />
                    </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
                    <el-form-item label="标签" prop="tags">
                        <el-select
                            v-model="ruleForm.articleTag"
                            class="w100"
                            multiple
                            filterable
                            allow-create
                            default-first-option
                            placeholder="可直接输入标签名称">
                            <el-option v-for="item in tags" :key="item.id" :label="item.tagName" :value="item.tagName"></el-option>
                        </el-select>
                    </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
                    <el-form-item label="附件" prop="attachTitle">
                        <el-input v-model="ruleForm.attachTitle" readonly></el-input>
                    </el-form-item>
                    <el-form-item>
                        <el-link type="primary" @click="onAttachlDialogOpen">选择附件</el-link>
                    </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
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
    <AttachDialog ref="thumbnailDialogRef" @attachHandler="getSelectThumbnail" :fileType="fileType"/>
    <AttachDialog ref="attachDialogRef" @attachHandler="getSelectAttach"/>
</div>
</template>

<script lang="ts">
import { toRefs, ref, reactive, getCurrentInstance, onMounted } from 'vue';
import { ElMessage } from 'element-plus';
import { useRoute } from 'vue-router';
import AttachDialog from '/@/components/attach/index.vue';
import { addArticle, getArticle, getArticleCategoryList, getArticleTagList } from '/@/api/article/index';
import qs from 'qs';
import CKEditor from "/@/components/ckeditor/index.vue";

export default {
	name: 'articleWrite',
    components: {
        AttachDialog,
        ckeditor: CKEditor
    },
	setup(props, ctx) {
        const thumbnailDialogRef = ref();
        const attachDialogRef = ref();
        const categoryCascaderRef = ref();
        const route = useRoute();
        const { proxy } = getCurrentInstance() as any;
		const state = reactive({
            fileType: "image",
            fit: "fill",
            row: null,
            isShowDialog: false,
            params: {},
            categories: [],
            tags: [],
            ruleForm: {
                title: '',
                commentEnable: true,
                contentHtml: '',
                summary: '',
                seoKeywords: '',
                seoDescription: '',
                outLink: '',
                sortNum: 0,
                thumbnail: '',
                suffix: '',
                status: 'publish'
            },
            rules: {
				"title": { required: true, message: '请输入文章标题', trigger: 'blur' },
                "contentHtml": { required: true, message: '请输入文章详情', trigger: 'blur' },
                "thumbnail": { required: true, message: '请选择缩略图', trigger: 'blur' },
                "summary": { required: true, message: '请输入文章摘要', trigger: 'blur' },
                "seoKeywords": { required: true, message: '请输入SEO关键词', trigger: 'blur' },
                "seoDescription": { required: true, message: '请输入SEO描述', trigger: 'blur' },
                "status": { required: true, message: '请选择发布状态', trigger: 'blur' },
			},
		});

        //获取文章分类跟标签
        const getCategoryList = () => {
            getArticleCategoryList().then((res) => {
                state.categories = res.data;
            })
            getArticleTagList().then(res => {
                res.data.forEach(item => {
                     state.tags.push(item);
                });
            })
        }

        const onSubmit = () => {
            proxy.$refs['myRefForm'].validate((valid: any) => {
				if (valid) {
                    // categoryCascaderRef.value.getCheckedNodes(true).map(item => {
                    //     state.ruleForm.articleCategory.push(item.value);
                    // });
					let params = qs.stringify(state.ruleForm, {arrayFormat: 'repeat'});
					addArticle(params).then((res) => {
                        state.ruleForm.id = res.data;
						ElMessage.success("保存成功");
					}).catch((res) => {
						ElMessage({showClose: true, message: res.message ? res.message : '系统错误' , type: 'error'});
					})
				}
			});
        };

        const getArticleInfo = (id: string) => {
            getArticle(id).then((res) => {
                state.ruleForm = res.data;
            })
        }

        const onEditorReady = (editor) => {
            console.log(editor);
        };

        //打开缩略图弹出框
		const onThumbnailDialogOpen = () => {
			thumbnailDialogRef.value.openDialog(1);
		};

        //打开附件弹出框
		const onAttachlDialogOpen = () => {
			attachDialogRef.value.openDialog(1);
		};

        //获取弹出框选中的图片
		const getSelectThumbnail = (value) => {
			state.ruleForm.thumbnail = value[0].filePath;
            state.ruleForm.thumbnailUrl = value[0].path;
		};

        //获取弹出框选中的附件
        const getSelectAttach = (value) => {
			state.ruleForm.attachId = value[0].id;
            state.ruleForm.attachTitle = value[0].fileName;
		};

        onMounted(() => {
            state.params = route;
            getCategoryList();
            let articleId = state.params.query.id;
            if(articleId) {
                getArticleInfo(articleId);
            }
        });

		return {
            thumbnailDialogRef,
            attachDialogRef,
            categoryCascaderRef,
            onSubmit,
            onThumbnailDialogOpen,
            onAttachlDialogOpen,
            getSelectThumbnail,
            getSelectAttach,
            getCategoryList,
            getArticleInfo,
            onEditorReady,
			...toRefs(state),
		};
	},
};

</script>

<style lang="scss">
.ck-content { height:500px; }
</style>