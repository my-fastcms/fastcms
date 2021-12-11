<script lang="ts">
import {defineComponent, ref, reactive, onMounted } from "vue";
import insertImage from "./imgPlugin/insertImage";
import connect from "./imgPlugin/connect";
export default defineComponent({
  props: {
    show: {
      default: false,
      type: Boolean
    },
    emits: [
      'update:show'
    ]
  },
  setup(props, { emit }) {
    let selectedIndex = ref(1);
    let changeSelect = function(index) {
      selectedIndex.value = index;
    }
    let imgList = reactive([]);
    onMounted(() => {
      const mockData = [{
        src: "https://img0.baidu.com/it/u=2089938236,2600489549&fm=26&fmt=auto",
      },{
        src: "https://img2.baidu.com/it/u=2840876380,2046257499&fm=26&fmt=auto",
      },{
        src: "https://img2.baidu.com/it/u=3745443056,3700782634&fm=26&fmt=auto",
      }];
      // 模拟请求数据
      setTimeout(() => {
        imgList.push(...mockData);
      }, 2000);
    });
    const confirm = function() {
      insertImage(connect.editorObj.model, imgList[selectedIndex.value]);
      emit("update:show", false);
    }
    return {
      selectedIndex,
      changeSelect,
      imgList,
      confirm
    }
  }
});
</script>

<template>
  <div class="imgResWin" v-show="show">
    <div class="imgResWinCon">
      <div class="imgResWinConImgs">
        <div v-for="(img, index) in imgList" :key="img.src" @click="changeSelect(index)" :class="{ selected: selectedIndex === index }">
          <img :src="img.src" />
        </div>
      </div>
      <span class="confirm" @click="confirm">确定</span>
    </div>
  </div>
</template>


<style scoped>
  .imgResWin {
    position: fixed;
    top: 0;
    left: 0;
    right: 0;
    bottom: 0;
    background-color: rgba(0, 0, 0, 0.6);
    z-index: 50;
  }
  .imgResWinCon {
    position: absolute;
    top: 100px;
    left: 100px;
    right: 100px;
    bottom: 100px;
    background-color: #fff;
    padding: 50px;
  }
  .imgResWinConImgs:after {
    content: "";
    display: block;
    clear: both;
  }
  .imgResWinConImgs > div {
    margin: 0 50px 50px 0;
    width: 200px;
    height: 150px;
    cursor: pointer;
    float: left;
  }
  .selected {
    outline: #409eff 10px solid;
  }
  .imgResWinConImgs img {
    height: 100%;
    width: 100%;
  }
  .confirm {
    margin-top: 100px;
    background-color: #409eff;
    font-size: 20px;
    padding: 15px 20px;
    border-radius: 6px;
    text-align: center;
    display: inline-block;
    cursor: pointer;
    color: #fff;
  }
</style>
