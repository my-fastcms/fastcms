<script lang="ts">
import {defineComponent, onMounted, ref} from "vue";

import connect from "./imgPlugin/connect";

import ClassicEditor from '@ckeditor/ckeditor5-editor-classic/src/classiceditor';
import Essentials from '@ckeditor/ckeditor5-essentials/src/essentials';
import Paragraph from '@ckeditor/ckeditor5-paragraph/src/paragraph';
import Italic from "@ckeditor/ckeditor5-basic-styles/src/italic";
import Image from '@ckeditor/ckeditor5-image/src/image';

import CKEditorInspector from '@ckeditor/ckeditor5-inspector';

import ImgCustom from './imgPlugin/main';

import imgResWin from "./imgResWin.vue";

export default defineComponent({
  components: {
    imgResWin
  },
  setup() {
    let showImgResWin = ref(false);
    onMounted(() => {
      connect.showWinControl = showImgResWin;
      ClassicEditor
          .create(document.querySelector('#imgEditor'), {
            plugins: [Essentials, Paragraph, Italic, Image, ImgCustom],
            toolbar: ["italic", ImgCustom.pluginName]
          })
      .then(editor => {
        CKEditorInspector.attach(editor);
      })
      .catch(error => {
        console.log(error);
      });
    });
    return {
      showImgResWin
    }
  }
});
</script>

<template>
  <div id="imgEditor"></div>
  <imgResWin v-model:show="showImgResWin" />
</template>

<style></style>
