export interface PostPage {
  posts: Post[]
  currentPage: number
  totalPages: number
  totalItems: number
}

export interface Post {
  id?: number
  authorId: number
  title: string
  content?: string
  contentInCard?: string
  createdAt?: string
  modifiedAt?: string
}

export interface PostFilter {
  authorId: number
  status: string
}
