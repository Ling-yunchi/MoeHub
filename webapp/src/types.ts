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
}

export interface MyVideoDetail {
  id: string;
  description: string;
  coverUrl: string;
  length: number;
  title: string;
  createAt: string;
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

export const actionUrl = "/api/video/uploadTemp";
// export const actionUrl = "http://47.111.179.22/wr_web/moehub/api/video/uploadTemp";
export const avatarUploadUrl = "/api/video/upload";
// export const avatarUploadUrl = "http://47.111.179.22/wr_web/moehub/api/video/upload";
