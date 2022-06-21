<template>
  <router-view></router-view>
</template>

<script lang="ts" setup>
import { provide, readonly, ref } from "vue";
import axios from "@/plugins/axios";
import { User } from "@/types";

// provide user data to all components
const user = ref<User | null>(null);
const updateUser = (u: User) => {
  user.value = u;
};
axios
  .get("/user/self")
  .then((res) => {
    if (res.data.success === true) {
      user.value = res.data.data as User;
    }
  })
  .catch(() => {
    // do nothing
  });
provide("user", readonly(user));
provide("updateUser", updateUser);
</script>
