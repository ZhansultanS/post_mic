import {NgModule} from '@angular/core';
import {RouterModule, Routes} from "@angular/router";

import {HomeComponent} from "./pages/home/home.component";

import {CreatePostComponent} from "./components/create-post/create-post.component";
import {AuthorPostsComponent} from "./components/author-posts/author-posts.component";
import {ShowPostComponent} from "./components/show-post/show-post.component";

const routes: Routes = [
  {path: '', component: HomeComponent},
  {path: 'posts/create', component: CreatePostComponent},
  {path: 'authors/:author_id/posts', component: AuthorPostsComponent},
  {path: 'posts/:post_id', component: ShowPostComponent}
]

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
