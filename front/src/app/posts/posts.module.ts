import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PostComponent } from './components/post/post.component';
import { PostListComponent } from './components/post-list/post-list.component';
import { NewPostComponent } from './components/new-post/new-post.component';
import { SinglePostComponent } from './components/single-post/single-post.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { PostsRoutingModule } from './posts-routing.module';
import { MatButtonModule } from '@angular/material/button';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatCardModule } from '@angular/material/card';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { MatDividerModule } from '@angular/material/divider';
import { CommentComponent } from './components/comment/comment.component';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';

const materialModules = [
  MatButtonModule,
  MatProgressSpinnerModule,
  MatCardModule,
  MatFormFieldModule,
  MatInputModule,
  MatIconModule,
  MatSnackBarModule,
  MatDividerModule,
  MatSelectModule,
];

@NgModule({
  declarations: [
    PostComponent,
    PostListComponent,
    NewPostComponent,
    SinglePostComponent,
    CommentComponent,
  ],
  imports: [
    CommonModule,
    ReactiveFormsModule,
    PostsRoutingModule,
    FormsModule,
    ...materialModules,
  ],
  exports: [
    PostComponent,
    PostListComponent,
    NewPostComponent,
    SinglePostComponent,
    CommentComponent,
  ],
})
export class PostsModule {}
