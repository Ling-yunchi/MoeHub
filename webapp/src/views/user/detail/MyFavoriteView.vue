<template>
  <div class="favorite-main-container">
    <div class="section-title">
      <span class="section-title__text"> 我的收藏 </span>
    </div>
    <div
      v-if="videoList.length === 0"
      class="w-full flex flex-col items-center text-slate-600"
    >
      <icon-empty size="3rem" class="" />
      <span class="mt-4">快去收藏喜欢的视频吧~</span>
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
import { BasePageResult, VideoList } from "@/types";
import SmallVideoCard from "@/components/SmallVideoCard.vue";
import router from "@/router";
import { onMounted, ref } from "vue";
import axios from "@/plugins/axios";
import { Message } from "@arco-design/web-vue";
import { IconEmpty } from "@arco-design/web-vue/es/icon";

const userId = router.currentRoute.value.params.id;

const videoList = ref<VideoList[]>([]);
onMounted(() => {
  axios
    .get<BasePageResult<VideoList>>("/api/favorite/showMyFavorite", {
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

// const videoList: VideoList[] = [
//   {
//     id: "1",
//     coverUrl: "/cover.webp",
//     length: 100,
//     title: "这眼睛里可不兴有爱心啊！",
//     authorId: "1",
//     avatar: "/avatar.jpg",
//     authorName: "Ling-yunchi",
//     views: 102301,
//     createAt: "2020-01-01",
//   },
//   {
//     id: "1",
//     coverUrl: "/cover.webp",
//     length: 100,
//     title: "这眼睛里可不兴有爱心啊！",
//     authorId: "1",
//     avatar: "/avatar.jpg",
//     authorName: "Ling-yunchi",
//     views: 102301,
//     createAt: "2020-01-01",
//   },
//   {
//     id: "1",
//     coverUrl: "/cover.webp",
//     length: 100,
//     title: "这眼睛里可不兴有爱心啊！",
//     authorId: "1",
//     avatar: "/avatar.jpg",
//     authorName: "Ling-yunchi",
//     views: 102301,
//     createAt: "2020-01-01",
//   },
//   {
//     id: "1",
//     coverUrl: "/cover.webp",
//     length: 100,
//     title: "这眼睛里可不兴有爱心啊！",
//     authorId: "1",
//     avatar: "/avatar.jpg",
//     authorName: "Ling-yunchi",
//     views: 102301,
//     createAt: "2020-01-01",
//   },
//   {
//     id: "1",
//     coverUrl: "/cover.webp",
//     length: 100,
//     title: "这眼睛里可不兴有爱心啊！",
//     authorId: "1",
//     avatar: "/avatar.jpg",
//     authorName: "Ling-yunchi",
//     views: 102301,
//     createAt: "2020-01-01",
//   },
//   {
//     id: "1",
//     coverUrl: "/cover.webp",
//     length: 100,
//     title: "这眼睛里可不兴有爱心啊！",
//     authorId: "1",
//     avatar: "/avatar.jpg",
//     authorName: "Ling-yunchi",
//     views: 102301,
//     createAt: "2020-01-01",
//   },
// ];
</script>

<style lang="scss" scoped>
.favorite-main-container {
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
