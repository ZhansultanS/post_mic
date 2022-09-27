import {Component, OnInit} from '@angular/core';
import {FormBuilder, Validators} from '@angular/forms';
import {Router} from "@angular/router"

import {NotificationService} from "../../services/notification.service";
import {PostsService} from "../../services/posts.service";
import {Post} from "../../models/post";

@Component({
  selector: 'app-create-post',
  templateUrl: './create-post.component.html',
  styleUrls: ['./create-post.component.css']
})
export class CreatePostComponent implements OnInit {

  ngOnInit(): void {
  }

  constructor(
    private formBuilder: FormBuilder,
    private router: Router,
    private notificationService: NotificationService,
    private postService: PostsService
  ) {
  }

  postForm = this.formBuilder.group({
    title: ['', [Validators.required]],
    content: ['', [Validators.required]],
  });

  create() {
    if (!this.postForm.valid) return
    const post: Post = {
      title: this.postForm.value.title as string,
      content: this.postForm.value.content as string,
      authorId: 5
    }
    this.postService.create(post)
      .subscribe({
        next: createdPost => this.router
          .navigate([`authors/${5}/posts`])
          .then(() => this.notificationService.show(`Post with id=${createdPost.id} successfully created`)),
        error: e => this.notificationService.show(`Error! ${e.error.message}`)
      })
  }

}
