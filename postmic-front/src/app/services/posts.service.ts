import {Injectable} from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";
import {Observable} from "rxjs";
import {Post, PostFilter, PostPage} from "../models/post";

@Injectable({
  providedIn: 'root'
})
export class PostsService {

  constructor(private httpClient: HttpClient) {
  }

  getAll(filter: PostFilter): Observable<PostPage> {
    return this.httpClient.get<PostPage>(`http://localhost:8080/posts`, {
      params: new HttpParams()
        .append('authorId', filter.authorId)
        .append('status', filter.status)
    })
  }

  create(post: Post): Observable<Post> {
    return this.httpClient.post<Post>('http://localhost:8080/posts', post)
  }
}
