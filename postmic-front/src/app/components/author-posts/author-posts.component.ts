import {Component, OnDestroy, OnInit} from '@angular/core';
import {BehaviorSubject, Observable} from "rxjs";
import {webSocket, WebSocketSubject} from 'rxjs/webSocket';

import {PostsService} from "../../services/posts.service";
import {PostPage} from "../../models/post";
import {NotificationService} from "../../services/notification.service";

@Component({
  selector: 'app-author-posts',
  templateUrl: './author-posts.component.html',
  styleUrls: ['./author-posts.component.css']
})
export class AuthorPostsComponent implements OnInit, OnDestroy {

  postStatus = new BehaviorSubject('waiting')
  postPage$: Observable<PostPage>
  private webSocket: WebSocketSubject<any>

  constructor(
    private postService: PostsService,
    private notificationService: NotificationService
  ) {
  }

  ngOnInit(): void {
    this.webSocket = webSocket('ws://localhost:8080/messaging?user_id=5')
    this.webSocket.subscribe(msg => {
      this.notificationService.show(msg.message)
      this.postPage$ = this.postService.getAll({authorId: 5, status: this.postStatus.getValue()})
    })
    this.postStatus.subscribe(status => this.postPage$ = this.postService.getAll({authorId: 5, status}))
  }

  ngOnDestroy(): void {
    this.webSocket.complete()
  }


}
