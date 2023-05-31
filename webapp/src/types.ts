import { InjectionKey, Ref } from "vue";

// this is all the types that are used in the views
export interface BaseResult<T> {
  success: boolean;
  message: string;
  data: T;
}

export interface BasePageResult<T> {
  success: boolean;
  message: string;
  data: T[];
  totalPages: number;
  currentPage: number;
  totalElements: number;
}

export interface User {
  id: string;
  username: string;
  nickname: string;
  avatar: string;
  email: string;
  sex: string;
}

export interface VideoList {
  id: string;
  coverUrl: string;
  length: number;
  title: string;
  authorId: string;
  avatarUrl: string;
  authorName: string;
  views: number;
  createAt: string;
  category: string;
}

export interface VideoInfo {
  id: string;
  title: string;
  videoUrl: string;
  coverUrl: string;
  description: string;
  length: number;
  authorId: string;
  authorName: string;
  authorAvatar: string;
  createAt: string;
  views: number;
  likes: number;
  favorites: number;
  isFavorite: boolean;
  isLiked: boolean;
  category: string;
}

export interface MyVideoDetail {
  id: string;
  description: string;
  coverUrl: string;
  length: number;
  title: string;
  createAt: string;
  category: string;
}

export interface CommentList {
  id: string;
  userId: string;
  nickname: string;
  avatar: string;
  createAt: string;
  content: string;
}

export interface BasePageResult<T> {
  success: boolean;
  message: string;
  data: T[];
  totalPages: number;
  currentPage: number;
  totalElement: number;
}

export interface Search {
  id: string;
  statement: string;
  count: number;
}

export const userKey = Symbol("user") as InjectionKey<
  Readonly<Ref<User | null>>
>;
export const updateUserKey = Symbol("updateUser") as InjectionKey<() => void>;

// export const actionUrl = "/api/video/uploadTemp";
// export const avatarUploadUrl = "/api/user/avatar";
export const baseUrl = "http://10.241.232.207:4000";
export const actionUrl = baseUrl + "/api/video/uploadTemp";
export const avatarUploadUrl = baseUrl + "/api/user/avatar";
