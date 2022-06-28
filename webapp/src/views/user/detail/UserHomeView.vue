<template>
  <div class="user-home-container">
    <div class="section-title">
      <span class="section-title__text">
        {{ user !== null && userId === user.id ? "我的视频" : "Ta的视频" }}
      </span>
    </div>
    <div class="video-list">
      <div class="video-list__item" v-for="video in videoList" :key="video.id">
        <small-video-card
          :cover="video.coverUrl"
          :length="video.length"
          :title="video.title"
          :views="video.views"
          :video-url="`/video/${video.id}`"
          :time="video.createAt"
        >
        </small-video-card>
      </div>
    </div>
  </div>
</template>

<script lang="ts" setup>
import { inject, onMounted, ref, Ref } from "vue";
import { BasePageResult, BaseResult, User, VideoList } from "@/types";
import SmallVideoCard from "@/components/SmallVideoCard.vue";
import router from "@/router";
import axios from "@/plugins/axios";
import { Video } from "@vime/core/dist/types/components/providers/video/video";
import { Message } from "@arco-design/web-vue";

const userId = router.currentRoute.value.params.id;
const user = inject<Ref<User>>("user") as Ref<User>;

const videoList = ref<VideoList[]>([]);
onMounted(() => {
  axios
    .get<BasePageResult<VideoList>>("/api/video/getUserVideo", {
      params: { userId, pageNum: 1, pageSize: 100 },
    })
    .then((res) => {
      if (res.data.success) {
        videoList.value = res.data.data;
      } else {
        Message.error(res.data.message);
      }
    });
});
</script>

<style lang="scss" scoped>
.user-home-container {
  margin-top: 20px;
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
    margin-bottom: 20px;
    width: calc(100% / 5);
  }
}
</style>
