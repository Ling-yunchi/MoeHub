// this is all the types that are used in the views
export interface User {
  id: string;
  username: string;
  nickname: string;
  avatar: string;
}

export interface Video {
  id: string;
  cover: string;
  length: number;
  title: string;
  authorId: string;
  avatar: string;
  authorName: string;
  views: number;
}
