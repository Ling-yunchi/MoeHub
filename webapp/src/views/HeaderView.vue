<template>
  <a-layout-header class="header-container">
    <div class="header">
      <router-link to="/home">
        <img class="logo-img" src="@/assets/logo.svg" alt="moehub" />
      </router-link>
      <template v-if="searchShow">
        <div class="search">
          <a-input-group>
            <a-select
              class="select-btn"
              :options="['视频', '用户']"
              v-model="searchType"
            />
            <a-input
              class="search-input"
              :placeholder="searchPlaceholder"
              prefix-icon="icon-search"
              suffix-icon="icon-close"
              v-model="searchInput"
              @keyup.enter="search"
            />
          </a-input-group>

          <a-button class="search-btn" @click="search">
            <icon-search />
          </a-button>
        </div>
      </template>
      <div class="user-container">
        <router-link v-if="searchShow" to="/search">
          <div
            class="h-8 w-8 inline-flex items-center justify-center rounded-full bg-blue-200 lg:hidden"
          >
            <icon-search size="1rem" />
          </div>
        </router-link>
        <template v-if="user !== null">
          <router-link :to="`/user/${user.id}`" target="_blank">
            <a-avatar :size="40">
              <img
                :src="
                  user.avatar
                    ? user.avatar
                    : require('@/assets/default-avatar.jpg')
                "
                alt="avatar"
                style="object-fit: cover"
              />
            </a-avatar>
          </router-link>
          <router-link :to="`/user/${user.id}`" class="user-name">
            {{ user.nickname }}
          </router-link>
          <a-button class="login-btn" type="dashed" @click="logout">
            <icon-export />
            登出
          </a-button>
        </template>
        <template v-else>
          <a-button
            class="login-btn"
            type="primary"
            @click="router.push('/user/login')"
          >
            <icon-user />
            登录
          </a-button>
          <a-button class="register-btn" @click="router.push('/user/register')">
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
  IconExport,
} from "@arco-design/web-vue/es/icon";
import { ref, watch, inject, Ref, defineProps } from "vue";
import router from "@/router";
import { updateUserKey, User, userKey } from "@/types";
import { Message } from "@arco-design/web-vue";
import axios from "@/plugins/axios";

defineProps({
  searchShow: {
    type: Boolean,
    required: false,
    default: true,
  },
});

const searchType = ref("视频");
const searchPlaceholder = ref("输入视频标题");
const searchInput = ref("");
watch(searchType, (type) => {
  if (type === "视频") {
    searchPlaceholder.value = "输入视频标题";
  } else {
    searchPlaceholder.value = "输入用户昵称";
  }
});

const logout = () => {
  axios.get("/api/user/logout").then((data) => {
    let res = data.data;
    Message.info(res.message);
    updateUser(null);
  });
};

const search = () => {
  if (searchInput.value === "") {
    Message.warning("请输入搜索内容");
    return;
  }
  if (searchType.value === "视频") {
    router.push(`/search?type=video&q=${searchInput.value}`);
  } else {
    router.push(`/search?type=user&q=${searchInput.value}`);
  }
};

const user = inject(userKey) as Readonly<Ref<User | null>>;
const updateUser = inject(updateUserKey) as (u: User | null) => void;
</script>

<style lang="scss">
.header-container {
  @apply p-4 h-24;

  .header {
    height: 100%;
    display: flex;
    align-items: center;
    justify-content: space-between;
    margin: 0 auto;
    @apply gap-2 lg:px-16;

    .logo-img {
      width: 150px;
      cursor: pointer;
      display: block;
    }

    .search {
      @apply hidden gap-2 lg:flex;
      .select-btn {
        height: 40px;
        width: 80px;
        border-radius: 4px 0 0 4px;
      }

      .search-input {
        width: 600px;
        height: 40px;
        border-radius: 0 4px 4px 0;
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
      @apply gap-2;

      .user-name {
        font-size: 16px;
        font-weight: bold;
        color: var(--color-neutral-8);
        margin-left: 10px;
        text-decoration: none;
        // 字符不换行
        white-space: nowrap;
        margin-right: 20px;
        @apply hidden lg:block;
      }
    }
  }
}
</style>
