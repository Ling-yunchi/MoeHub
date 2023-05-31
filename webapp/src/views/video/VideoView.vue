<template>
  <a-layout>
    <header-view></header-view>
    <a-layout-content>
      <div class="video-main-container">
        <div class="video-player-container">
          <player class="video-player">
            <video
              controls
              crossorigin="anonymous"
              :poster="videoInfo.coverUrl"
              @play="onPlay"
              id="player"
              ref="mainPlayer"
            ></video>
            <default-ui></default-ui>
          </player>
          <div class="video-footer">
            <a-button
              v-if="videoInfo.isLiked"
              class="video-like"
              status="normal"
              @click="likes(false)"
            >
              <icon-heart-fill style="color: #fa8dac; margin-right: 5px" />
              <span>已喜欢</span>
            </a-button>
            <a-button
              v-else
              class="video-like"
              status="normal"
              @click="likes(true)"
            >
              <icon-heart style="color: #fa8dac; margin-right: 5px" />
              <span>喜欢</span>
            </a-button>
            <a-button
              v-if="videoInfo.isFavorite"
              class="video-favorite"
              status="normal"
              @click="favorite(false)"
            >
              <icon-star-fill style="color: #f8e302; margin-right: 5px" />
              <span>已收藏</span>
            </a-button>
            <a-button
              v-else
              class="video-favorite"
              status="normal"
              @click="favorite(true)"
            >
              <icon-star style="color: #f8e302; margin-right: 5px" />
              <span>收藏</span>
            </a-button>
          </div>
        </div>
        <div class="video-info-container">
          <div class="video-info">
            <div class="video-info-title">
              <h1>{{ videoInfo.title }}</h1>
              <div
                class="bg-gray-200 py-1 px-2 rounded-sm text-base text-slate-700 font-bold whitespace-nowrap"
              >
                {{ videoInfo.category }}
              </div>
            </div>
            <div class="video-info__description">
              <a-typography-paragraph
                :ellipsis="{
                  rows: 3,
                  expandable: true,
                }"
                style="padding: 0 20px 0 20px"
              >
                <div
                  v-html="videoInfo.description.replaceAll('\n', '<br/>')"
                ></div>
              </a-typography-paragraph>
            </div>
            <div class="video-info-author">
              <router-link :to="`/user/${videoInfo.authorId}`">
                <a-avatar :size="50">
                  <img
                    style="object-fit: cover"
                    :src="
                      videoInfo.authorAvatar
                        ? videoInfo.authorAvatar
                        : require('@/assets/default-avatar.jpg')
                    "
                    alt="avatar"
                  />
                </a-avatar>
              </router-link>
              <span class="author-info">
                <a class="video-info-author-name">{{ videoInfo.authorName }}</a>
                <span class="video-info-author-time">
                  <icon-clock-circle />
                  {{ videoInfo.createAt }}
                </span>
              </span>
            </div>
            <div class="video-info-other">
              <span><icon-eye /> {{ videoInfo.views }}</span>
              <span><icon-heart /> {{ videoInfo.likes }}</span>
              <span><icon-star /> {{ videoInfo.favorites }}</span>
            </div>
          </div>
          <div class="comment-container">
            <div class="comment-title">
              <h2>评论</h2>
            </div>
            <div class="comment-input">
              <a-input
                v-model="commentInput"
                placeholder="说点什么吧..."
                size="large"
              >
                <template #prepend>
                  <icon-message />
                </template>
              </a-input>
              <a-button @click="addComment" style="height: 36px">
                <icon-send />
              </a-button>
            </div>
            <div>
              <a-comment
                :author="comment.nickname"
                :datetime="comment.createAt"
                style="
                  margin-bottom: 20px;
                  white-space: pre-wrap;
                  background-color: var(--color-neutral-1);
                  border-radius: 4px;
                  padding: 10px;
                "
                v-for="comment in commentList"
                :key="comment.id"
              >
                <template #avatar>
                  <router-link :to="`/user/${comment.userId}`">
                    <a-avatar>
                      <img
                        style="object-fit: cover"
                        alt="avatar"
                        :src="
                          comment.avatar
                            ? comment.avatar
                            : require('@/assets/default-avatar.jpg')
                        "
                      />
                    </a-avatar>
                  </router-link>
                </template>
                <template #content>
                  <a-typography-paragraph
                    :ellipsis="{
                      rows: 3,
                      expandable: true,
                    }"
                    style="margin-top: 10px"
                  >
                    <div
                      v-html="comment.content.replaceAll('\n', '<br/>')"
                    ></div>
                  </a-typography-paragraph>
                </template>
              </a-comment>
              <div
                style="
                  width: 100%;
                  height: 100px;
                  display: flex;
                  align-items: center;
                  justify-content: center;
                "
              >
                <span style="font-size: 15px; color: var(--color-neutral-6)"
                  >没有更多评论啦qwqq</span
                >
              </div>
            </div>
          </div>
        </div>
      </div>
    </a-layout-content>
  </a-layout>
</template>

<script lang="ts" setup>
import HeaderView from "@/views/HeaderView.vue";
import { Player, DefaultUi } from "@vime/vue-next";
import {
  IconHeart,
  IconHeartFill,
  IconClockCircle,
  IconMessage,
  IconEye,
  IconStar,
  IconStarFill,
  IconSend,
} from "@arco-design/web-vue/es/icon";
import { onMounted, ref } from "vue";
import router from "@/router";
import { BasePageResult, BaseResult, CommentList, VideoInfo } from "@/types";
import { MediaPlayer } from "@vime/core";
import axios from "@/plugins/axios";
import { Message } from "@arco-design/web-vue";

const videoInfo = ref<VideoInfo>({
  id: "1",
  title: "【湊あくあ】夜に駆ける / 奔向夜晚【翻唱】",
  videoUrl: "",
  coverUrl: "",
  description: "",
  length: 0,
  authorId: "1",
  authorName: "龗云螭",
  authorAvatar: "",
  createAt: "2020-01-01",
  views: 114514,
  likes: 11451,
  favorites: 1111,
  isFavorite: false,
  isLiked: false,
  category: "动画",
});

const mainPlayer = ref<MediaPlayer>();
onMounted(() => {
  axios
    .get("/api/video/getVideoInfo", {
      params: { videoId: router.currentRoute.value.params.id },
    })
    .then((res) => {
      const result = res.data as BaseResult<VideoInfo>;
      if (result.success) {
        videoInfo.value = result.data;
        let source = `<source src="${videoInfo.value.videoUrl}" type="video/mp4">`;
        let player = document.getElementById("player") as HTMLVideoElement;
        player.innerHTML = source;
      } else {
        console.log(result.message);
        router.push("/404");
      }
    });
});

const played = ref(false);
const onPlay = () => {
  if (!played.value) {
    played.value = true;
    axios
      .get("/api/video/view", {
        params: { videoId: videoInfo.value.id },
      })
      .catch((err) => {
        console.log(err);
      });
  }
};

const likes = (like: boolean) => {
  if (like) {
    axios
      .get("/api/like/setLike", {
        params: {
          videoId: videoInfo.value.id,
        },
      })
      .then((data) => {
        const res = data.data as BaseResult<never>;
        if (res.success) {
          Message.success(res.message);
          videoInfo.value.isLiked = like;
          videoInfo.value.likes = like
            ? videoInfo.value.likes + 1
            : videoInfo.value.likes - 1;
        } else {
          Message.error(res.message);
        }
      });
  } else {
    axios
      .get("/api/like/cancelLike", {
        params: {
          videoId: videoInfo.value.id,
        },
      })
      .then((data) => {
        const res = data.data as BaseResult<never>;
        if (res.success) {
          Message.success(res.message);
          videoInfo.value.isLiked = like;
          videoInfo.value.likes = like
            ? videoInfo.value.likes + 1
            : videoInfo.value.likes - 1;
        } else {
          Message.error(res.message);
        }
      });
  }
};
const favorite = (favor: boolean) => {
  axios
    .get("/api/favorite/isFavorite", {
      params: { videoId: videoInfo.value.id },
    })
    .then((data) => {
      const res = data.data as BaseResult<never>;
      if (res.success) {
        Message.success(res.message);
        videoInfo.value.isFavorite = favor;
        videoInfo.value.favorites = favor
          ? videoInfo.value.favorites + 1
          : videoInfo.value.favorites - 1;
      } else {
        Message.error(res.message);
      }
    });
};

const commentList = ref<CommentList[]>([
  {
    id: "1",
    userId: "1",
    nickname: "Ling-yunchi",
    avatar: "",
    createAt: "2020-01-01",
    content: "夸宝可爱捏🥰🥰🥰\n\n\n\n\n🥵🥵🥵夸宝🥵🥵🥵我的夸宝🥵🥵🥵",
  },
  {
    id: "2",
    userId: "1",
    nickname: "Ling-yunchi",
    avatar: "",
    createAt: "2020-01-01",
    content: "夸宝可爱捏🥰🥰🥰\n\n\n\n\n🥵🥵🥵夸宝🥵🥵🥵我的夸宝🥵🥵🥵",
  },
]);

const getComments = () => {
  axios
    .get<BasePageResult<CommentList>>("/api/comments/search", {
      params: {
        videoId: router.currentRoute.value.params.id,
        pageNum: 1,
        pageSize: 100,
      },
    })
    .then((res) => {
      if (res.data.success) {
        commentList.value = res.data.data;
      } else {
        Message.error(res.data.message);
      }
    });
};
onMounted(getComments);
const commentInput = ref("");
const addComment = () => {
  if (commentInput.value.length === 0) {
    Message.warning("评论内容不能为空");
    return;
  }
  axios
    .post("/api/comments/add", {
      content: commentInput.value,
      videoId: router.currentRoute.value.params.id,
    })
    .then((res) => {
      const result = res.data as BaseResult<never>;
      if (result.success) {
        Message.success(result.message);
        getComments();
      } else {
        Message.error(result.message);
      }
    });
};
</script>

<style lang="scss" scoped>
.video-main-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  height: 100%;

  .video-player-container {
    height: 100%;
    width: 100%;
    @apply w-full max-w-4xl;

    .video-player {
      width: 100%;

      video {
        width: 100%;
        aspect-ratio: 16/9;
        background-color: black;
      }
    }

    .video-footer {
      width: 100%;
      margin-top: 10px;
      display: flex;
      align-items: center;
      justify-content: flex-start;

      .video-like {
        width: 93px;
        margin: 0 10px;
      }

      .video-favorite {
        width: 93px;
      }
    }
  }

  .video-info-container {
    height: 100%;
    width: 100%;
    @apply w-full max-w-4xl px-2;

    .video-info {
      width: 100%;
      @apply mt-4;

      .video-info-title {
        width: 100%;
        margin-bottom: 10px;
        color: var(--color-neutral-9);
        @apply text-xl font-bold flex justify-between items-center;
      }

      .video-info__description {
        width: 100%;
        margin-bottom: 10px;
        color: var(--color-neutral-8);
        white-space: pre-wrap;
        line-height: 20px;
        font-size: 14px;
      }

      .video-info-author {
        display: flex;
        justify-content: flex-start;
        align-items: center;

        .author-info {
          display: inline-flex;
          flex-direction: column;
          align-items: flex-start;
          height: 100%;
          margin-left: 10px;

          .video-info-author-name {
            font-weight: bold;
            font-size: 18px;
            color: var(--color-neutral-8);
            margin-bottom: 10px;
          }

          .video-info-author-time {
            font-size: 12px;
            font-weight: bold;
            color: var(--color-neutral-7);
          }
        }
      }

      .video-info-other {
        @apply my-4 space-x-4;

        span {
          color: var(--color-neutral-7);
        }
      }
    }
  }

  .comment-container {
    .comment-title {
      @apply text-lg font-bold mb-2;
    }

    .comment-input {
      display: flex;
      flex-direction: row;
      align-items: center;
      @apply mb-4;
    }
  }
}
</style>
