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
                        <ckeditor :editor="editor" v-model="ruleForm.contentHtml" :config="editorConfig" class="ck-content"></ckeditor>
                    </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
                    <el-form-item label="缩略图" prop="thumbnail">
                        <el-input v-model="ruleForm.thumbnail" placeholder="请选择缩略图" clearable></el-input>
                    </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
                    <el-form-item label="文章摘要" prop="summary">
                        <el-input v-model="ruleForm.summary" placeholder="请输入文章摘要" clearable></el-input>
                    </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
                    <el-form-item label="SEO关键词" prop="seoKeywords">
                        <el-input v-model="ruleForm.seoKeywords" placeholder="请输入seo关键词" clearable></el-input>
                    </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
                    <el-form-item label="SEO描述" prop="seoDescription">
                        <el-input v-model="ruleForm.seoDescription" placeholder="请输入SEO描述" clearable></el-input>
                    </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
                    <el-form-item label="排序" prop="sortNum">
                        <el-input v-model="ruleForm.sortNum" placeholder="请输入排序序号" clearable></el-input>
                    </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
                    <el-form-item label="外链" prop="outLink">
                        <el-input v-model="ruleForm.outLink" placeholder="请输入外链" clearable></el-input>
                    </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
                    <el-form-item label="模板样式" prop="suffix">
                        <el-input v-model="ruleForm.suffix" placeholder="请选择模板样式" clearable></el-input>
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
                        <el-select
                            v-model="ruleForm.articleCategory"
                            multiple
                            filterable
                            allow-create
                            default-first-option
                            placeholder="请选择文章分类">
                            <el-option v-for="item in categories" :key="item.id" :label="item.title" :value="item.id"></el-option>
                        </el-select>
                    </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
                    <el-form-item label="标签" prop="tags">
                        <el-select
                            v-model="ruleForm.articleTag"
                            multiple
                            filterable
                            allow-create
                            default-first-option
                            placeholder="请选择文章标签">
                            <el-option v-for="item in tags" :key="item.id" :label="item.title" :value="item.id"></el-option>
                        </el-select>
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
</div>
</template>

<script lang="ts">
import { toRefs, reactive, getCurrentInstance, onMounted } from 'vue';
import ClassicEditor from '@ckeditor/ckeditor5-build-classic';
import { ElMessage } from 'element-plus';
import { useRoute } from 'vue-router';
import { addArticle, getArticleCategoryList, getArticle } from '/@/api/article/index';
import qs from 'qs';

export default {
	name: 'articleWrite',
    components: {
        // Use the <ckeditor> component in this view.
        ckeditor: ClassicEditor.component
    },
	setup() {
        const route = useRoute();
        const { proxy } = getCurrentInstance() as any;
		const state: any = reactive({
            params: {},
            categories: [],
            tags: [],
            ruleForm: {
                title: '',
                commentEnable: 1,
                contentHtml: '',
            },
			editor: ClassicEditor,
            editorConfig: {
                //toolbar: [ 'heading', '|', 'bold', 'italic', 'link', 'bulletedList', 'numberedList', 'blockQuote' ],
                heading: {
                    options: [
                       // { model: 'paragraph', title: 'Paragraph', class: 'ck-heading_paragraph' },
                       // { model: 'heading1', view: 'h1', title: 'Heading 1', class: 'ck-heading_heading1' },
                       // { model: 'heading2', view: 'h2', title: 'Heading 2', class: 'ck-heading_heading2' }
                    ]
                }
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
                res.data.forEach(item => {
                    if(item.type == 'tag') {
                        state.tags.push(item);
                    }else if(item.type == 'category') {
                        state.categories.push(item);
                    }
                })
            })
        }

        const onSubmit = () => {
            proxy.$refs['myRefForm'].validate((valid: any) => {
				if (valid) {
					let params = qs.stringify(state.ruleForm, {arrayFormat: 'repeat'});
					addArticle(params).then((res) => {
                        state.ruleForm.id = res;
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

        onMounted(() => {
            state.params = route;
            getCategoryList();
            let articleId = state.params.query.id;
            if(articleId) {
                getArticleInfo(articleId);
            }
        });

		return {
            onSubmit,
            getCategoryList,
            getArticleInfo,
			...toRefs(state),
		};
	},
};

</script>

<style lang="scss">
.ck-content { height:500px; }
</style>