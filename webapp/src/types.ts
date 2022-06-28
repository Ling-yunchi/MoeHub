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

export interface CommentList {
  id: string;
  userId: string;
  username: string;
  avatar: string;
  time: string;
  content: string;
}

export interface BasePageResult<T> {
  success:boolean
  message:string
  data:T[]
  totalPages:number
  currentPage:number
  totalElement:number
}
