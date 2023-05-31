<template>
  <div style="margin-top: 20px">
    <div class="section-title">
      <span class="section-title__text"> 修改资料 </span>
    </div>
    <a-form
      :model="userForm"
      :label-width="100"
      layout="vertical"
      :style="{ width: '1020px', padding: '40px' }"
    >
      <a-form-item label="用户名">
        <a-input v-model="userForm.username" :disabled="true"></a-input>
      </a-form-item>
      <a-form-item label="昵称">
        <a-input v-model="userForm.nickname"></a-input>
      </a-form-item>
      <a-form-item label="头像">
        <a-avatar style="margin-right: 10px">
          <img
            style="object-fit: cover"
            :src="
              avatarUrl ? avatarUrl : require('@/assets/default-avatar.jpg')
            "
            alt=""
          />
        </a-avatar>
        <a-upload
          style="display: block"
          id="avatar"
          :action="avatarUploadUrl"
          name="avatar"
          @success="uploadAvatar"
        >
        </a-upload>
      </a-form-item>
      <a-form-item label="邮箱">
        <a-input v-model="userForm.email"></a-input>
      </a-form-item>
      <a-form-item label="性别">
        <a-radio-group v-model="userForm.sex">
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
      <a-form-item field="password" label="密码">
        <a-input-password v-model="userForm.password"></a-input-password>
      </a-form-item>
      <a-form-item>
        <a-button type="primary" @click="updateUserInfo">
          <icon-edit />
          提交
        </a-button>
      </a-form-item>
    </a-form>
  </div>
</template>

<script lang="ts" setup>
import { inject, Ref, ref } from "vue";
import { IconMan, IconWoman, IconEdit } from "@arco-design/web-vue/es/icon";
import { FileItem, Message } from "@arco-design/web-vue";
import {
  BaseResult,
  User,
  avatarUploadUrl,
  updateUserKey,
  userKey,
} from "@/types";
import axios from "@/plugins/axios";

const user = inject(userKey) as Readonly<Ref<User | null>>;
const updateUser = inject(updateUserKey) as (user: User | null) => void;

const userForm = ref({
  username: user.value?.username,
  nickname: user.value?.nickname,
  password: "",
  email: user.value?.email,
  sex: user.value?.sex,
});

const avatarUrl = ref(user.value ? user.value.avatar : "");
const uploadAvatar = (file: FileItem) => {
  const res = file.response as BaseResult<string>;
  if (res.success) {
    avatarUrl.value = res.data;
    updateUser({
      ...user.value!,
      avatar: res.data,
    });
  } else {
    Message.error(res.message);
  }
};

const updateUserInfo = () => {
  axios
    .post<BaseResult<User>>("/api/user/update", userForm.value, {
      params: { updatePassword: userForm.value.password !== "" },
    })
    .then((res) => {
      if (res.data.success) {
        updateUser(res.data.data);
        Message.success("修改成功");
      } else {
        Message.error(res.data.message);
      }
    });
};
</script>

<style lang="scss" scoped></style>
