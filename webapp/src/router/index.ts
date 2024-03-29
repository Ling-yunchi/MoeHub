import { createRouter, createWebHashHistory, RouteRecordRaw } from "vue-router";
import MainView from "@/views/main/MainView.vue";
import HomeView from "@/views/main/home/HomeView.vue";
import VideosView from "@/views/main/videos/VideosView.vue";
import IndexView from "@/views/main/index/IndexView.vue";
import AboutView from "@/views/main/about/AboutView.vue";
import UserView from "@/views/user/UserView.vue";
import LoginView from "@/views/user/login/LoginView.vue";
import RegisterView from "@/views/user/register/RegisterView.vue";
import DetailView from "@/views/user/detail/DetailView.vue";
import VideoView from "@/views/video/VideoView.vue";
import fzfView from "@/views/404View.vue";

const routes: Array<RouteRecordRaw> = [
  {
    path: "/",
    name: "main",
    component: MainView,
    redirect: "home",
    children: [
      {
        path: "home",
        name: "home",
        component: HomeView,
      },
      {
        path: "videos",
        name: "videos",
        component: VideosView,
      },
      {
        path: "index",
        name: "index",
        component: IndexView,
      },
      {
        path: "about",
        name: "about",
        component: AboutView,
      },
    ],
  },
  {
    path: "/user",
    name: "user",
    component: UserView,
    redirect: "/user/login",
    children: [
      {
        path: ":id",
        name: "detail",
        component: DetailView,
        redirect: (to) => "/user/" + to.params.id + "/home",
        children: [
          {
            path: "home",
            name: "userHome",
            component: () => import("@/views/user/detail/UserHomeView.vue"),
          },
          {
            path: "favorite",
            name: "myFavorite",
            component: () => import("@/views/user/detail/MyFavoriteView.vue"),
          },
          {
            path: "video",
            name: "myVideo",
            component: () => import("@/views/user/detail/MyVideoView.vue"),
          },
          {
            path: "upload",
            name: "upload",
            component: () => import("@/views/user/detail/UploadView.vue"),
          },
          {
            path: "setting",
            name: "setting",
            component: () => import("@/views/user/detail/SettingView.vue"),
          },
        ],
      },
      {
        path: "login",
        name: "login",
        component: LoginView,
      },
      {
        path: "register",
        name: "register",
        component: RegisterView,
      },
    ],
  },
  {
    path: "/video/:id",
    name: "video",
    component: VideoView,
  },
  {
    path: "/search",
    name: "search",
    component: () => import("@/views/search/SearchView.vue"),
  },
  // 404 route
  {
    path: "/:pathMatch(.*)*",
    name: "404",
    component: fzfView,
  },
];

const router = createRouter({
  history: createWebHashHistory(process.env.BASE_URL),
  routes,
});

export default router;
