<template>
  <el-container style="height: 100vh;">
    <el-header class="center-layout-header">
      <span class="center-layout-header-title">个人中心</span>
      <div class="center-layout-header-tools">
        <span class="center-layout-header-tools-user" @click="backDashboard">
          <img :src="store.state.userInfos.userInfos.photo" class="center-layout-header-tools-user-photo" />
          <span>{{ store.state.userInfos.userInfos.username }}</span>
        </span>
      </div>
    </el-header>
    <el-container class="center-layout-container">
      <el-aside width="200px" class="center-layout-aside">
        <el-menu class="center-layout-menu" router>
          <el-menu-item
              v-for="menu in centerRoute.children"
              :key="menu.path"
              :index="menu.path">
            <template #title>{{ menu.meta.title }}</template>
          </el-menu-item>
        </el-menu>
      </el-aside>
      <el-scrollbar class="center-layout-main">
          <router-view />
      </el-scrollbar>
    </el-container>
  </el-container>
</template>

<script lang="ts">
import { defineComponent } from 'vue'
import { useRouter } from 'vue-router';
import { useStore } from '/@/store';
import { centerRoute } from "/@/router/center";
export default defineComponent({
  components: {},
  setup() {
    const router = useRouter();
    const linkTo = (path: string) => router.push({ path });
    const backDashboard = () => linkTo("/");
    const store = useStore();
    return {
      linkTo,
      backDashboard,
      centerRoute,
      store
    }
  },
})
</script>

<style lang="scss">
@import "../theme/common/var";
$headerHeight: 60px;
.center-layout-header {
  background-color: #fff;
  color: #333;
  height: $headerHeight;
  line-height: $headerHeight;
  border-bottom: #ddd 1px solid;
  z-index: 5;
  .center-layout-header-title {
    font-size: 22px;
  }
  .center-layout-header-tools {
    float: right;
    span {
      cursor: pointer;
      padding: 0 10px;
    }
    .center-layout-header-tools-user {
      display: inline-block;
      .center-layout-header-tools-user-photo {
        height: 30px;
        vertical-align: middle;
        border-radius: 50%;
      }
    }
  }
}
.center-layout-container {
  height: calc(100vh - $headerHeight);
  .center-layout-main {
    flex: 1;
  }
}
.center-layout-aside {
  background-color: $--bg-columnsMenuBar;
  box-shadow: 0 1px 0 #fff inset;
  .center-layout-menu {
    width: 200px;
    background-color: transparent;
  }
}
</style>
