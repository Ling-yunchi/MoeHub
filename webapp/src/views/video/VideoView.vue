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
              ref="mainPlayer"
            >
              <source :src="videoInfo.videoUrl" type="video/mp4" />
            </video>
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
              <a :href="`/user/${videoInfo.authorId}`">
                <a-avatar :size="50">
                  <img
                    style="object-fit: cover"
                    :src="videoInfo.authorAvatar"
                    alt="avatar"
                  />
                </a-avatar>
              </a>
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
                  <a :href="`/user/${comment.userId}`">
                    <a-avatar>
                      <img
                        style="object-fit: cover"
                        alt="avatar"
                        :src="comment.avatar"
                      />
                    </a-avatar>
                  </a>
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
import { BasePageResult, BaseResult, CommentList } from "@/types";
import { MediaPlayer } from "@vime/core";
import axios from "@/plugins/axios";
import { Message } from "@arco-design/web-vue";

const videoInfo = ref({
  id: "1",
  title: "【湊あくあ】夜に駆ける / 奔向夜晚【翻唱】",
  videoUrl:
    "http://39.103.135.63:9000/moehub/%E3%80%90%E6%B9%8A%E3%81%82%E3%81%8F%E3%81%82%E3%80%91%E5%A4%9C%E3%81%AB%E9%A7%86%E3%81%91%E3%82%8B%20_%20%E5%A5%94%E5%90%91%E5%A4%9C%E6%99%9A%E3%80%90%E7%BF%BB%E5%94%B1%E3%80%91%20-%201.%E5%A4%9C%E3%81%AB%E9%A7%86%E3%81%91%E3%82%8B%20%E5%AE%9A%E7%A8%BF%28Av330487200%2CP1%29.mp4",
  coverUrl: "/test-cover.jpg",
  description:
    "世界でいちばんおニオンさま!\nお誕生日おめでとう！~\n【初めて会った日から】\n【僕の心の全てを奪った】\nこれからももっともっと応援するよ——d(*・ω・*)b♪\n-------------\n本家様：YOASOBI様\nhttps://www.youtube.com/watch?v=x8VYWazR5mE\n分镜参考: BV1h5411a7LC\n-------------\nCover：\nvocal：湊あくあ\nillust：瑠\nmix：星月夜舞\nmovie：星奕工作室\norganizer: ZestXteam\n※中文歌词参考自互联网\n---------------\n推特关注不迷路↓\nTwitter：https://twitter.com/minatoaqua\nTwitter话题　#湊あくあ\n绘画　#あくあーと\n粉丝　#あくあクルー",
  length: 0,
  authorId: "1",
  authorName: "龗云螭",
  authorAvatar: "/avatar.jpg",
  createAt: "2020-01-01",
  views: 114514,
  likes: 11451,
  favorites: 1111,
  isFavorite: false,
  isLiked: false,
});
onMounted(() => {
  axios
    .get("/api/video/getVideoInfo", {
      params: { videoId: router.currentRoute.value.params.id },
    })
    .then((res) => {
      const result = res.data as BaseResult<any>;
      if (result.success) {
        videoInfo.value = result.data;
      } else {
        console.log(result.message);
        router.push("/404");
      }
    });
});

const mainPlayer = ref<MediaPlayer>();

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
  if (!like) {
    axios
      .get("/api/like/setLike", {
        params: {
          userId: videoInfo.value.authorId,
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
          userId: videoInfo.value.authorId,
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
    avatar: "/avatar.jpg",
    createAt: "2020-01-01",
    content: "夸宝可爱捏🥰🥰🥰\n\n\n\n\n🥵🥵🥵夸宝🥵🥵🥵我的夸宝🥵🥵🥵",
  },
  {
    id: "2",
    userId: "1",
    nickname: "Ling-yunchi",
    avatar: "/avatar.jpg",
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
    min-width: 480px;
    max-width: 900px;

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
      justify-content: start;

      .video-like {
        width: 93px;
        margin-right: 10px;
      }

      .video-favorite {
        width: 93px;
      }
    }
  }

  .video-info-container {
    height: 100%;
    width: 100%;
    min-width: 480px;
    max-width: 900px;

    .video-info {
      width: 100%;

      .video-info-title {
        width: 100%;
        margin-bottom: 10px;
        color: var(--color-neutral-9);
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
        margin-top: 10px;

        span {
          margin-right: 10px;
          color: var(--color-neutral-7);
        }
      }
    }
  }

  .comment-container {
    .comment-input {
      display: flex;
      flex-direction: row;
      align-items: center;
      margin-bottom: 40px;
    }
  }
}
</style>
