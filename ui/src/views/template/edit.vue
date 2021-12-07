<template>
<div class="container">
    <el-card>
        <el-form :model="ruleForm" size="small" label-width="100px" :rules="rules" ref="myRefForm">
            <el-row :gutter="35">
                <el-col :sm="5" class="mb20">
                    <div class="tree-container">
                        <el-card shadow="hover" header="模板树">
                            <div v-loading="treeLoading">
                                <el-tree :data="treeTableData" show-checkbox node-key="id" ref="treeTable" :props="treeDefaultProps" @check="onCheckTree">
                                    <template #default="{ node, data }">
                                        <span class="tree-custom-node">
                                            <span style="flex: 1">{{ node.label }}</span>
                                            <span v-if="data.isShow" style="flex: 1; display: flex">
                                                <span type="text" size="mini" style="flex: 1">{{ data.label1 }}</span>
                                                <span type="text" size="mini" style="flex: 1">{{ data.label2 }}</span>
                                            </span>
                                        </span>
                                    </template>
                                </el-tree>
                            </div>
                        </el-card>
                    </div>
                </el-col>
                <el-col :sm="7" class="mb20">
                    <v-ace-editor
                            v-model:value="content"
                            lang="html"
                            theme="chrome"
                            @init="editorInit"
                            style="height: 550px" />
                </el-col>
            </el-row>    
        </el-form>
        <el-button type="primary" @click="onSubmit" size="small">保 存</el-button>
    </el-card>
</div>
</template>

<script lang="ts">
import { toRefs, reactive, getCurrentInstance, onMounted } from 'vue';
import { ElMessage } from 'element-plus';
import { useRoute } from 'vue-router';
import { addArticle, getArticleCategoryList, getArticle } from '/@/api/article/index';
import qs from 'qs';
import { VAceEditor } from 'vue3-ace-editor';
import 'ace-builds/src-noconflict/mode-text';
import 'ace-builds/src-noconflict/mode-html';
import 'ace-builds/src-noconflict/theme-chrome';

export default {
	name: 'templateEdit',
    components: {
        VAceEditor
    },
	setup() {
        const route = useRoute();
        const { proxy } = getCurrentInstance() as any;
		const state = reactive({
            treeLoading: false,
			treeTableData: [],
            content: '',
            params: {},
            categories: [],
            tags: [],
            tableData: {
				data: [
					{
						date: '2016-05-02',
						name: '1号实验室',
					},
					{
						date: '2016-05-04',
						name: '2号实验室',
						address: '温度30℃',
					},
					{
						date: '2016-05-01',
						name: '3号实验室',
						address: '湿度57%RH',
					},
                    {
						date: '2016-05-022016-05-02',
						name: '1号实验室',
						address: '烟感2.1%OBS/M',
					},
					{
						date: '2016-05-042016-05-02',
						name: '2号实验室',
						address: '温度30℃',
					},
					{
						date: '2016-05-01',
						name: '3号实验室',
						address: '湿度57%RH',
					},
				],
			},
            ruleForm: {
                title: '',
                commentEnable: 1,
                contentHtml: '',
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

        const editorInit = () => {
            console.log("====editorInit");
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
            editorInit,
			...toRefs(state),
		};
	},
};

</script>

<style lang="scss">
.ck-content { height:500px; }
</style>