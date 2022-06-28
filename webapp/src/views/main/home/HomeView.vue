<template>
  <div class="home-main">
    <div class="section-title">
      <h3 class="section-title__text">热门视频</h3>
      <a class="section-title__more" href="/videos">
        <span class="section-title__more-text">查看更多</span>
        <IconRight />
      </a>
    </div>
    <div class="popular-list">
      <div
        class="popular-list__item"
        v-for="video in popularList"
        :key="video.id"
      >
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
import { IconRight } from "@arco-design/web-vue/es/icon";
import VideoCard from "@/components/VideoCard.vue";
import { BasePageResult, VideoList } from "@/types";
import { onMounted, ref } from "vue";
import axios from "@/plugins/axios";
import { Message } from "@arco-design/web-vue";

const popularList = ref<VideoList[]>([]);
onMounted(() => {
  axios
    .get<BasePageResult<VideoList>>("/api/video/showHot", {
      params: { pageNum: 1, pageSize: 100 },
    })
    .then((res) => {
      if (res.data.success) {
        popularList.value = res.data.data;
      } else {
        Message.error(res.data.message);
      }
    });
});

// const popularList: VideoList[] = [
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
a {
  outline: 0;
  color: inherit;
  text-decoration: none;
}
.home-main {
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
.popular-list {
  margin-top: 20px;
  display: flex;
  flex-wrap: wrap;
  justify-content: flex-start;
  margin-bottom: 20px;
  .popular-list__item {
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
