import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Observable, catchError } from 'rxjs';
import { PostComment } from 'src/app/core/models/post/postComment.interface';
import { PostSingle } from 'src/app/core/models/post/postSingle.interface';
import { ErrorHandlerService } from 'src/app/core/services/error-handler.service';
import { PostService } from 'src/app/core/services/post.service';

@Component({
  selector: 'app-single-post',
  templateUrl: './single-post.component.html',
  styleUrls: ['./single-post.component.css'],
})
export class SinglePostComponent implements OnInit {
  post$!: Observable<[PostSingle, PostComment[]]>;
  constructor(
    private route: ActivatedRoute,
    private postService: PostService,
    private errorHandler: ErrorHandlerService
  ) {}

  ngOnInit(): void {
    const postId = +this.route.snapshot.params['id'];
    this.post$ = this.postService.getPostWithComments(postId).pipe(
      catchError((error) => {
        return this.errorHandler.handleError(error);
      })
    );
  }
}
