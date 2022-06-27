<template>
  <div style="margin-top: 20px">
    <a-list>
      <template #header>
        <div
          style="
            width: 100%;
            display: flex;
            justify-content: space-between;
            align-items: center;
          "
        >
          <div class="title">已上传视频</div>
        </div>
      </template>
      <a-list-item v-for="video in list" :key="video.id">
        <div class="video-container">
          <div class="cover">
            <img :src="video.cover" alt="" />
          </div>
          <div class="video-info">
            <div class="video-info__title">
              <span>{{ video.title }}</span>
            </div>
            <div class="video-info__desc">
              <span>{{ video.description }}</span>
            </div>
            <div class="video-info__time">
              <span>{{ video.time }}</span>
            </div>
          </div>
          <div class="operation">
            <a-button
              type="primary"
              style="margin-right: 10px"
              @click="handleEdit(video.id)"
            >
              编辑
            </a-button>
            <a-popconfirm
              type="warning"
              content="确定要删除吗?"
              :ok-button-props="{ status: 'danger' }"
              @ok="handleDelete(video.id)"
            >
              <a-button type="primary" status="danger">删除</a-button>
            </a-popconfirm>
          </div>
        </div>
      </a-list-item>
    </a-list>
  </div>
</template>

<script lang="ts" setup>
import { IconPlus } from "@arco-design/web-vue/es/icon";
import router from "@/router";

let list = [
  {
    id: 1,
    title: "title",
    description: "description",
    cover: "/test-cover.jpg",
    time: "2019-01-01",
  },
  {
    id: 2,
    title: "title",
    description: "description",
    cover: "/test-cover.jpg",
    time: "2019-01-01",
  },
];
const handleDelete = (id: number) => {
  console.log(id);
};
</script>

<style lang="scss" scoped>
.video-container {
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: space-between;
  width: 100%;
  height: 100%;
  .cover {
    width: 200px;
    img {
      width: 100%;
      height: 100%;
    }
  }
  .video-info {
    width: 600px;
    display: flex;
    flex-direction: column;
    .video-info__title {
      font-size: 16px;
      font-weight: bold;
      margin-bottom: 5px;
    }
    .video-info__desc {
      font-size: 14px;
      margin-bottom: 5px;
    }
    .video-info__time {
      font-size: 12px;
      color: #999;
    }
  }
}
</style>
