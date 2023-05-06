import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AuthGuard } from '../core/guards/auth.guard';
import { PostListComponent } from '../posts/components/post-list/post-list.component';
import { SinglePostComponent } from '../posts/components/single-post/single-post.component';
import { NewPostComponent } from '../posts/components/new-post/new-post.component';

const routes: Routes = [
  { path: 'create', component: NewPostComponent, canActivate: [AuthGuard] },
  { path: ':id', component: SinglePostComponent, canActivate: [AuthGuard] },
  { path: '', component: PostListComponent, canActivate: [AuthGuard] },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class PostsRoutingModule {}