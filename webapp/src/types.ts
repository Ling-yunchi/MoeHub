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
  avatar: string;
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
  id:String;
  statement:String;
  count:number;
}