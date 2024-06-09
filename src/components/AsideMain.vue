<template>
  <el-menu :router="true">
    <el-menu-item index="/home">
      <router-link :to="{ name: 'Home' }">
        <i class="el-icon-s-home"></i>
        <span slot="title">首页</span>
      </router-link>
    </el-menu-item>
    <el-menu-item index="/one">
      <router-link :to="{ name: 'One' }">
        <i class="el-icon-menu"></i>
        <span slot="title">页面 One</span>
      </router-link>
    </el-menu-item>
  </el-menu>
</template>

<script>
export default {
  name: 'AsideMain'
};
</script>

<style scoped>
.el-menu {
  width: 100%;
}
</style>
