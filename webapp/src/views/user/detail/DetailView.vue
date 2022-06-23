<template>
  <div class="content-container">
    <div class="user-header">
      <div class="user-info">
        <a-avatar
          :size="60"
          style="
            border: 5px solid rgba(255, 255, 255, 0.5);
            background: transparent;
            box-sizing: content-box;
          "
        >
          <img src="/avatar.jpg" alt="avatar" />
        </a-avatar>
        <div class="user-info-name">
          <span style="margin-bottom: 10px">
            <span class="user-nickname">龗云螭</span>
          </span>
          <span>
            <a-tag style="background-color: rgba(0, 0, 0, 0.4)">
              <span class="user-username">@Ling-yunchi</span>
            </a-tag>
          </span>
        </div>
      </div>
    </div>
    <div class="navigator">
      <a-menu mode="horizontal" :default-selected-keys="['0']">
        <a-menu-item key="0">
          <icon-home />
          <span>首页</span>
          <router-link to="home"></router-link>
        </a-menu-item>
        <template v-if="self">
          <a-menu-item key="1">
            <icon-star />
            <span>我的收藏</span>
          </a-menu-item>
          <a-menu-item key="2">
            <icon-archive />
            <span>我的视频</span>
          </a-menu-item>
          <a-menu-item key="3">
            <icon-settings />
            <span>设置</span>
          </a-menu-item>
        </template>
      </a-menu>
    </div>
    <div class="content-container">
      <router-view></router-view>
    </div>
  </div>
</template>

<script lang="ts" setup>
import {
  IconHome,
  IconStar,
  IconArchive,
  IconSettings,
} from "@arco-design/web-vue/es/icon";
import { inject, Ref, ref } from "vue";
import router from "@/router";
import { User } from "@/types";

const userId = router.currentRoute.value.params.id;
const user = inject<Ref<User>>("user") as Ref<User>;
const self = ref(user.value !== null && user.value.id === userId);
</script>

<style lang="scss" scoped>
.content-container {
  display: flex;
  flex-direction: column;
  height: 100%;
  width: 1100px;
  margin: 0 auto;
  .user-header {
    background-image: url("/public/header-img.png");
    background-size: cover;
    background-position: center;
    background-repeat: no-repeat;
    width: 100%;
    height: 200px;
    position: relative;
    .user-info {
      position: absolute;
      bottom: 0;
      width: 100%;
      display: flex;
      flex-direction: row;
      padding: 20px;
      .user-info-name {
        flex: 1;
        display: flex;
        flex-direction: column;
        justify-content: center;
        margin-left: 20px;
        .user-nickname {
          font-size: 20px;
          font-weight: bold;
          letter-spacing: 1px;
          color: var(--color-neutral-2);
        }
        .user-username {
          font-size: 10px;
          font-weight: bold;
          color: var(--color-neutral-2);
        }
      }
    }
  }
}
</style>
