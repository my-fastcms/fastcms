<template>
  <div class="container">
    <el-card>
      <el-form :model="state.ruleForm" label-width="100px" :rules="state.rules" ref="myRefForm">
        <el-row :gutter="35">
          <el-col class="mb20">
            <el-form-item label="标题" prop="title">
              <el-input v-model="state.ruleForm.title" placeholder="请输入文章标题" clearable></el-input>
            </el-form-item>
          </el-col>
          <el-col class="mb20">
            <el-form-item label="文章详情" prop="contentHtml">
              <CKEditor v-model="state.ruleForm.contentHtml" :isClient="true"></CKEditor>
            </el-form-item>
          </el-col>
          <el-col class="mb20">
            <el-form-item label="缩略图" prop="thumbnail">
              <el-image style="width: 100px; height: 100px" :src="state.ruleForm.thumbnailUrl" :fit="state.fit"></el-image>
            </el-form-item>
            <el-form-item>
              <el-link type="primary" @click="onThumbnailDialogOpen">选择图片</el-link>
            </el-form-item>
          </el-col>
          <el-col class="mb20">
            <el-form-item label="文章摘要" prop="summary">
              <el-input
                v-model="state.ruleForm.summary"
                type="textarea"
                :rows="2"
                placeholder="请输入文章摘要"
                clearable
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
            <el-form-item label="SEO关键词" prop="seoKeywords">
              <el-input
                type="textarea"
                :rows="2"
                v-model="state.ruleForm.seoKeywords"
                placeholder="请输入seo关键词"
                clearable
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
            <el-form-item label="SEO描述" prop="seoDescription">
              <el-input
                type="textarea"
                :rows="2"
                v-model="state.ruleForm.seoDescription"
                placeholder="请输入SEO描述"
                clearable
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
            <el-form-item label="外链" prop="outLink">
              <el-input v-model="state.ruleForm.outLink" placeholder="请输入外链" clearable></el-input>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
            <el-form-item label="允许评论" prop="commentEnable">
              <el-switch v-model="state.ruleForm.commentEnable" active-color="#13ce66"></el-switch>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
            <el-form-item label="分类" prop="categories">
              <el-cascader
                ref="categoryCascader"
                v-model="state.ruleForm.articleCategory"
                :options="state.categories"
                :props="{ multiple: true, label: 'label', value: 'id', children: 'children' }"
                collapse-tags
                clearable
              />
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
            <el-form-item label="标签" prop="tags">
              <el-select
                v-model="state.ruleForm.articleTag"
                class="w100"
                multiple
                filterable
                default-first-option
                placeholder="请选择标签"
              >
                <el-option
                  v-for="item in state.tags"
                  :key="item.id"
                  :label="item.tagName"
                  :value="item.tagName"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24" class="mb20">
            <el-form-item label="附件" prop="attachTitle">
              <el-input v-model="state.ruleForm.attachTitle" readonly></el-input>
            </el-form-item>
            <el-form-item>
              <el-link type="primary" @click="onAttachlDialogOpen">选择附件</el-link>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-form-item>
            <el-button type="primary" @click="onSubmit" size="default">保 存</el-button>
          </el-form-item>
        </el-row>
      </el-form>
    </el-card>
    <AttachDialog ref="thumbnailDialogRef" @attachHandler="getSelectThumbnail" :isClient="true"/>
    <AttachDialog ref="attachDialogRef" @attachHandler="getSelectAttach" :isClient="true"/>
  </div>
</template>

<script lang="ts" name="clientArticleWrite" setup>
import { ref, reactive, onMounted } from 'vue';
import { ElMessage } from 'element-plus';
import { useRoute } from 'vue-router';
import AttachDialog from '/@/components/attach/index.vue';
import { ClientArticleApi } from '/@/api/article/client';
import qs from 'qs';
import CKEditor from "/@/components/ckeditor/index.vue";

const clientArticleApi = ClientArticleApi();
const thumbnailDialogRef = ref();
const attachDialogRef = ref();
const myRefForm = ref();

// const categoryCascaderRef = ref();
const route = useRoute();
const state = reactive({
  fit: "fill",
  row: null,
  isShowDialog: false,
  params: {},
  categories: [],
  tags: [],
  ruleForm: {
    title: '',
    commentEnable: 1,
    contentHtml: '',
    summary: '',
    seoKeywords: '',
    seoDescription: '',
    outLink: '',
    sortNum: 0,
    thumbnail: '',
    suffix: '',

    articleCategory: null,
    articleTag: null,
    attachTitle: '',
    thumbnailUrl: '',
  },
  rules: {
    "title": { required: true, message: '请输入文章标题', trigger: 'blur' },
    "contentHtml": { required: true, message: '请输入文章详情', trigger: 'blur' },
    "thumbnail": { required: true, message: '请选择缩略图', trigger: 'blur' },
    "summary": { required: true, message: '请输入文章摘要', trigger: 'blur' },
    "seoKeywords": { required: true, message: '请输入SEO关键词', trigger: 'blur' },
    "seoDescription": { required: true, message: '请输入SEO描述', trigger: 'blur' },
  },
});

//获取文章分类跟标签
const getCategoryList = () => {
  clientArticleApi.getArticleCategoryList().then((res) => {
    state.categories = res.data;
  })
  clientArticleApi.getArticleTagList().then(res => {
    res.data.forEach(item => {
      state.tags.push(item);
    });
  })
}

const onSubmit = () => {
  myRefForm.value.validate((valid: any) => {
    if (valid) {
      // categoryCascaderRef.value.getCheckedNodes(true).map(item => {
      //     state.ruleForm.articleCategory.push(item.value);
      // });
      let params = qs.stringify(state.ruleForm, { arrayFormat: 'repeat' });
      clientArticleApi.addArticle(params).then((res) => {
        state.ruleForm.id = res.data;
        ElMessage.success("保存成功");
      }).catch((res) => {
        ElMessage({ showClose: true, message: res.message ? res.message : '系统错误', type: 'error' });
      })
    }
  });
};

const getArticleInfo = (id: string) => {
  clientArticleApi.getArticle(id).then((res) => {
    state.ruleForm = res.data;
  })
}

// const onEditorReady = (editor) => {
//   console.log(editor);
// };

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
  if (articleId) {
    getArticleInfo(articleId);
  }
});

</script>

<style lang="scss">
.ck-content {
  height: 500px;
}
</style>
