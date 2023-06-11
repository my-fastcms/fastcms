<script lang="ts" setup name ="fastcmsCkeditor">
import {onMounted, ref, watch, reactive} from "vue";

import connect from "./imgPlugin/connect";

// import ClassicEditor from '@ckeditor/ckeditor5-editor-classic/src/classiceditor';
import DecoupledEditor from '@ckeditor/ckeditor5-editor-decoupled/src/decouplededitor';
import Essentials from '@ckeditor/ckeditor5-essentials/src/essentials';
import UploadAdapter from '@ckeditor/ckeditor5-adapter-ckfinder/src/uploadadapter';
import Autoformat from '@ckeditor/ckeditor5-autoformat/src/autoformat';
import Bold from '@ckeditor/ckeditor5-basic-styles/src/bold';
import Code from '@ckeditor/ckeditor5-basic-styles/src/code';
import Italic from '@ckeditor/ckeditor5-basic-styles/src/italic';
import BlockQuote from '@ckeditor/ckeditor5-block-quote/src/blockquote';
import CKFinder from '@ckeditor/ckeditor5-ckfinder/src/ckfinder';
import EasyImage from '@ckeditor/ckeditor5-easy-image/src/easyimage';
import Heading from '@ckeditor/ckeditor5-heading/src/heading';
import Image from '@ckeditor/ckeditor5-image/src/image';
import ImageCaption from '@ckeditor/ckeditor5-image/src/imagecaption';
import ImageStyle from '@ckeditor/ckeditor5-image/src/imagestyle';
import ImageToolbar from '@ckeditor/ckeditor5-image/src/imagetoolbar';
import ImageUpload from '@ckeditor/ckeditor5-image/src/imageupload';
import ImageResize from '@ckeditor/ckeditor5-image/src/imageresize';
import ImageResizeEditing from '@ckeditor/ckeditor5-image/src/imageresize/imageresizeediting';
import ImageResizeHandles from '@ckeditor/ckeditor5-image/src/imageresize/imageresizehandles';
import Indent from '@ckeditor/ckeditor5-indent/src/indent';
import Link from '@ckeditor/ckeditor5-link/src/link';
import List from '@ckeditor/ckeditor5-list/src/list';
import MediaEmbed from '@ckeditor/ckeditor5-media-embed/src/mediaembed';
import Paragraph from '@ckeditor/ckeditor5-paragraph/src/paragraph';
import PasteFromOffice from '@ckeditor/ckeditor5-paste-from-office/src/pastefromoffice';
import Table from '@ckeditor/ckeditor5-table/src/table';
import TableToolbar from '@ckeditor/ckeditor5-table/src/tabletoolbar';
import TextTransformation from '@ckeditor/ckeditor5-typing/src/texttransformation';
import CloudServices from '@ckeditor/ckeditor5-cloud-services/src/cloudservices';
import SourceEditing from '@ckeditor/ckeditor5-source-editing/src/sourceediting';
import Markdown from '@ckeditor/ckeditor5-markdown-gfm/src/markdown';

// import CKEditorInspector from '@ckeditor/ckeditor5-inspector/inspector.js';

import ImgCustom from './imgPlugin/main';

import imgResWin from "./imgResWin.vue";

const props = defineProps({
  isClient: {
      type: Boolean,
      default: false,
    },
    modelValue: {
      type: String
    }
});

const emit = defineEmits(["update:modelValue"]);

let attachDialog = ref();
let ckeditorDom = ref();
let editorExample: { setData: (arg0: string | undefined) => any; } | null = null;
let isPrint = false;

const state = reactive({
  isClient: props.isClient || false
})
const editorConfig = reactive({
    language: {
        ui: 'zh-cn'
    },
    plugins: [
        SourceEditing,
        Essentials,
        UploadAdapter,
        Autoformat,
        Bold,
        Code,
        Italic,
        BlockQuote,
        CKFinder,
        CloudServices,
        EasyImage,
        Heading,
        Image,
        ImageCaption,
        ImageStyle,
        ImageToolbar,
        ImageUpload,
        ImageResize,
        ImageResizeEditing,
        ImageResizeHandles,
        Indent,
        Link,
        List,
        MediaEmbed,
        // Paragraph,
        PasteFromOffice,
        Table,
        TableToolbar,
        TextTransformation,
        // Markdown,
        ImgCustom
    ],
    toolbar: [
        'sourceEditing',
        '|',
        'heading',
        '|',
        'bold',
        'italic',
        'link',
        'bulletedList',
        'numberedList',
        '|',
        'outdent',
        'indent',
        '|',
        ImgCustom.pluginName,
        // Markdown.pluginName,
        'blockQuote',
        'insertTable',
        'mediaEmbed',
        'undo',
        'redo',
    ],
    image: {
      styles: ["alignLeft", "alignCenter", "alignRight"],
      resizeOptions: [
        {
          name: "imageResize:original",
          label: "Original",
          value: null,
        },
        {
          name: "imageResize:25",
          label: "25%",
          value: "25",
        },
        {
          name: "imageResize:50",
          label: "50%",
          value: "50",
        },
        {
          name: "imageResize:75",
          label: "75%",
          value: "75",
        },
      ],
      toolbar: [
        'imageStyle:inline',
        'imageStyle:block',
        'imageStyle:side',
        "|",
        "imageStyle:alignLeft",
        "imageStyle:alignCenter",
        "imageStyle:alignRight",
        "|",
        "imageResize",
        "imageTextAlternative",
        'toggleImageCaption',
      ]
    },
    table: {
      contentToolbar: [
        'tableColumn',
        'tableRow',
        'mergeTableCells'
      ]
    },
  })
onMounted(() => {
  connect.dialogObj = attachDialog.value;
  const ckeditorDiv = ckeditorDom.value;
  DecoupledEditor.create(ckeditorDiv.querySelector('.CKEditorContent'), editorConfig)
  .then((editor: any) => {
    const toolbar = ckeditorDiv.querySelector(".CKEditorToolbar");
    toolbar && (toolbar.innerHTML = "");
    setTimeout(() => toolbar.appendChild( editor.ui.view.toolbar.element ), 0);
    editorExample = editor;
    editor.setData(props.modelValue);
    editor.model.document.on("change", function() {
      isPrint = true;
      emit("update:modelValue", editor.getData());
    });
    // CKEditorInspector.attach(editor); // CKEditor 调试器
  })
  .catch((error: any) => {
    console.log(error);
  });
});
watch(() => props.modelValue, val => {
  if (isPrint) return isPrint = false;
  editorExample && editorExample.setData(val);
})

</script>

<template>
  <div class="CKEditor" ref="ckeditorDom">
    <div class="CKEditorBox">
      <div class="CKEditorToolbar"></div>
      <div class="CKEditorContent"></div>
    </div>
    <imgResWin ref="attachDialog" :isClient=state.isClient />
  </div>
</template>

<style>
  .ck.ck-content.CKEditorContent {
    border: #bbb 1px solid;
  }
</style>
