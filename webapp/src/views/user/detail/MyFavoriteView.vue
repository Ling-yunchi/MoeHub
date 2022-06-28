<template>
  <div class="favorite-main-container">
    <div class="section-title">
      <span class="section-title__text"> 我的收藏 </span>
    </div>
    <div class="video-list">
      <div class="video-list__item" v-for="video in videoList" :key="video.id">
        <small-video-card
          :cover="video.coverUrl"
          :length="video.length"
          :title="video.title"
          :views="102301"
          :video-url="`/video/${video.id}`"
          :time="video.createAt"
        >
        </small-video-card>
      </div>
    </div>
  </div>
</template>

<script lang="ts" setup>
import { VideoList } from "@/types";
import SmallVideoCard from "@/components/SmallVideoCard.vue";



const videoList: VideoList[] = [
  {
    id: "1",
    coverUrl: "/cover.webp",
    length: 100,
    title: "这眼睛里可不兴有爱心啊！",
    authorId: "1",
    avatar: "/avatar.jpg",
    authorName: "Ling-yunchi",
    views: 102301,
    createAt: "2020-01-01",
  },
  {
    id: "1",
    coverUrl: "/cover.webp",
    length: 100,
    title: "这眼睛里可不兴有爱心啊！",
    authorId: "1",
    avatar: "/avatar.jpg",
    authorName: "Ling-yunchi",
    views: 102301,
    createAt: "2020-01-01",
  },
  {
    id: "1",
    coverUrl: "/cover.webp",
    length: 100,
    title: "这眼睛里可不兴有爱心啊！",
    authorId: "1",
    avatar: "/avatar.jpg",
    authorName: "Ling-yunchi",
    views: 102301,
    createAt: "2020-01-01",
  },
  {
    id: "1",
    coverUrl: "/cover.webp",
    length: 100,
    title: "这眼睛里可不兴有爱心啊！",
    authorId: "1",
    avatar: "/avatar.jpg",
    authorName: "Ling-yunchi",
    views: 102301,
    createAt: "2020-01-01",
  },
  {
    id: "1",
    coverUrl: "/cover.webp",
    length: 100,
    title: "这眼睛里可不兴有爱心啊！",
    authorId: "1",
    avatar: "/avatar.jpg",
    authorName: "Ling-yunchi",
    views: 102301,
    createAt: "2020-01-01",
  },
  {
    id: "1",
    coverUrl: "/cover.webp",
    length: 100,
    title: "这眼睛里可不兴有爱心啊！",
    authorId: "1",
    avatar: "/avatar.jpg",
    authorName: "Ling-yunchi",
    views: 102301,
    createAt: "2020-01-01",
  },
];
</script>

<style lang="scss" scoped>
.favorite-main-container {
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
