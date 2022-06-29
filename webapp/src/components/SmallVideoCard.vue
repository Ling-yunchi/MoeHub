<template>
  <div class="small-video-card">
    <router-link class="cover" :to="videoUrl">
      <img style="object-fit: cover" :src="coverUrl" :alt="title" />
      <span class="length">{{ lengthString(length) }}</span>
    </router-link>
    <router-link class="title" :to="videoUrl">{{ title }}</router-link>
    <div class="info">
      <span class="views"><icon-play-arrow />{{ viewsString(views) }}</span>
      <span><icon-clock-circle />{{ createAt }}</span>
    </div>
  </div>
</template>

<script lang="ts" setup>
import { defineProps } from "vue";
import { IconPlayArrow, IconClockCircle } from "@arco-design/web-vue/es/icon";

const props = defineProps({
  videoUrl: {
    type: String,
    required: true,
  },
  title: {
    type: String,
    required: true,
  },
  coverUrl: {
    type: String,
    required: true,
  },
  length: {
    type: Number,
    required: true,
  },
  views: {
    type: Number,
    required: true,
  },
  createAt: {
    type: String,
    required: true,
  },
});
const lengthString = (length: number) => {
  let hour = Math.floor(length / 3600);
  let minute = Math.floor((length % 3600) / 60);
  let second = Math.floor(length % 60);
  let hourStr = hour < 10 ? `0${hour}` : hour;
  let minuteStr = minute < 10 ? `0${minute}` : minute;
  let secondStr = second < 10 ? `0${second}` : second;
  if (hour > 0) {
    return `${hourStr}:${minuteStr}:${secondStr}`;
  } else if (minute > 0) {
    return `${minuteStr}:${secondStr}`;
  } else {
    return `00:${secondStr}`;
  }
};
const viewsString = (views: number) => {
  if (views < 10000) {
    return views.toString();
  } else if (views < 100000000) {
    return (views / 10000).toFixed(1) + "万";
  } else {
    return (views / 100000000).toFixed(1) + "亿";
  }
};
</script>

<style lang="scss" scoped>
a {
  text-decoration: none;
  word-break: break-all;
}
.small-video-card {
  width: 160px;
  position: relative;
  .cover {
    width: 160px;
    height: 100px;
    border-radius: 4px;
    display: block;
    overflow: hidden;
    position: relative;
    transition: all 0.3s ease;
    &:hover {
      transform: scale(1.05);
    }
    img {
      width: 100%;
      height: 100%;
      object-fit: cover;
    }
    .length {
      position: absolute;
      bottom: 0;
      right: 0;
      padding: 0 6px;
      font-size: 12px;
      line-height: 20px;
      border-radius: 5px 0 0 0;
      color: #fff;
      background: rgba(0, 0, 0, 0.5);
    }
  }
  .title {
    display: block;
    line-height: 20px;
    height: 38px;
    overflow: hidden;
    margin-top: 6px;
    margin-bottom: 3px;
    font-size: 14px;
    color: var(--color-neutral-9);
    &:hover {
      color: rgb(0, 161, 214);
    }
  }
  .info {
    span {
      display: inline-block;
      white-space: nowrap;
      height: 14px;
      line-height: 14px;
      font-size: 12px;
      overflow: hidden;
      color: var(--color-neutral-7);
    }
    .views {
      width: 72px;
    }
  }
}
</style>
