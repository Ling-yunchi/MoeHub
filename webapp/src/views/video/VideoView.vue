<template>
  <a-layout>
    <header-view></header-view>
    <a-layout-content>
      <div class="video-main-container">
        <div class="video-player-container">
          <player class="video-player">
            <video crossorigin="anonymous" poster="/test-cover.jpg" controls>
              <source src="/test-video.mp4" type="video/mp4" />
            </video>
            <default-ui></default-ui>
          </player>
          <div class="video-footer">
            <a-button class="video-like" type="primary" icon="icon-heart">
              <icon-heart />
              <span>喜欢</span>
            </a-button>
            <a-button class="video-favorite" type="primary" icon="icon-heart">
              <icon-star />
              <span>收藏</span>
            </a-button>
          </div>
        </div>
        <div class="video-info-container">
          <div class="video-info">
            <div class="video-info-title">
              <h1>【湊あくあ】夜に駆ける / 奔向夜晚【翻唱】</h1>
            </div>
            <div class="video-info-author">
              <a href="/user/1">
                <a-avatar :size="50">
                  <img src="/avatar.jpg" alt="avatar" />
                </a-avatar>
              </a>
              <span class="author-info">
                <a class="video-info-author-name">Ling-yunchi</a>
                <div class="video-info-author-time">
                  <icon-clock-circle />
                  2020-01-01
                </div>
              </span>
            </div>
            <div class="video-info-other">
              <span><icon-eye /> 102301</span>
              <span><icon-heart /> 114514</span>
              <span><icon-star /> 11451</span>
            </div>
          </div>
          <div class="comment-container">
            <div class="comment-title">
              <h2>评论</h2>
            </div>
            <div class="comment-input">
              <a-input
                placeholder="说点什么吧..."
                size="large"
                prefix="icon-comment"
              ></a-input>
              <a-button style="height: 36px">
                <icon-send />
              </a-button>
            </div>
            <div class="comment-list">
              <a-comment
                author="Socrates"
                content="Comment body content."
                datetime="1 hour"
              >
                <template #actions>
                  <span class="action" key="heart" @click="onLikeChange">
                    <span v-if="like">
                      <IconHeartFill :style="{ color: '#f53f3f' }" />
                    </span>
                    <span v-else>
                      <IconHeart />
                    </span>
                    {{ 83 + (like ? 1 : 0) }}
                  </span>
                  <span class="action" key="star" @click="onStarChange">
                    <span v-if="star">
                      <IconStarFill style="color: red" />
                    </span>
                    <span v-else>
                      <IconStar />
                    </span>
                    {{ 3 + (star ? 1 : 0) }}
                  </span>
                  <span class="action" key="reply">
                    <IconMessage /> Reply
                  </span>
                </template>
                <template #avatar>
                  <a-avatar>
                    <img
                      alt="avatar"
                      src="https://p1-arco.byteimg.com/tos-cn-i-uwbnlip3yd/3ee5f13fb09879ecb5185e440cef6eb9.png~tplv-uwbnlip3yd-webp.webp"
                    />
                  </a-avatar>
                </template>
              </a-comment>
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
  IconEye,
  IconStar,
  IconStarFill,
  IconSend,
} from "@arco-design/web-vue/es/icon";
import { onMounted } from "vue";
import router from "@/router";
onMounted(() => {
  console.log(router.currentRoute.value.params.id);
});
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
        margin-right: 10px;
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
      margin-bottom: 20px;
    }
  }
}
</style>
