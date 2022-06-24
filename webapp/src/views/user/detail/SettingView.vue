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
        <a-upload
          style="display: block"
          id="avatar"
          action="/"
          :fileList="avatarFile ? [avatarFile] : []"
          :show-file-list="false"
          @change="
            (_, currentFile) => {
              avatarFile = {
                ...currentFile,
              };
            }
          "
          @progress="
            (currentFile) => {
              avatarFile = currentFile;
            }
          "
        >
          <template #upload-button>
            <div
              :class="`arco-upload-list-item${
                avatarFile && avatarFile.status === 'error'
                  ? ' arco-upload-list-item-error'
                  : ''
              }`"
            >
              <div
                class="arco-upload-list-picture custom-upload-avatar"
                v-if="avatarFile && avatarFile.url"
              >
                <img :src="avatarFile.url" alt="" />
                <div class="arco-upload-list-picture-mask">
                  <IconEdit />
                </div>
                <a-progress
                  v-if="
                    avatarFile.status === 'uploading' &&
                    avatarFile.percent < 100
                  "
                  :percent="avatarFile.percent"
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
      <a-form-item>
        <a-button type="primary" @click="updateUser"
          ><icon-edit /> 提交</a-button
        >
      </a-form-item>
    </a-form>
  </div>
</template>

<script lang="ts" setup>
import { ref } from "vue";
import {
  IconMan,
  IconWoman,
  IconPlus,
  IconEdit,
} from "@arco-design/web-vue/es/icon";
import { FileItem } from "@arco-design/web-vue";

const userForm = ref({
  username: "",
  nickname: "",
  sex: "",
});
const avatarFile = ref<FileItem>();

const updateUser = () => {
  console.log(userForm);
};
</script>

<style lang="scss" scoped></style>
