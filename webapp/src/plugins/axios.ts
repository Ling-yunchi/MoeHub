import axios, { AxiosRequestConfig } from "axios";
import { baseUrl } from "@/types";

const config: AxiosRequestConfig = {
  baseURL: baseUrl,
  timeout: 5000,
  headers: {
    Accept: "application/json",
    "Content-Type": "application/json",
  },
};

const _axios = axios.create(config);

export default _axios;
