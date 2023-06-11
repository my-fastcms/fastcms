<template>
<div class="container">
    <el-card>
        <el-upload 
            class="upload-btn"
            :action="state.uploadUrl"
            name="files"
            :data="state.uploadParam"
            multiple
            :headers="state.headers"
            :show-file-list="false"
            :on-success="uploadSuccess"
            :on-exceed="onHandleExceed"
            :on-error="onHandleUploadError"
            :before-upload="onBeforeUpload"
            :limit="state.limit">
            <el-button size="default" type="primary"><el-icon><ele-Plus /></el-icon>上传模板文件</el-button>
            <div class="el-upload__tip" v-html="state.uploadParam.dirName"></div>
        </el-upload>
        <el-form style="padding-top: 5px;" size="default" label-width="100px" ref="myRefForm">
            <el-row :gutter="35">
                <el-col :sm="5" class="mb20">
                    <div class="tree-container">
                        <el-card shadow="hover" header="模板文件树">
                            <div v-loading="state.treeLoading">
                                <el-tree :data="state.treeTableData" 
                                    :default-expand-all="false" 
                                    node-key="filePath" 
                                    :props="state.treeDefaultProps" 
                                    @node-click="onNodeClick"
                                    style="height: 550px;overflow: auto;" 
                                    ref="treeTable">
                                </el-tree>
                            </div>
                        </el-card>
                    </div>
                </el-col>
                <el-col :sm="19" class="mb20">
                    <Codemirror
                            ref="codeMirror"
                            v-model="state.content" 
                            :style="{ height: '100%', width: '100%' }" 
                            :autofocus="true"
                            @change="onChange"
                            v-bind="$attrs"
                            :extensions="extensions" />
                        <div class="mb15" style="padding-top: 3px;">
                            <el-button type="primary" @click="onSaveFile" size="default">保 存</el-button>
                            <el-button type="danger" @click="onDelFile" size="default" v-if="state.content != ''">删 除</el-button>
                        </div>
                </el-col>
            </el-row>    
        </el-form>
    </el-card>
</div>
</template>

<script lang="ts" name="templateEdit" setup>
import { reactive, onMounted, ref } from 'vue';
import { ElMessageBox, ElMessage } from 'element-plus';
import { Local } from '/@/utils/storage';
import { TemplateApi } from '/@/api/template/index';
import { Codemirror } from "vue-codemirror";
import { html } from "@codemirror/lang-html";
import { oneDark } from "@codemirror/theme-one-dark";

const codeMirror = ref()
const extensions = [html(), oneDark];

const templateApi = TemplateApi();
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
    templateApi.getTemplateFileTree().then((res) => {
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

    templateApi.saveTemplateFile({"filePath": state.currEditFile, "fileContent": state.content}).then(() => {
        ElMessage.success("保存成功");
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
        templateApi.delTemplateFile(state.currEditFile).then(() => {
            ElMessage.success("删除成功");
            state.content = '';
            getFileTree();
        }).catch((res) => {
            ElMessage.error(res.message);
        })
    })
    .catch(() => {});
}

const onNodeClick = (node: any) => {
    if(node.children == null) {
        state.currEditFile = node.filePath;
        templateApi.getTemplateFile(node.filePath).then((res: any) => {
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
const onChange = (value: string) => {
    // console.log("value:" + value)
    state.content = value;
}
onMounted(() => {
    getFileTree();
});
</script>

<style lang="scss">
.CodeMirror-scroll {
  overflow: scroll !important;
  margin-bottom: 0;
  margin-right: 0;
  padding-bottom: 0;
  height: 100%;
  outline: none;
  position: relative;
  border: 1px solid #dddddd;
}
.code-mirror{
  font-size : 13px;
  line-height : 150%;
  text-align: left;
}
</style>