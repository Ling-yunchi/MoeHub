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
      <a-form-item
        field="title"
        label="标题"
        :rules="[
          {
            required: true,
            message: '请输入标题',
            trigger: 'blur',
          },
          {
            type: 'string',
            maxLength: 50,
            message: '标题长度不能超过50个字符',
          },
        ]"
      >
        <a-input v-model="videoForm.title" placeholder="请输入标题" />
      </a-form-item>
      <a-form-item
        field="description"
        label="简介"
        :rules="[
          {
            type: 'string',
            maxLength: 255,
            message: '简介不能超过255个字符',
          },
        ]"
      >
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
      <a-form-item label="封面">
        <a-upload
          accept="image/*"
          :action="actionUrl"
          name="file"
          @before-upload="checkCoverUpload"
          @success="handleCoverUploadSuccess"
          :on-before-remove="handleCoverRemove"
          :limit="1"
          image-preview
          list-type="picture-card"
        >
        </a-upload>
      </a-form-item>
      <a-form-item
        field="coverPrefix"
        :rules="[
          {
            required: true,
            message: '请上传封面',
            trigger: 'change',
          },
        ]"
      >
        <a-input disabled v-model="videoForm.coverPrefix" />
      </a-form-item>
      <a-form-item label="视频">
        <a-upload
          :auto-upload="false"
          accept="video/*"
          :action="actionUrl"
          name="file"
          draggable
          @before-upload="checkVideoUpload"
          :limit="1"
          @success="handleVideoUploadSuccess"
          :on-before-remove="handleVideoRemove"
        />
      </a-form-item>
      <a-form-item
        field="videoPrefix"
        :rules="[
          {
            required: true,
            message: '请上传视频',
            trigger: 'change',
          },
          {
            type: 'string',
            minLength: 1,
          },
        ]"
      >
        <a-input disabled v-model="videoForm.videoPrefix" />
        <span style="margin-left: 10px; margin-right: 5px">length:</span>
        <a-input-number disabled v-model="videoForm.length" />
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
import { BaseResult, actionUrl } from "@/types";

const videoForm = ref({
  title: "",
  description: "",
  videoPrefix: "",
  length: 0,
  coverPrefix: "",
});

const handleCoverUploadSuccess = (file: FileItem) => {
  const res = file.response as BaseResult<string>;
  if (res.success) {
    videoForm.value.coverPrefix = res.data;
    Message.success("视频上传成功");
  } else {
    Message.error(res.message);
  }
};

const handleCoverRemove = () => {
  videoForm.value.coverPrefix = "";
  return true;
};

const checkVideoUpload = (file: File) => {
  //检查是否为视频文件
  const types = ["video/mp4"];
  if (!types.includes(file.type)) {
    Message.error("请上传视频文件！！！");
    return false;
  }
  let audio = new Audio(URL.createObjectURL(file));
  audio.preload = "metadata";
  audio.onloadedmetadata = () => {
    videoForm.value.length = Math.floor(audio.duration);
    audio.remove();
  };
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
const handleVideoRemove = () => {
  videoForm.value.videoPrefix = "";
  videoForm.value.length = 0;
  return true;
};

const checkCoverUpload = (file: File) => {
  //检查是否为图片文件
  const types = ["image/jpeg", "image/png"];
  if (!types.includes(file.type)) {
    Message.error("请上传图片文件！！！");
    return false;
  }
  return true;
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
