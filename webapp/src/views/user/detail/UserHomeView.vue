<template>
  <div class="user-home-container">
    <div class="section-title">
      <span class="section-title__text">
        {{ user !== null && userId === user.id ? "我的视频" : "Ta的视频" }}
      </span>
    </div>
    <div
      v-if="videoList.length === 0"
      class="w-full flex flex-col items-center text-slate-600"
    >
      <icon-empty size="3rem" class="" />
      <span class="mt-4">{{
        user !== null && userId === user.id
          ? "你还没有上传视频哦~"
          : "Ta还没有上传视频哦~"
      }}</span>
    </div>
    <div v-else class="video-list">
      <div class="video-list__item" v-for="video in videoList" :key="video.id">
        <small-video-card
          :cover-url="video.coverUrl"
          :length="video.length"
          :title="video.title"
          :views="video.views"
          :video-url="`/video/${video.id}`"
          :create-at="video.createAt"
        >
        </small-video-card>
      </div>
    </div>
  </div>
</template>

<script lang="ts" setup>
import { inject, onMounted, ref, Ref } from "vue";
import { BasePageResult, User, userKey, VideoList } from "@/types";
import SmallVideoCard from "@/components/SmallVideoCard.vue";
import router from "@/router";
import axios from "@/plugins/axios";
import { Message } from "@arco-design/web-vue";
import { IconEmpty } from "@arco-design/web-vue/es/icon";

const userId = router.currentRoute.value.params.id;
const user = inject(userKey) as Readonly<Ref<User | null>>;

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
  @apply h-full flex justify-center flex-wrap gap-8 p-4 lg:justify-start lg:gap-0;

  .video-list__item {
    box-sizing: content-box;
    display: flex;
    align-items: center;
    justify-content: center;
    @apply mb-4 lg:w-[calc(100%/5)];
  }
}
</style>
