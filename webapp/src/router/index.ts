import { createRouter, createWebHistory, RouteRecordRaw } from "vue-router";
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
    redirect: "/home",
    children: [
      {
        path: "/home",
        name: "home",
        component: HomeView,
      },
      {
        path: "/videos",
        name: "videos",
        component: VideosView,
      },
      {
        path: "/index",
        name: "index",
        component: IndexView,
      },
      {
        path: "/about",
        name: "about",
        component: AboutView,
      },
    ],
  },
  {
    path: "/user",
    name: "user",
    component: UserView,
    children: [
      {
        path: "/user/:id",
        name: "detail",
        component: DetailView,
      },
      {
        path: "/user/login",
        name: "login",
        component: LoginView,
      },
      {
        path: "/user/register",
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
  // 404 route
  {
    path: "/:pathMatch(.*)*",
    name: "404",
    component: fzfView,
  },
];

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes,
});

export default router;
