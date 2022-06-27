<template>
  <router-view></router-view>
</template>

<script lang="ts" setup>
import { onActivated, onMounted, provide, readonly, ref } from "vue";
import axios from "@/plugins/axios";
import { User } from "@/types";

// provide user data to all components
const user = ref<User | null>(null);
const updateUser = (u: User | null) => {
  console.log("updateUser", u);
  user.value = u;
};
onMounted(() => {
  axios
    .get("/api/user/self")
    .then((res) => {
      if (res.data.success === true) {
        user.value = res.data.data as User;
      }
    })
    .catch(() => {
      // do nothing
    });
});
// user.value = {
//   avatar: "/avatar.jpg",
//   id: "1",
//   nickname: "龗云螭",
//   username: "Ling_yunchi",
// };
provide("user", readonly(user));
provide("updateUser", updateUser);
</script>
