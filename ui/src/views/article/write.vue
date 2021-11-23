<template>
<div class="container">
    <el-card>
        <el-form :model="ruleForm" size="small" label-width="80px" :rules="rules" ref="myRefForm">
            <el-row :gutter="35">
                <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
                    <el-form-item label="标题" prop="title">
                        <el-input v-model="ruleForm.title" placeholder="请输入标题" clearable></el-input>
                    </el-form-item>
                </el-col>
                <el-col class="mb20">
                    <el-form-item label="文章详情">
                        <ckeditor :editor="editor" v-model="editorData" :config="editorConfig" class="ck-content"></ckeditor>
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
import { toRefs, reactive } from 'vue';
import ClassicEditor from '@ckeditor/ckeditor5-build-classic';


export default {
	name: 'articleWrite',
    components: {
        // Use the <ckeditor> component in this view.
        ckeditor: ClassicEditor.component
    },
	setup() {
		const state: any = reactive({
            ruleForm: {
                title: ''
            },
			editor: ClassicEditor,
            editorData: '<p>Content of the editor.</p>',
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
				"title": { required: true, message: '请输入标题', trigger: 'blur' },
			},
		});

        const onSubmit = () => {
            console.log("content:" + state.editorData);
        };

		return {
            onSubmit,
			...toRefs(state),
		};
	},
};

</script>

<style lang="scss">
.ck-content { height:500px; }
</style>