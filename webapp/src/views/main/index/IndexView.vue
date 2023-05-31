<template>
  <div class="max-w-[1600px] mx-auto">
    <div class="section-title">
      <h3 class="section-title__text">分类</h3>
    </div>
  </div>
  <div class="max-w-[1600px] mx-auto flex flex-col p-4 gap-2">
    <div
      v-for="category in categories"
      :key="category"
      class="p-4 bg-slate-100 rounded-lg"
    >
      <div class="text-xl font-bold border-b w-full mb-2">{{ category }}</div>
      <div
        class="flex items-center gap-4 overflow-x-scroll scroll-smooth"
        @wheel="scrollHorizontal"
      >
        <video-card
          v-for="video in categoryVideos[category] ?? []"
          class="min-w-[360px]"
          :key="video.id"
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
import { BaseResult } from "@/types";
import { VideoList } from "@/types";
import { Message } from "@arco-design/web-vue";
import axios from "axios";
import { ref } from "vue";
import { onMounted } from "vue";
import VideoCard from "@/components/VideoCard.vue";

const categories = ["动画", "音乐", "游戏", "美食", "科技"];
const categoryVideos = ref<Record<string, VideoList[]>>({});
onMounted(() => {
  axios
    .get<BaseResult<VideoList[]>>("/api/video/getAllVideoByCategory")
    .then((res) => {
      if (res.data.success) {
        categoryVideos.value = res.data.data.reduce((prev, cur) => {
          if (prev[cur.category]) {
            prev[cur.category].push(cur);
          } else {
            prev[cur.category] = [cur];
          }
          return prev;
        }, {} as Record<string, VideoList[]>);
      } else {
        Message.error(res.data.message);
      }
    });
});

const scrollHorizontal = (e: WheelEvent) => {
  if (e.deltaX === 0) e.preventDefault();
  if (e.currentTarget) {
    (e.currentTarget as HTMLElement).scrollLeft += e.deltaY;
  }
};
</script>
