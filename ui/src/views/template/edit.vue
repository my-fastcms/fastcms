<template>
<div class="container">
    <el-card>
        <el-form :model="ruleForm" size="small" label-width="100px" :rules="rules" ref="myRefForm">
            <el-row :gutter="35">
                <el-col :sm="5" class="mb20">
                    <div class="tree-container">
                        <el-card shadow="hover" header="模板文件树">
                            <div v-loading="treeLoading">
                                <el-tree :data="treeTableData" 
                                    :default-expand-all="true" 
                                    node-key="id" 
                                    :props="treeDefaultProps" 
                                    style="height: 550px;overflow: auto" 
                                    ref="treeTable">
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
import { getTemplateFileTree } from '/@/api/template/index';
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
        const { proxy } = getCurrentInstance() as any;
		const state = reactive({
            treeLoading: false,
			treeTableData: [],
            treeDefaultProps: {
				children: 'children',
				label: 'label',
			},
            content: '',
            params: {},
            categories: [],
            tags: [],
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

        //
        const getFileTree = () => {
            getTemplateFileTree().then((res) => {
                state.treeTableData = res.data;
            })
        }

        const onSubmit = () => {
            proxy.$refs['myRefForm'].validate((valid: any) => {
				if (valid) {
					// let params = qs.stringify(state.ruleForm, {arrayFormat: 'repeat'});
					
				}
			});
        };

        // const getArticleInfo = (id: string) => {
        //     getArticle(id).then((res) => {
        //         state.ruleForm = res.data;
        //     })
        // }

        const editorInit = () => {
            console.log("====editorInit");
        }

        onMounted(() => {
            getFileTree();
        });

		return {
            onSubmit,
            getFileTree,
            editorInit,
			...toRefs(state),
		};
	},
};

</script>

<style lang="scss">
.ck-content { height:500px; }
</style>