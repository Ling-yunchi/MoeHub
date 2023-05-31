<template>
  <a-layout style="height: 100%">
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
</script>

<style scoped></style>
