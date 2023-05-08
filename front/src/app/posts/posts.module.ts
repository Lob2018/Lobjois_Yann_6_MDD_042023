import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PostComponent } from './components/post/post.component';
import { PostListComponent } from './components/post-list/post-list.component';
import { NewPostComponent } from './components/new-post/new-post.component';
import { SinglePostComponent } from './components/single-post/single-post.component';
import { ReactiveFormsModule } from '@angular/forms';
import { PostsRoutingModule } from './posts-routing.module';
import { MatButtonModule } from '@angular/material/button';
import {MatProgressSpinnerModule} from '@angular/material/progress-spinner';
import {MatCardModule} from '@angular/material/card';

const materialModules = [MatButtonModule, MatProgressSpinnerModule,MatCardModule];

@NgModule({
  declarations: [
    PostComponent,
    PostListComponent,
    NewPostComponent,
    SinglePostComponent,
  ],
  imports: [
    CommonModule,
    ReactiveFormsModule,
    PostsRoutingModule,
    ...materialModules,
  ],
  exports: [
    PostComponent,
    PostListComponent,
    NewPostComponent,
    SinglePostComponent,
  ],
})
export class PostsModule {}
