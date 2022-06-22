<template>
  <div class="login-container">
    <div class="login-form-container">
      <div class="login-title">
        <h1 class="login-title__text">
          登录<img class="favicon" src="/favicon.svg" />
          <span style="color: #fa8dac">MoeHub</span>
        </h1>
      </div>
      <div class="login-form">
        <div class="login-form-item">
          <label for="username">用户名</label>
          <a-input type="text" id="username" v-model="username" />
        </div>
        <div class="login-form-item">
          <label for="password">密码</label>
          <a-input-password id="password" v-model="password" />
        </div>
        <div class="login-form-item">
          <a-button class="login-btn" type="primary" @click="login">
            登录
          </a-button>
        </div>
      </div>
    </div>
  </div>
</template>

<script lang="ts" setup>
import { ref } from "vue";

const username = ref("");
const password = ref("");
const login = () => {
  console.log(username.value, password.value);
};
</script>

<style lang="scss" scoped>
.login-container {
  width: 100%;
  height: 100%;
  .login-form-container {
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
    .login-form {
      width: 100%;
      display: flex;
      flex-direction: column;
      .login-form-item {
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
  }
}
</style>
