<template>
  <div class="video-card-container">
    <div class="video-cover">
      <router-link :to="videoUrl" target="_blank">
        <img style="object-fit: cover" :src="coverUrl" alt="cover" />
      </router-link>
      <div class="video-length">{{ lengthString(length) }}</div>
    </div>
    <div class="video-info">
      <div class="author-avatar">
        <router-link :to="authorUrl" target="_blank">
          <a-avatar>
            <img
              style="object-fit: cover"
              :src="avatar ? avatar : require('@/assets/default-avatar.jpg')"
              alt="avatar"
            />
          </a-avatar>
        </router-link>
      </div>
      <div class="video-text">
        <router-link :to="videoUrl" target="_blank">
          <p class="video-title">{{ title }}</p>
        </router-link>
        <p class="video-info">
          {{ authorName }} · {{ viewsString(views) }}次播放
        </p>
      </div>
    </div>
  </div>
</template>

<script lang="ts" setup>
import { defineProps } from "vue";

const props = defineProps({
  coverUrl: {
    type: String,
    default: "",
  },
  length: {
    type: Number,
    default: 0,
  },
  title: {
    type: String,
    default: "",
  },
  avatar: {
    type: String,
    default: "",
  },
  authorName: {
    type: String,
    default: "",
  },
  authorUrl: {
    type: String,
    default: "",
  },
  views: {
    type: Number,
    default: 0,
  },
  videoUrl: {
    type: String,
    default: "",
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
  outline: 0;
  color: inherit;
  text-decoration: none;
  box-sizing: inherit;
}
.video-card-container {
  width: 360px;
  overflow: hidden;
  margin: 0;
  .video-cover {
    width: 100%;
    height: 203px;
    position: relative;
    margin-bottom: 8px;
    border-radius: 8px;
    overflow: hidden;
    img {
      width: 100%;
    }
    .video-length {
      position: absolute;
      bottom: 14px;
      right: 16px;
      color: #fff;
      font-size: 12px;
      font-weight: bold;
      border-radius: 4px;
    }
  }
  .video-info {
    display: flex;
    padding: 0 4px;
    .author-avatar {
      margin-right: 8px;
      flex-shrink: 0;
      width: 40px;
      height: 40px;
      border-radius: 50%;
      overflow: hidden;
      a {
        outline: 0;
        color: inherit;
        text-decoration: none;
        box-sizing: inherit;
        img {
          width: 100%;
        }
      }
    }
    .video-text {
      flex: 1;
      .video-title {
        font-size: 16px;
        font-weight: 400;
        color: var(--color-neutral-8);
        margin: 0 0 4px;
        overflow: hidden;
        text-overflow: ellipsis;
        word-break: break-word;
        line-height: 20px;
      }
      .video-info {
        margin-top: 5px;
        font-size: 14px;
        color: var(--color-neutral-5);
        overflow: hidden;
        text-overflow: ellipsis;
        word-break: break-word;
      }
    }
  }
}
</style>
