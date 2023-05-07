import { Component, OnInit } from '@angular/core';
import { Observable, catchError } from 'rxjs';
import { PostCard } from 'src/app/core/models/post/postCard.interface';
import { ErrorHandlerService } from 'src/app/core/services/error-handler.service';
import { PostService } from 'src/app/core/services/post.service';

@Component({
  selector: 'app-post-list',
  templateUrl: './post-list.component.html',
  styleUrls: ['./post-list.component.css'],
})
export class PostListComponent implements OnInit {
  posts$!: Observable<PostCard[]>;

  constructor(
    private postService: PostService,
    private errorHandler: ErrorHandlerService
  ) {}

  ngOnInit(): void {
    this.posts$ = this.postService.getMyPosts().pipe(
      catchError((error) => {
        return this.errorHandler.handleError(error);
      })
    );
  }
}
