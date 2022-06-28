<template>
  <a-layout class="main-container">
    <header-view></header-view>
    <a-layout>
      <a-layout-sider
        class="sider-container"
        hide-trigger
        collapsible
        :collapsed="collapsed"
      >
        <a-button class="sider-trigger" @click="collapsed = !collapsed">
          <icon-menu-fold v-if="!collapsed"></icon-menu-fold>
          <icon-menu-unfold v-else></icon-menu-unfold>
        </a-button>
        <a-menu @menu-item-click="navigate">
          <a-menu-item key="home">
            <icon-home />
            <span>首页</span>
          </a-menu-item>
          <a-menu-item key="videos">
            <icon-live-broadcast />
            <span>视频</span>
          </a-menu-item>
          <a-menu-item key="index">
            <icon-unordered-list />
            <span>分类</span>
          </a-menu-item>
          <a-menu-item key="about">
            <icon-info-circle />
            <span>关于</span>
          </a-menu-item>
        </a-menu>
      </a-layout-sider>
      <a-layout-content style="height: calc(100vh - 80px); overflow: scroll">
        <router-view></router-view>
      </a-layout-content>
    </a-layout>
  </a-layout>
</template>

<script lang="ts" setup>
import { ref } from "vue";
import HeaderView from "@/views/HeaderView.vue";
import {
  IconMenuFold,
  IconMenuUnfold,
  IconHome,
  IconUnorderedList,
  IconLiveBroadcast,
  IconInfoCircle,
} from "@arco-design/web-vue/es/icon";
import router from "@/router";

const collapsed = ref(false);
const navigate = (to: string) => {
  router.push(to);
};
</script>

<style lang="scss">
.main-container {
  height: 100%;
  .sider-container {
    height: 100%;
    width: 200px;
    .sider-trigger {
      width: 100%;
      color: var(--color-natural-6);
    }
  }
}
</style>
