<template>
  <div style="margin-top: 20px">
    <div class="section-title">
      <span class="section-title__text"> 上传视频 </span>
    </div>
    <a-form
      :model="videoForm"
      :label-width="100"
      layout="vertical"
      :style="{ width: '1020px', padding: '40px' }"
    >
      <a-form-item field="title" label="标题">
        <a-input v-model="videoForm.title" placeholder="请输入标题" />
      </a-form-item>
      <a-form-item field="description" label="简介">
        <a-textarea
          v-model="videoForm.description"
          placeholder="请输入简介"
          :max-length="255"
          show-word-limit
          :auto-size="{
            minRows: 3,
            maxRows: 20,
          }"
        />
      </a-form-item>
      <a-form-item field="cover" label="封面">
        <input v-model="videoForm.coverUrl" hidden />
        <a-upload
          @before-upload="checkCoverUpload"
          :custom-request="uploadCover"
          :limit="1"
          list-type="picture-card"
          image-preview
        >
        </a-upload>
      </a-form-item>
      <a-form-item field="video" label="视频">
        <input v-model="videoForm.videoUrl" hidden />
        <a-upload
          draggable
          @before-upload="checkVideoUpload"
          :custom-request="uploadVideo"
          :limit="1"
        />
      </a-form-item>
    </a-form>
  </div>
</template>

<script lang="ts" setup>
import { ref } from "vue";
import { Message } from "@arco-design/web-vue";

const videoForm = ref({
  title: "",
  description: "",
  videoUrl: "",
  coverUrl: "",
});
const checkVideoUpload = (file: File) => {
  //检查是否为视频文件
  const types = ["video/mp4"];
  if (!types.includes(file.type)) {
    Message.error("请上传视频文件！！！");
    return false;
  }
};
const uploadVideo = (option: any) => {
  const { onProgress, onError, onSuccess, fileItem, name } = option;
  console.log(videoForm.value);
};

const checkCoverUpload = (file: File) => {
  //检查是否为图片文件
  const types = ["image/jpeg", "image/png"];
  if (!types.includes(file.type)) {
    Message.error("请上传图片文件！！！");
    return false;
  }
};
const uploadCover = (option: any) => {
  const { onProgress, onError, onSuccess, fileItem, name } = option;
  console.log(videoForm.value);
};
</script>

<style scoped></style>
