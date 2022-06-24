<template>
  <a-layout>
    <header-view></header-view>
    <a-layout-content>
      <div class="main-container">
        <h1>404 没有找到页面</h1>
        <a-button type="primary" @click="router.push('/')">
          <icon-home />
          返回首页
        </a-button>
      </div>
    </a-layout-content>
  </a-layout>
</template>

<script lang="ts" setup>
import HeaderView from "@/views/HeaderView.vue";
import { IconHome } from "@arco-design/web-vue/es/icon";
import router from "@/router";
</script>

<style scoped>
.main-container {
  text-align: center;
  padding: 100px 0;
}
</style>
