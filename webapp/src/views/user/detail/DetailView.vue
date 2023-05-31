<template>
  <div class="content-container">
    <div
      class="user-header"
      :style="{
        'background-image': `url(${require('@/assets/header-img.png')})`,
      }"
    >
      <div class="user-info">
        <a-avatar
          :size="60"
          style="
            border: 5px solid rgba(255, 255, 255, 0.5);
            background: transparent;
            box-sizing: content-box;
          "
        >
          <img
            style="object-fit: cover"
            :src="
              pageUser.avatar
                ? pageUser.avatar
                : require('@/assets/default-avatar.jpg')
            "
            alt="avatar"
          />
        </a-avatar>
        <div class="user-info-name">
          <span style="margin-bottom: 10px">
            <span class="user-nickname">{{ pageUser.nickname }}</span>
          </span>
          <span>
            <a-tag style="background-color: rgba(0, 0, 0, 0.4)">
              <span class="user-username">@{{ pageUser.username }}</span>
            </a-tag>
          </span>
        </div>
      </div>
    </div>
    <div class="navigator">
      <!--      <a-tabs :active-key="selectMenuKey">-->
      <!--        <a-tab-pane key="0" @click="router.push('home')">-->
      <!--          <template #title>-->
      <!--            <icon-home />-->
      <!--            <span>首页</span>-->
      <!--          </template>-->
      <!--        </a-tab-pane>-->
      <!--        <template v-if="self">-->
      <!--          <a-tab-pane key="1" @click="() => router.push('favorite')">-->
      <!--            <template #title>-->
      <!--              <icon-star />-->
      <!--              <span>我的收藏</span>-->
      <!--            </template>-->
      <!--          </a-tab-pane>-->
      <!--          <a-tab-pane key="2" @click="router.push('video')">-->
      <!--            <template #title>-->
      <!--              <icon-archive />-->
      <!--              <span>我的视频</span>-->
      <!--            </template>-->
      <!--          </a-tab-pane>-->
      <!--          <a-tab-pane key="3" @click="router.push('upload')">-->
      <!--            <template #title>-->
      <!--              <icon-upload />-->
      <!--              <span>上传视频</span>-->
      <!--            </template>-->
      <!--          </a-tab-pane>-->
      <!--          <a-tab-pane key="4" @click="router.push('setting')">-->
      <!--            <template #title>-->
      <!--              <icon-settings />-->
      <!--              <span>设置</span>-->
      <!--            </template>-->
      <!--          </a-tab-pane>-->
      <!--        </template>-->
      <!--      </a-tabs>-->
      <a-menu mode="horizontal" :default-selected-keys="[selectMenuKey]">
        <a-menu-item key="0" @click="router.push('home')">
          <icon-home />
          <span>首页</span>
        </a-menu-item>
        <template v-if="self">
          <a-menu-item key="1" @click="router.push('favorite')">
            <icon-star />
            <span>我的收藏</span>
          </a-menu-item>
          <a-menu-item key="2" @click="router.push('video')">
            <icon-archive />
            <span>我的视频</span>
          </a-menu-item>
          <a-menu-item key="3" @click="router.push('upload')">
            <icon-upload />
            <span>上传视频</span>
          </a-menu-item>
          <a-menu-item key="4" @click="router.push('setting')">
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
  IconArchive,
  IconHome,
  IconSettings,
  IconStar,
  IconUpload,
} from "@arco-design/web-vue/es/icon";
import { inject, Ref, ref, watch } from "vue";
import router from "@/router";
import { BaseResult, User, userKey } from "@/types";
import axios from "@/plugins/axios";

const userId = router.currentRoute.value.params.id;
const pageUser = ref({
  id: "",
  username: "",
  nickname: "",
  avatar: "",
});
axios.get("/api/user/info", { params: { userId: userId } }).then((res) => {
  const result = res.data as BaseResult<User>;
  if (result.success) {
    pageUser.value = result.data;
  } else {
    router.push("/404");
  }
});

const user = inject(userKey) as Readonly<Ref<User | null>>;
const self = ref(user.value !== null && user.value.id === userId);
watch(user, (newUser) => {
  self.value = newUser !== null && newUser.id === userId;
  if (router.currentRoute.value.path.split("/").pop() !== "home") {
    router.push("home");
    selectMenuKey.value = "0";
  }
});
const path = router.currentRoute.value.path.split("/").pop();
if (!self.value && path !== "home") {
  router.push("home");
}
const selectMenuKey = ref("0");
switch (path) {
  case "home":
    selectMenuKey.value = "0";
    break;
  case "favorite":
    selectMenuKey.value = "1";
    break;
  case "video":
    selectMenuKey.value = "2";
    break;
  case "upload":
    selectMenuKey.value = "3";
    break;
  case "setting":
    selectMenuKey.value = "4";
    break;
  default:
    break;
}
</script>

<style lang="scss">
.section-title {
  display: flex;
  justify-content: space-between;
  align-content: center;
  margin-bottom: 15px;
  padding-bottom: 10px;
  line-height: 20px;
  border-bottom: 1px solid hsla(0, 0%, 60%, 0.1);
  width: 100%;
  .section-title__text {
    font-size: 18px;
    font-weight: bold;
    color: var(--color-natural-6);
    margin: 0 0 0 20px;
  }
  .section-title__more {
    display: flex;
    align-items: center;
    font-size: 16px;
    margin-right: 4px;
    .section-title__more-text {
      margin-right: 4px;
      font-size: 16px;
      color: var(--color-natural-6);
    }
  }
}
</style>

<style lang="scss" scoped>
.content-container {
  display: flex;
  flex-direction: column;
  height: 100%;
  width: 1100px;
  margin: 0 auto;
  .user-header {
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
