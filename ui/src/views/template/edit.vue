<template>
<div class="container">
    <el-card>
        <el-upload 
            class="upload-btn"
            :action="uploadUrl"
            name="files"
            :data="uploadParam"
            multiple
            :headers="headers"
            :show-file-list="false"
            :on-success="uploadSuccess"
            :on-exceed="onHandleExceed"
            :on-error="onHandleUploadError"
            :before-upload="onBeforeUpload"
            :limit="limit">
            <el-button size="small" type="primary">上传模板文件</el-button>
            <div slot="tip" class="el-upload__tip" v-html="uploadParam.dirName"></div>
        </el-upload>
        <el-form size="small" label-width="100px" ref="myRefForm">
            <el-row :gutter="35">
                <el-col :sm="5" class="mb20">
                    <div class="tree-container">
                        <el-card shadow="hover" header="模板文件树">
                            <div v-loading="treeLoading">
                                <el-tree :data="treeTableData" 
                                    :default-expand-all="true" 
                                    node-key="filePath" 
                                    :props="treeDefaultProps" 
                                    @node-click="onNodeClick"
                                    style="height: 550px;overflow: auto;" 
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
                            :options="aceOptions"
                            style="height: 550px;width: 1000px; overflow: auto;" />
                        <div class="mb15">
                            <el-button type="primary" @click="onSaveFile" size="small">保 存</el-button>
                            <el-button type="danger" @click="onDelFile" size="small" v-if="content != ''">删 除</el-button>
                        </div>
                </el-col>
            </el-row>    
        </el-form>
    </el-card>
</div>
</template>

<script lang="ts">
import { toRefs, reactive, onMounted } from 'vue';
import { ElMessageBox, ElMessage } from 'element-plus';
import { Local, Session } from '/@/utils/storage';
import { getTemplateFileTree, getTemplateFile, saveTemplateFile, delTemplateFile } from '/@/api/template/index';
import { VAceEditor } from 'vue3-ace-editor';
import 'ace-builds/src-noconflict/mode-text';
import 'ace-builds/src-noconflict/mode-html';
import 'ace-builds/src-noconflict/mode-javascript';
import 'ace-builds/src-noconflict/mode-css';
import 'ace-builds/src-noconflict/theme-chrome';
import qs from 'qs';

export default {
	name: 'templateEdit',
    components: {
        VAceEditor
    },
	setup() {
		const state = reactive({
            treeLoading: false,
			treeTableData: [],
            treeDefaultProps: {
				children: 'children',
				label: 'label',
                filePath: 'filePath'
			},
            currEditFile: "",
            content: '',
            aceOptions: {
                enableBasicAutocompletion: true,
                enableSnippets: true,
                enableLiveAutocompletion: false
            },
            limit: 3,
			uploadUrl: import.meta.env.VITE_API_URL + "/admin/template/files/upload",
			headers: {"Authorization": Local.get('token')},
            uploadParam: {
                dirName: ''
            },
		});

        //
        const getFileTree = () => {
            getTemplateFileTree().then((res) => {
                state.treeTableData = res.data;
            })
        }

        const onSaveFile = () => {
            if(state.content == null || state.content == '') {
                ElMessage.warning("文件内容不能为空");
                return;
            }
            if(state.currEditFile == '') {
                ElMessage.warning("请选择需要编辑的文件");
                return;
            }

            saveTemplateFile(qs.stringify({"filePath": state.currEditFile, "fileContent": state.content})).then(() => {
                ElMessage.success("保存成功");
            }).catch((res) => {
                ElMessage.error(res.message);
            })

        };

        const onDelFile = () => {
            if(state.currEditFile == '') {
                ElMessage.warning("请选择需要删除的文件");
                return;
            }

            ElMessageBox.confirm('此操作将永久删除['+state.currEditFile+']文件, 是否继续?', '提示', {
				confirmButtonText: '删除',
				cancelButtonText: '取消',
				type: 'warning',
			}).then(() => {
				delTemplateFile(state.currEditFile).then(() => {
                    ElMessage.success("删除成功");
                    state.content = '';
                    getFileTree();
                }).catch((res) => {
                    ElMessage.error(res.message);
                })
			})
			.catch(() => {});
        }

        const onUploadFile = () => {

        }

        const onNodeClick = (node: any) => {
            if(node.children == null) {
                state.currEditFile = node.filePath;
                getTemplateFile(node.filePath).then((res: any) => {
                    state.content = res.data;
                }).catch((res) => {
                    ElMessage.error(res.message);
                }) 
            }else {
                state.uploadParam.dirName = node.filePath;
                state.currEditFile = '';
                state.content = '';
            }
        }

        const editorInit = () => {
            console.log("====editorInit");
        }

        const uploadSuccess = () => {
            ElMessage.success("上传成功");
			getFileTree();
		}
		const onHandleExceed = () => {
			ElMessage.error("上传文件数量不能超过 "+state.limit+" 个!");
		}
		const onHandleUploadError = () => {
			ElMessage.error("上传失败");
		}
        const onBeforeUpload = () => {
            if(state.uploadParam.dirName == '') {
                ElMessage.warning("请选择上传目录");
                return false;
            }
        }

        onMounted(() => {
            getFileTree();
        });

		return {
            onSaveFile,
            onDelFile,
            onUploadFile,
            getFileTree,
            onNodeClick,
            editorInit,
            onHandleExceed,
			onHandleUploadError,
			uploadSuccess,
            onBeforeUpload,
			...toRefs(state),
		};
	},
};

</script>

<style lang="scss">
.ck-content { height:500px; }
</style>