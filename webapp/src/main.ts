import { createApp } from "vue";
import App from "./App.vue";
import router from "./router";
import "@/assets/css/global.css";
import "@vime/core/themes/default.css";
import "@arco-design/web-vue/dist/arco.css";

createApp(App).use(router).mount("#app");
