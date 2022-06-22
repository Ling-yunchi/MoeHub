<template>
  <div class="register-container">
    <div class="register-form-container">
      <div class="register-title">
        <h1 class="register-title__text">
          注册<img class="favicon" src="/favicon.svg" />
          <span style="color: #fa8dac">MoeHub</span>
        </h1>
      </div>
      <div class="register-form">
        <div class="register-form-item">
          <label for="username">用户名</label>
          <a-input type="text" id="username" v-model="username" />
        </div>
        <div class="register-form-item">
          <label for="password">密码</label>
          <a-input-password id="password" v-model="password" />
        </div>
        <div class="register-form-item">
          <label for="avatar">头像</label>
          <a-upload
            style="display: block"
            id="avatar"
            action="/"
            :fileList="file ? [file] : []"
            :show-file-list="false"
            @change="onChange"
            @progress="onProgress"
          >
            <template #upload-button>
              <div
                :class="`arco-upload-list-item${
                  file && file.status === 'error'
                    ? ' arco-upload-list-item-error'
                    : ''
                }`"
              >
                <div
                  class="arco-upload-list-picture custom-upload-avatar"
                  v-if="file && file.url"
                >
                  <img :src="file.url" />
                  <div class="arco-upload-list-picture-mask">
                    <IconEdit />
                  </div>
                  <a-progress
                    v-if="file.status === 'uploading' && file.percent < 100"
                    :percent="file.percent"
                    type="circle"
                    size="mini"
                    :style="{
                      position: 'absolute',
                      left: '50%',
                      top: '50%',
                      transform: 'translateX(-50%) translateY(-50%)',
                    }"
                  />
                </div>
                <div class="arco-upload-picture-card" v-else>
                  <div class="arco-upload-picture-card-text">
                    <IconPlus />
                    <div style="margin-top: 10px; font-weight: 600">Upload</div>
                  </div>
                </div>
              </div>
            </template>
          </a-upload>
        </div>
        <div class="register-form-item">
          <label for="nickname">昵称</label>
          <a-input id="nickname" v-model="nickname" />
        </div>
        <div class="register-form-item">
          <label for="email">邮箱</label>
          <a-input id="email" v-model="email" />
        </div>
        <div class="register-form-item">
          <label for="sex">性别</label>
          <a-radio-group
            id="sex"
            style="display: block; margin-top: 10px"
            v-model="sex"
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
        </div>
        <div class="register-form-item">
          <a-button class="register-btn" type="primary" @click="register">
            注册
          </a-button>
        </div>
      </div>
    </div>
  </div>
</template>

<script lang="ts" setup>
import { ref } from "vue";
import {
  IconEdit,
  IconPlus,
  IconMan,
  IconWoman,
} from "@arco-design/web-vue/es/icon";

const username = ref("");
const password = ref("");
const nickname = ref("");
const email = ref("");
const avatar = ref("");
const sex = ref("");

const register = () => {
  console.log(
    username.value,
    password.value,
    nickname.value,
    email.value,
    sex.value
  );
};
</script>

<style lang="scss" scoped>
.register-container {
  width: 100%;
  height: 100%;
  .register-form-container {
    position: absolute;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
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
    .register-form {
      width: 100%;
      display: flex;
      flex-direction: column;
      .register-form-item {
        width: 100%;
        margin-bottom: 20px;
        // last child dont need margin-bottom
        &:last-child {
          margin-bottom: 0;
        }
        label {
          font-size: 14px;
          font-weight: bold;
          color: var(--color-neutral-6);
        }
        .a-input .a-input-password {
          width: 100%;
          height: 40px;
          border-radius: var(--border-radius);
          border: 1px solid var(--color-neutral-6);
          padding: 0 10px;
          &:focus {
            border: 1px solid var(--color-primary-1);
          }
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
  }
}
</style>
