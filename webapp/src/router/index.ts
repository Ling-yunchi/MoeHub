import { createRouter, createWebHistory, RouteRecordRaw } from "vue-router";
import MainView from "@/views/main/MainView.vue";
import HomeView from "@/views/main/home/HomeView.vue";
import VideosView from "@/views/main/videos/VideosView.vue";
import IndexView from "@/views/main/index/IndexView.vue";
import AboutView from "@/views/main/about/AboutView.vue";

const routes: Array<RouteRecordRaw> = [
  {
    path: "/",
    name: "main",
    component: MainView,
    redirect: "/home",
    children: [
      {
        path: "/",
        name: "home",
        component: HomeView,
      },
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
];

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes,
});

export default router;
