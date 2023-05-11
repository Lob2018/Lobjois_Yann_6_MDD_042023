import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Observable, catchError } from 'rxjs';
import { PostComment } from 'src/app/core/models/post/postComment.interface';
import { PostSingle } from 'src/app/core/models/post/postSingle.interface';
import { ErrorHandlerService } from 'src/app/core/services/error-handler.service';
import { PostService } from 'src/app/core/services/post.service';
import { Location } from '@angular/common';
import { FormBuilder, Validators } from '@angular/forms';
import { PostCommentRequest } from 'src/app/core/models/post/postCommentRequest';
import { PostCommentResponse } from 'src/app/core/models/post/postCommentResponse';
import { MatSnackBar } from '@angular/material/snack-bar';
import { take } from 'rxjs/operators';

@Component({
  selector: 'app-single-post',
  templateUrl: './single-post.component.html',
  styleUrls: ['./single-post.component.css'],
})
export class SinglePostComponent implements OnInit {
  post$!: Observable<[PostSingle, PostComment[]]>;
  public onError = false;

  public form = this.fb.group({
    comment: ['', [Validators.required, Validators.minLength(1)]],
  });

  constructor(
    private route: ActivatedRoute,
    private postService: PostService,
    private errorHandler: ErrorHandlerService,
    private router: Router,
    private location: Location,
    private fb: FormBuilder,
    private _snackBar: MatSnackBar
  ) {}

  ngOnInit(): void {
    this.refresh();
  }

  showSnackBarError(msg: string, duration: number) {
    this._snackBar.open(msg, '', {
      duration: duration,
      panelClass: ['multiline-snackbar'],
    });
  }

  refresh() {
    const postId = +this.route.snapshot.params['id'];
    this.post$ = this.postService.getPostWithComments(postId).pipe(
      catchError((error) => {
        this.router.navigate(['/404']);
        return this.errorHandler.handleError(error);
      })
    );
  }

  public submit(): void {
    const postId = +this.route.snapshot.params['id'];
    const postCommentRequest = this.form.value as PostCommentRequest;
    // service with errorHandler
    this.postService
      .postComment(postId, postCommentRequest)
      .pipe(take(1))
      .subscribe({
        next: (response: PostCommentResponse) => {
          const msg = 'Your comment has been published';
          this.refresh();
          this.showSnackBarError(msg, 2000);
          return response;
        },
        error: (error) => this.errorHandler.handleError(error),
      });
  }

  /**
   * Return to previous page
   */
  goBack() {
    this.location.back();
  }
}
