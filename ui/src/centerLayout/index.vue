<template>
  <el-container style="height: 100vh;">
    <el-header class="center-layout-header">
      <span class="center-layout-header-title">个人中心</span>
      <div class="center-layout-header-tools">
        <span @click="backDashboard">Admin</span>
      </div>
    </el-header>
    <el-container>
      <el-aside width="200px" class="center-layout-aside">
        <el-menu class="center-layout-menu">
          <el-menu-item @click="linkTo('/center/centerHome')">首页</el-menu-item>
        </el-menu>
      </el-aside>
      <el-main>
        <el-scrollbar>
          <router-view />
        </el-scrollbar>
      </el-main>
    </el-container>
  </el-container>
</template>

<script lang="ts">
import { defineComponent } from 'vue'
import { useRouter } from 'vue-router';
export default defineComponent({
  components: {},
  setup() {
    const router = useRouter();
    const linkTo = (path: string) => router.push({ path });
    const backDashboard = () => linkTo("/center/centerHome");
    return {
      linkTo,
      backDashboard
    }
  },
})
</script>

<style lang="scss">
.center-layout-header {
  background-color: #409eff;
  color: white;
  line-height: 60px;
  .center-layout-header-title {
    font-size: 22px;
  }
  .center-layout-header-tools {
    float: right;
    span {
      cursor: pointer;
      padding: 0 10px;
    }
  }
}
.center-layout-aside {
  background-color: #eee;
  .center-layout-menu {
    width: 200px;
  }
}
</style>