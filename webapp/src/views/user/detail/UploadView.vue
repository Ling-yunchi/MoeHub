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
      @submit-success="addVideo"
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
        <a-upload
          @before-upload="checkCoverUpload"
          :custom-request="uploadCover"
          :limit="1"
          list-type="picture-card"
          image-preview
        >
        </a-upload>
      </a-form-item>
      <a-form-item>
        <a-input disabled v-model="videoForm.coverPrefix" />
      </a-form-item>
      <a-form-item field="video" label="视频">
        <a-upload
          :auto-upload="false"
          accept="video/*"
          action="/api/video/upload"
          name="video"
          draggable
          @before-upload="checkVideoUpload"
          :limit="1"
          @success="handleVideoUploadSuccess"
        />
      </a-form-item>
      <a-form-item>
        <a-input disabled v-model="videoForm.videoPrefix" />
      </a-form-item>
      <a-form-item>
        <a-button type="primary" html-type="submit"> 上传 </a-button>
      </a-form-item>
    </a-form>
  </div>
</template>

<script lang="ts" setup>
import { ref } from "vue";
import { FileItem, Message } from "@arco-design/web-vue";
import axios from "@/plugins/axios";
import { BaseResult } from "@/types";

const videoForm = ref({
  title: "",
  description: "",
  videoPrefix: "",
  coverPrefix: "",
});
const checkVideoUpload = (file: File) => {
  //检查是否为视频文件
  const types = ["video/mp4"];
  if (!types.includes(file.type)) {
    Message.error("请上传视频文件！！！");
    return false;
  }
  return true;
};
const handleVideoUploadSuccess = (file: FileItem) => {
  const res = file.response as BaseResult<string>;
  if (res.success) {
    videoForm.value.videoPrefix = res.data;
    Message.success("视频上传成功");
  } else {
    Message.error(res.message);
  }
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

const addVideo = () => {
  axios.post("/api/video/add", videoForm.value).then((res) => {
    const result = res.data as BaseResult<never>;
    if (result.success) {
      Message.success("视频上传成功");
    } else {
      Message.error(result.message);
    }
  });
};
</script>

<style scoped></style>
