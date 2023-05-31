import axios, { AxiosRequestConfig } from "axios";
import { baseUrl } from "@/types";
import { Message } from "@arco-design/web-vue";

const config: AxiosRequestConfig = {
  baseURL: baseUrl,
  timeout: 10000,
  headers: {
    Accept: "application/json",
    "Content-Type": "application/json",
  },
};

const _axios = axios.create(config);

_axios.interceptors.response.use(
  (res) => res,
  (err) => {
    if (err.message.includes("timeout")) {
      Message.error("请求超时，请稍后重试");
    }
    console.log("catch", err);
  }
);

export default _axios;
