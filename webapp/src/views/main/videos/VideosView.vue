<template>
  <div class="videos-main">
    <div class="section-title">
      <h3 class="section-title__text">所有视频</h3>
    </div>
    <div class="video-list">
      <div class="video-list__item" v-for="video in videoList" :key="video.id">
        <video-card
          :cover-url="video.coverUrl"
          :length="video.length"
          :author-name="video.authorName"
          :avatar="video.avatarUrl"
          :title="video.title"
          :views="video.views"
          :video-url="`/video/${video.id}`"
          :author-url="`/user/${video.authorId}`"
        >
        </video-card>
      </div>
    </div>
  </div>
</template>

<script lang="ts" setup>
import { onMounted, ref } from "vue";
import VideoCard from "@/components/VideoCard.vue";
import axios from "@/plugins/axios";
import { BasePageResult, VideoList } from "@/types";
import { Message } from "@arco-design/web-vue";

const videoList = ref<VideoList[]>([]);
const getVideos = () => {
  axios
    .get("/api/video/getAllVideo", { params: { pageNum: 1, pageSize: 100 } })
    .then((res) => {
      const result = res.data as BasePageResult<VideoList>;
      if (result.success) {
        videoList.value = result.data;
      } else {
        Message.error(result.message);
      }
    });
};
onMounted(getVideos);
</script>

<style lang="scss" scoped>
a {
  outline: 0;
  color: inherit;
  text-decoration: none;
}
.videos-main {
  max-width: 1600px;
  margin: 0 auto;
}
.section-title {
  display: flex;
  justify-content: space-between;
  align-content: center;
  margin-bottom: 15px;
  padding-bottom: 10px;
  line-height: 20px;
  border-bottom: 1px solid hsla(0, 0%, 60%, 0.1);
  width: 100%;
  .section-title__text {
    font-size: 18px;
    font-weight: bold;
    color: var(--color-natural-6);
    margin: 0 0 0 20px;
  }
  .section-title__more {
    display: flex;
    align-items: center;
    font-size: 16px;
    margin-right: 4px;
    .section-title__more-text {
      margin-right: 4px;
      font-size: 16px;
      color: var(--color-natural-6);
    }
  }
}
.video-list {
  margin-top: 20px;
  display: flex;
  flex-wrap: wrap;
  justify-content: flex-start;
  margin-bottom: 20px;
  .video-list__item {
    box-sizing: content-box;
    display: flex;
    align-items: center;
    justify-content: center;
    width: calc(100% / 3);
    margin-bottom: 20px;
    @media (max-width: 1400px) {
      width: calc(100% / 2);
    }
    @media (max-width: 1000px) {
      width: 100%;
    }
  }
}
</style>
