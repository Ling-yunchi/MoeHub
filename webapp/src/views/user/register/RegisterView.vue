<template>
  <div class="register-container">
    <div class="register-form-container">
      <div class="register-title">
        <h1 class="register-title__text">
          注册<img class="favicon" src="/favicon.svg" alt="logo" />
          <span style="color: #fa8dac">MoeHub</span>
        </h1>
      </div>
      <a-form
        layout="vertical"
        :model="registerForm"
        style="width: 500px"
        @submit-success="register"
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
            v-model="registerForm.username"
            prefix-icon="user"
            placeholder="用户名"
            type="text"
            autocomplete="off"
            class="register-input"
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
            v-model="registerForm.password"
            placeholder="密码"
            type="password"
            autocomplete="off"
            class="register-input"
          >
            <template #prefix>
              <icon-lock />
            </template>
          </a-input>
        </a-form-item>
        <a-form-item
          field="nickname"
          label="昵称"
          :rules="[
            {
              type: 'string',
              required: true,
              message: '请输入昵称',
            },
            {
              minLength: 6,
              maxLength: 20,
              message: '昵称必须为6-20个字符',
            },
          ]"
        >
          <a-input
            v-model="registerForm.nickname"
            placeholder="昵称"
            type="text"
            autocomplete="off"
            class="register-input"
          >
            <template #prefix>
              <icon-lock />
            </template>
          </a-input>
        </a-form-item>
        <a-form-item
          field="email"
          label="邮箱"
          :rules="[
            {
              type: 'email',
              required: true,
              message: '请输入邮箱',
            },
          ]"
        >
          <a-input
            v-model="registerForm.email"
            placeholder="邮箱"
            type="email"
            autocomplete="off"
            class="register-input"
          >
            <template #prefix>
              <icon-email />
            </template>
          </a-input>
        </a-form-item>
        <a-form-item
          field="sex"
          label="性别"
          :rules="[
            {
              type: 'string',
              required: true,
              message: '请选择性别',
            },
          ]"
        >
          <a-radio-group
            id="sex"
            style="display: block; margin-top: 10px"
            v-model="registerForm.sex"
          >
            <a-radio value="man">
              <icon-man style="color: deepskyblue" />
              <span style="color: var(--color-neutral-7)"> 男 </span>
            </a-radio>
            <a-radio value="woman">
              <icon-woman style="color: #fa8dac" />
              <span style="color: var(--color-neutral-7)"> 女 </span>
            </a-radio>
          </a-radio-group>
        </a-form-item>
        <a-form-item>
          <a-button type="primary" class="register-btn" html-type="submit">
            注册
          </a-button>
        </a-form-item>
      </a-form>
    </div>
  </div>
</template>

<script lang="ts" setup>
import { ref } from "vue";
import {
  IconMan,
  IconWoman,
  IconUser,
  IconLock,
  IconEmail,
} from "@arco-design/web-vue/es/icon";
import axios from "@/plugins/axios";
import { BaseResult, User } from "@/types";
import router from "@/router";
import { Message } from "@arco-design/web-vue";

const registerForm = ref({
  username: "",
  password: "",
  nickname: "",
  email: "",
  sex: "",
});
const register = () => {
  axios.post("/api/user/register", registerForm.value).then((res) => {
    const result = res.data as BaseResult<User>;
    if (result.success) {
      Message.success("注册成功");
      router.push("/user/login");
    } else {
      Message.error(result.message);
    }
  });
};
</script>

<style lang="scss" scoped>
.register-container {
  width: 100%;
  height: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
  .register-form-container {
    background-color: var(--color-neutral-1);
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    border-radius: var(--border-radius);
    box-shadow: var(--box-shadow);
    padding: 20px;
    width: 500px;
    .register-title {
      margin-bottom: 20px;
      .register-title__text {
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

    .register-input {
      height: 40px;
    }
    .register-btn {
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
