// this is all the types that are used in the views
export interface User {
  id: string;
  username: string;
  nickname: string;
  avatar: string;
}

export interface VideoList {
  id: string;
  cover: string;
  length: number;
  title: string;
  authorId: string;
  avatar: string;
  authorName: string;
  views: number;
  time: string;
}

export interface CommentList {
  id: string;
  userId: string;
  username: string;
  avatar: string;
  time: string;
  content: string;
}
