<template>
  <a-layout>
    <a-layout-header>
      <header-view></header-view>
    </a-layout-header>
    <a-layout>
      <a-layout-content>
        <router-view></router-view>
      </a-layout-content>
    </a-layout>
  </a-layout>
</template>

<script lang="ts" setup>
import HeaderView from "@/views/HeaderView.vue";
import router from "@/router";

if (router.currentRoute.value.path === "/user") {
  router.push("/user/login");
}
</script>

<style scoped></style>
