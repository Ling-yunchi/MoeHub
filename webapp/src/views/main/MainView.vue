<template>
  <a-layout class="main-container">
    <header-view></header-view>
    <a-layout>
      <!-- lg menu -->
      <a-layout-sider
        class="hidden lg:inline sider-container"
        hide-trigger
        collapsible
        :collapsed="collapsed"
      >
        <a-button class="sider-trigger" @click="collapsed = !collapsed">
          <icon-menu-fold v-if="!collapsed"></icon-menu-fold>
          <icon-menu-unfold v-else></icon-menu-unfold>
        </a-button>
        <a-menu @menu-item-click="navigate" :selected-keys="[selectMenuKey]">
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
      <!-- sm menu -->
      <a-trigger trigger="click" position="top">
        <div
          class="fixed bottom-4 right-4 z-10 flex items-center justify-center w-10 h-10 bg-blue-300 rounded-full transition-colors active:bg-blue-400 lg:hidden"
        >
          <icon-menu />
        </div>
        <template #content>
          <a-menu
            mode="popButton"
            :selected-keys="[selectMenuKey]"
            @menu-item-click="navigate"
          >
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
          </a-menu></template
        >
      </a-trigger>
      <a-layout-content class="h-[calc(100vh-6rem)] overflow-y-scroll">
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
  IconMenu,
} from "@arco-design/web-vue/es/icon";
import router from "@/router";
import { onUpdated } from "vue";
import { onMounted } from "vue";

const collapsed = ref(false);
const navigate = (to: string) => {
  router.push(to);
};
const selectMenuKey = ref("home");
const updateMenuKey = () => {
  const path = router.currentRoute.value.path.split("/").pop();
  switch (path) {
    case "home":
      selectMenuKey.value = "home";
      break;
    case "videos":
      selectMenuKey.value = "videos";
      break;
    case "index":
      selectMenuKey.value = "index";
      break;
    case "about":
      selectMenuKey.value = "about";
      break;
  }
};
onMounted(updateMenuKey);
onUpdated(updateMenuKey);
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
