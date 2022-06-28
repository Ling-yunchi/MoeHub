<template>
  <div class="login-container">
    <div class="login-form-container">
      <div class="login-title">
        <h1 class="login-title__text">
          登录<img class="favicon" src="/favicon.svg" />
          <span style="color: #fa8dac">MoeHub</span>
        </h1>
      </div>
      <a-form
        layout="vertical"
        :model="loginForm"
        style="width: 400px"
        @submit-success="login"
      >
        <a-form-item
          field="username"
          label="用户名"
          :rules="[
            {
              type: 'string',
              required: true,
              message: '请输入用户名',
            },
            {
              minLength: 3,
              maxLength: 20,
              message: '用户名长度必须在 3 到 20 个字符之间',
            },
          ]"
        >
          <a-input
            v-model="loginForm.username"
            prefix-icon="user"
            placeholder="用户名"
            type="text"
            autocomplete="off"
            class="login-input"
          >
            <template #prefix> <icon-user /> </template>
          </a-input>
        </a-form-item>
        <a-form-item
          field="password"
          label="密码"
          :rules="[
            {
              type: 'string',
              required: true,
              message: '请输入密码',
            },
            {
              minLength: 6,
              maxLength: 20,
              message: '密码必须为6-20个字符',
            },
          ]"
        >
          <a-input
            v-model="loginForm.password"
            placeholder="密码"
            type="password"
            autocomplete="off"
            class="login-input"
          >
            <template #prefix>
              <icon-lock />
            </template>
          </a-input>
        </a-form-item>
        <a-form-item>
          <a-button type="primary" class="login-btn" html-type="submit">
            登录
          </a-button>
        </a-form-item>
      </a-form>
    </div>
  </div>
</template>

<script lang="ts" setup>
import { inject, ref } from "vue";
import { IconUser, IconLock } from "@arco-design/web-vue/es/icon";
import axios from "@/plugins/axios";
import { BaseResult, User } from "@/types";
import router from "@/router";
import { Message } from "@arco-design/web-vue";

const updateUser = inject("updateUser") as (u: User | null) => void;

const loginForm = ref({
  username: "",
  password: "",
});
const login = () => {
  axios.post("/api/user/login", loginForm.value).then((data) => {
    const res = data.data as BaseResult<never>;
    if (res.success) {
      axios.get("/api/user/self").then((data) => {
        const res = data.data as BaseResult<User>;
        updateUser(res.data);
        Message.success("登录成功");
        router.push("/home");
      });
    } else {
      Message.error(res.message);
    }
  });
};
</script>

<style lang="scss" scoped>
.login-container {
  width: 100%;
  height: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
  .login-form-container {
    background-color: var(--color-neutral-1);
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    border-radius: var(--border-radius);
    box-shadow: var(--box-shadow);
    padding: 20px;
    .login-title {
      margin-bottom: 20px;
      .login-title__text {
        font-size: 18px;
        font-weight: bold;
        color: var(--color-neutral-6);
        .favicon {
          width: 30px;
          height: 30px;
          margin-left: 5px;
          margin-right: 5px;
        }
      }
    }
    .login-input {
      height: 40px;
    }
    .login-btn {
      width: 100%;
      height: 40px;
      font-size: 14px;
      font-weight: bold;
      &:hover {
        background-color: #fa8dac;
      }
    }
  }
}
</style>
