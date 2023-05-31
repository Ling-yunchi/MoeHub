<template>
  <div style="margin-top: 20px">
    <a-list class="p-4">
      <template #header>
        <div
          style="
            width: 100%;
            display: flex;
            justify-content: space-between;
            align-items: center;
          "
        >
          <div class="title">已上传视频</div>
        </div>
      </template>
      <a-list-item v-for="video in videoList" :key="video.id">
        <div class="video-container">
          <div class="cover">
            <img :src="video.coverUrl" alt="" />
          </div>
          <div class="video-info">
            <div class="video-info__title">
              <span>{{ video.title }}</span>
            </div>
            <div class="video-info__desc">
              <span>{{ video.description }}</span>
            </div>
            <div class="video-info__category">
              <span>{{ video.category }}</span>
            </div>
            <div class="video-info__time">
              <span>{{ video.createAt }}</span>
            </div>
          </div>
          <div class="operation">
            <a-popconfirm
              type="warning"
              content="确定要删除吗?"
              :ok-button-props="{ status: 'danger' }"
              @ok="handleDelete(video.id)"
            >
              <a-button type="primary" status="danger">删除</a-button>
            </a-popconfirm>
          </div>
        </div>
      </a-list-item>
    </a-list>
  </div>
</template>

<script lang="ts" setup>
import { onMounted, ref } from "vue";
import { BaseResult, MyVideoDetail } from "@/types";
import axios from "@/plugins/axios";
import { Message } from "@arco-design/web-vue";

const videoList = ref<MyVideoDetail[]>([]);
onMounted(() => {
  axios
    .get<BaseResult<MyVideoDetail[]>>("/api/video/getMyVideoDetails")
    .then((res) => {
      if (res.data.success) {
        videoList.value = res.data.data;
      } else {
        Message.error(res.data.message);
      }
    });
});
const handleDelete = (id: string) => {
  axios.get("/api/video/delete", { params: { videoId: id } }).then((res) => {
    if (res.data.success) {
      Message.success("删除成功");
      videoList.value = videoList.value.filter((item) => item.id !== id);
    } else {
      Message.error(res.data.message);
    }
  });
};
</script>

<style lang="scss" scoped>
.video-container {
  display: flex;
  flex-direction: column;
  align-items: start;
  width: 100%;
  height: 100%;
  @apply gap-2 lg:flex-row lg:justify-between lg:items-center;

  .cover {
    width: 200px;

    img {
      width: 100%;
      height: 100%;
    }
  }

  .video-info {
    width: 600px;
    display: flex;
    flex-direction: column;
    @apply gap-1;

    .video-info__title {
      font-size: 16px;
      font-weight: bold;
    }

    .video-info__desc {
      font-size: 14px;
    }

    .video-info__category {
      @apply text-gray-500;
    }

    .video-info__time {
      font-size: 12px;
      color: #999;
    }
  }
}
</style>
