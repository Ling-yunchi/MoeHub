<template>
  <a-layout>
    <header-view :search-show="false"></header-view>
    <a-layout>
      <a-layout-content>
        <div class="search-container">
          <div class="search-form">
            <a-form
              :model="searchForm"
              layout="inline"
              style="flex-direction: column"
            >
              <a-form-item>
                <a-input-group class="w-full">
                  <a-select
                    class="h-10 w-fit rounded-l-md"
                    :options="['视频', '用户']"
                    v-model="searchForm.type"
                  />
                  <a-input
                    class="h-10 w-full rounded-r-md p-2 text-sm"
                    v-model="searchForm.q"
                    placeholder="请输入关键字"
                    :prefix-icon="searchForm.q ? 'close' : 'search'"
                  >
                  </a-input>
                </a-input-group>
              </a-form-item>
              <a-form-item>
                <div
                  style="
                    width: 100%;
                    display: flex;
                    justify-content: space-between;
                    align-items: center;
                    gap: 10px;
                  "
                >
                  <a-select
                    class="h-10 w-32 rounded-md"
                    :options="['默认排序', '播放量', '收藏量']"
                    v-model="searchForm.sort"
                  ></a-select>
                  <a-button
                    class="h-10 w-32 rounded-md"
                    type="primary"
                    @click="search"
                    :disabled="!searchForm.q"
                  >
                    <icon-search style="margin-right: 5px" />
                    搜索
                  </a-button>
                </div>
              </a-form-item>
            </a-form>
          </div>
          <div class="search-result-list">
            <div
              class="search-result-item"
              v-for="video in resultList"
              :key="video.id"
            >
              <video-card
                style="transform: scale(0.9)"
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
      </a-layout-content>
    </a-layout>
  </a-layout>
</template>

<script lang="ts" setup>
import router from "@/router";
import { onMounted, ref } from "vue";
import { VideoList, BasePageResult } from "@/types";
import { IconSearch } from "@arco-design/web-vue/es/icon";
import HeaderView from "@/views/HeaderView.vue";
import VideoCard from "@/components/VideoCard.vue";
import axios from "@/plugins/axios";
import { Message } from "@arco-design/web-vue";

interface SearchQuery {
  type: string;
  q: string;
}

const query = router.currentRoute.value.query as unknown as SearchQuery;
const map: { [key: string]: string } = {
  video: "视频",
  user: "用户",
  视频: "video",
  用户: "user",
  默认排序: "",
  播放量: "views",
  收藏量: "favorite",
};
const searchForm = ref({
  type: map[query.type] || "视频",
  q: query.q || "",
  sort: "默认排序",
});

const resultList = ref<VideoList[]>([]);
const search = () => {
  router.currentRoute.value.query = {
    type: searchForm.value.type,
    q: searchForm.value.q,
  };
  if (searchForm.value.type == "视频") {
    axios
      .get<BasePageResult<VideoList>>("/api/video/searchVideoByTitle", {
        params: { videoTitle: searchForm.value.q, pageNum: 1, pageSize: 100 },
      })
      .then((res) => {
        if (res.data.success) {
          resultList.value = res.data.data;
        } else {
          Message.error(res.data.message);
        }
      });
    // axios
    //   .get<BaseResult<never>>("/api/search/updateSearchCount", {
    //     params: { videoTitle: searchForm.value.q },
    //   })
    //   .then((res) => {
    //     if (res.data.success) {
    //       Message.success(res.data.message);
    //     } else {
    //       Message.error(res.data.message);
    //     }
    //   });
  } else {
    axios
      .get<BasePageResult<VideoList>>("/api/video/searchVideoByAuthor", {
        params: { nickname: searchForm.value.q, pageNum: 1, pageSize: 100 },
      })
      .then((res) => {
        if (res.data.success) {
          resultList.value = res.data.data;
        } else {
          Message.error(res.data.message);
        }
      });
  }
};
onMounted(() => {
  search();
});
// const resultList = ref([
//   {
//     id: "1",
//     cover: "/cover.webp",
//     length: 100,
//     title: "这眼睛里可不兴有爱心啊！",
//     authorId: "1",
//     avatar: "/avatar.jpg",
//     authorName: "Ling-yunchi",
//     views: 102301,
//     time: "2020-01-01",
//   },
//   {
//     id: "1",
//     cover: "/cover.webp",
//     length: 100,
//     title: "这眼睛里可不兴有爱心啊！",
//     authorId: "1",
//     avatar: "/avatar.jpg",
//     authorName: "Ling-yunchi",
//     views: 102301,
//     time: "2020-01-01",
//   },
//   {
//     id: "1",
//     cover: "/cover.webp",
//     length: 100,
//     title: "这眼睛里可不兴有爱心啊！",
//     authorId: "1",
//     avatar: "/avatar.jpg",
//     authorName: "Ling-yunchi",
//     views: 102301,
//     time: "2020-01-01",
//   },
//   {
//     id: "1",
//     cover: "/cover.webp",
//     length: 100,
//     title: "这眼睛里可不兴有爱心啊！",
//     authorId: "1",
//     avatar: "/avatar.jpg",
//     authorName: "Ling-yunchi",
//     views: 102301,
//     time: "2020-01-01",
//   },
//   {
//     id: "1",
//     cover: "/cover.webp",
//     length: 100,
//     title: "这眼睛里可不兴有爱心啊！",
//     authorId: "1",
//     avatar: "/avatar.jpg",
//     authorName: "Ling-yunchi",
//     views: 102301,
//     time: "2020-01-01",
//   },
//   {
//     id: "1",
//     cover: "/cover.webp",
//     length: 100,
//     title: "这眼睛里可不兴有爱心啊！",
//     authorId: "1",
//     avatar: "/avatar.jpg",
//     authorName: "Ling-yunchi",
//     views: 102301,
//     time: "2020-01-01",
//   },
// ]);
</script>

<style lang="scss" scoped>
.search-container {
  display: flex;
  flex-direction: column;
  height: 100%;
  margin: 20px auto 0;
  @apply w-full max-w-5xl;

  .search-form {
    width: 100%;
    border-bottom: 2px solid #e6e6e6;
  }

  .search-result-list {
    display: flex;
    flex-direction: row;
    flex-wrap: wrap;
    @apply mt-4 justify-center lg:justify-start;

    .search-result-item {
      display: flex;
      align-items: center;
      justify-content: center;
      @apply mb-4 lg:w-[calc(100%/3)];
    }
  }
}
</style>
