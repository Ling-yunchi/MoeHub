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
          :cover="video.cover"
          :length="video.length"
          :title="video.title"
          :views="102301"
          :video-url="`/video/${video.id}`"
          :time="video.time"
        >
        </small-video-card>
      </div>
    </div>
  </div>
</template>

<script lang="ts" setup>
import { inject, Ref } from "vue";
import { BaseResult, User, VideoList } from "@/types";
import SmallVideoCard from "@/components/SmallVideoCard.vue";
import router from "@/router";
import axios from "@/plugins/axios";
import { Video } from "@vime/core/dist/types/components/providers/video/video";

const userId = router.currentRoute.value.params.id;

const user = inject<Ref<User>>("user") as Ref<User>;

const videoList: VideoList[] = [
  {
    id: "1",
    cover: "/cover.webp",
    length: 100,
    title: "这眼睛里可不兴有爱心啊！",
    authorId: "1",
    avatar: "/avatar.jpg",
    authorName: "Ling-yunchi",
    views: 102301,
    time: "2020-01-01",
  },
  {
    id: "1",
    cover: "/cover.webp",
    length: 100,
    title: "这眼睛里可不兴有爱心啊！",
    authorId: "1",
    avatar: "/avatar.jpg",
    authorName: "Ling-yunchi",
    views: 102301,
    time: "2020-01-01",
  },
  {
    id: "1",
    cover: "/cover.webp",
    length: 100,
    title: "这眼睛里可不兴有爱心啊！",
    authorId: "1",
    avatar: "/avatar.jpg",
    authorName: "Ling-yunchi",
    views: 102301,
    time: "2020-01-01",
  },
  {
    id: "1",
    cover: "/cover.webp",
    length: 100,
    title: "这眼睛里可不兴有爱心啊！",
    authorId: "1",
    avatar: "/avatar.jpg",
    authorName: "Ling-yunchi",
    views: 102301,
    time: "2020-01-01",
  },
  {
    id: "1",
    cover: "/cover.webp",
    length: 100,
    title: "这眼睛里可不兴有爱心啊！",
    authorId: "1",
    avatar: "/avatar.jpg",
    authorName: "Ling-yunchi",
    views: 102301,
    time: "2020-01-01",
  },
  {
    id: "1",
    cover: "/cover.webp",
    length: 100,
    title: "这眼睛里可不兴有爱心啊！",
    authorId: "1",
    avatar: "/avatar.jpg",
    authorName: "Ling-yunchi",
    views: 102301,
    time: "2020-01-01",
  },
];
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
