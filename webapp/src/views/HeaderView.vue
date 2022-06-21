<template>
  <a-layout-header class="header-container">
    <div class="header">
      <a href="/">
        <img class="logo-img" src="@/assets/logo.svg" alt="moehub" />
      </a>
      <div class="search">
        <a-input
          class="search-input"
          placeholder="搜索视频"
          prefix-icon="icon-search"
          suffix-icon="icon-close"
          v-model="searchInput"
          @keyup.enter="search"
        />
        <a-button class="search-btn" @click="search">
          <icon-search />
        </a-button>
      </div>
      <div class="user-container">
        <template v-if="user !== null">
          <a-avatar :src="user.avatar" size="small" />
          <span class="user-name">{{ user.name }}</span>
        </template>
        <template v-else>
          <a-button class="login-btn" type="primary">
            <icon-user />
            登录
          </a-button>
          <a-button class="register-btn">
            <icon-user-add />
            注册
          </a-button>
        </template>
      </div>
    </div>
  </a-layout-header>
</template>

<script lang="ts" setup>
import {
  IconSearch,
  IconUser,
  IconUserAdd,
} from "@arco-design/web-vue/es/icon";
import { ref, inject, Ref } from "vue";
import router from "@/router";
import { User } from "@/types";

const searchInput = ref("");
const search = () => {
  router.push(`/search/${searchInput.value}`);
};

const user = inject<Ref<User>>("user") as Ref<User>;
</script>

<style lang="scss">
.header-container {
  height: 80px;
  .header {
    height: 100%;
    display: flex;
    align-items: center;
    justify-content: space-between;
    padding: 0 50px;
    margin: 0 auto;
    position: relative;
    .logo-img {
      width: 150px;
      cursor: pointer;
      display: block;
    }
    .search {
      .search-input {
        width: 600px;
        height: 40px;
        border-radius: 4px;
        padding: 0 10px;
        font-size: 14px;
        @media (max-width: 1400px) {
          width: 400px;
        }
        @media (max-width: 1000px) {
          width: 200px;
        }
      }
      .search-btn {
        height: 40px;
        width: 60px;
        @media (max-width: 1000px) {
          width: 40px;
        }
      }
    }
    .user-container {
      display: flex;
      align-items: center;
      justify-content: center;
      width: 100px;
      height: 100px;
      border-radius: 50%;
    }
  }
}
</style>
