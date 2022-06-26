<template>
  <a-layout>
    <header-view></header-view>
    <a-layout-content>
      <div class="video-main-container">
        <div class="video-player-container">
          <player class="video-player">
            <video crossorigin="anonymous" poster="/test-cover.jpg" controls>
              <source
                src="http://39.103.135.63:9000/moehub/%E3%80%90%E6%B9%8A%E3%81%82%E3%81%8F%E3%81%82%E3%80%91%E5%A4%9C%E3%81%AB%E9%A7%86%E3%81%91%E3%82%8B%20_%20%E5%A5%94%E5%90%91%E5%A4%9C%E6%99%9A%E3%80%90%E7%BF%BB%E5%94%B1%E3%80%91%20-%201.%E5%A4%9C%E3%81%AB%E9%A7%86%E3%81%91%E3%82%8B%20%E5%AE%9A%E7%A8%BF%28Av330487200%2CP1%29.mp4"
                type="video/mp4"
              />
            </video>
            <default-ui></default-ui>
          </player>
          <div class="video-footer">
            <a-button class="video-like" status="normal">
              <icon-heart-fill style="color: #fa8dac; margin-right: 5px" />
              <span>喜欢</span>
            </a-button>
            <a-button class="video-favorite" status="normal">
              <icon-star-fill style="color: #f8e302; margin-right: 5px" />
              <span>收藏</span>
            </a-button>
          </div>
        </div>
        <div class="video-info-container">
          <div class="video-info">
            <div class="video-info-title">
              <h1>【湊あくあ】夜に駆ける / 奔向夜晚【翻唱】</h1>
            </div>
            <div class="video-info__description">
              <a-typography-paragraph
                :ellipsis="{
                  rows: 3,
                  expandable: true,
                }"
                style="padding: 0 20px 0 20px"
              >
                <div v-html="description.replaceAll('\n', '<br/>')"></div>
              </a-typography-paragraph>
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
              <a-input placeholder="说点什么吧..." size="large">
                <template #prepend>
                  <icon-message />
                </template>
              </a-input>
              <a-button style="height: 36px">
                <icon-send />
              </a-button>
            </div>
            <div>
              <a-comment
                author="Ling-yunchi"
                datetime="1 hour"
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
                      <img alt="avatar" :src="comment.avatar" />
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
import { CommentList } from "@/types";
const description = ref(
  "世界でいちばんおニオンさま!\n" +
    "お誕生日おめでとう！~\n" +
    "【初めて会った日から】\n" +
    "【僕の心の全てを奪った】\n" +
    "これからももっともっと応援するよ——d(*・ω・*)b♪\n" +
    "------------------------------------------------------------------------------\n" +
    "本家様：YOASOBI様\n" +
    "https://www.youtube.com/watch?v=x8VYWazR5mE\n" +
    "分镜参考: BV1h5411a7LC\n" +
    "------------------------------------------------------------------------------\n" +
    "Cover：\n" +
    "vocal：湊あくあ\n" +
    "illust：瑠\n" +
    "mix：星月夜舞\n" +
    "movie：星奕工作室\n" +
    "organizer: ZestXteam\n" +
    "※中文歌词参考自互联网\n" +
    "------------------------------------------------------------------------------\n" +
    "推特关注不迷路↓\n" +
    "Twitter：https://twitter.com/minatoaqua\n" +
    "Twitter话题　#湊あくあ\n" +
    "绘画　#あくあーと\n" +
    "粉丝　#あくあクルー"
);
const commentList = ref<CommentList[]>([
  {
    id: "1",
    userId: "1",
    username: "Ling-yunchi",
    avatar: "/avatar.jpg",
    time: "2020-01-01",
    content: "夸宝可爱捏🥰🥰🥰\n\n\n\n\n🥵🥵🥵夸宝🥵🥵🥵我的夸宝🥵🥵🥵",
  },
  {
    id: "2",
    userId: "1",
    username: "Ling-yunchi",
    avatar: "/avatar.jpg",
    time: "2020-01-01",
    content: "夸宝可爱捏🥰🥰🥰\n\n\n\n\n🥵🥵🥵夸宝🥵🥵🥵我的夸宝🥵🥵🥵",
  },
]);

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
