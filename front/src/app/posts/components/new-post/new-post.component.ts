import { Component, OnInit } from '@angular/core';
import { Validators, FormBuilder } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { Observable, catchError, take } from 'rxjs';
import { PostSingle } from 'src/app/core/models/post/postSingle.interface';
import { PostSingleRequest } from 'src/app/core/models/post/postSingleRequest.interface';
import { SubjectsResponse } from 'src/app/core/models/subject/subjectsResponse.interface';
import { ErrorHandlerService } from 'src/app/core/services/error-handler.service';
import { PostService } from 'src/app/core/services/post.service';
import { SubjectService } from 'src/app/core/services/subject.service';
import { Location } from '@angular/common';

@Component({
  selector: 'app-new-post',
  templateUrl: './new-post.component.html',
  styleUrls: ['./new-post.component.css'],
})
export class NewPostComponent implements OnInit {
  subjects$!: Observable<SubjectsResponse[]>;
  public onError = false;
  // \s matches any whitespace character (equivalent to [\r\n\t\f\v ])
  // \S matches any non-whitespace character (equivalent to [^\r\n\t\f\v ])
  titlePattern = /^(?!\s*$)[\s\S]{1,254}$/;
  contentPattern = /^(?!\s*$)[\s\S]{1,1999}$/;
  subject_idPattern = /^[1-9][0-9]*$/;

  public form = this.fb.group({
    subject_id: [
      0,
      [Validators.required, Validators.pattern(this.subject_idPattern)],
    ],
    title: ['', [Validators.required, Validators.pattern(this.titlePattern)]],
    content: [
      '',
      [Validators.required, Validators.pattern(this.contentPattern)],
    ],
  });

  constructor(
    private subjectService: SubjectService,
    private postService: PostService,
    private errorHandler: ErrorHandlerService,
    private fb: FormBuilder,
    private router: Router,
    private _snackBar: MatSnackBar,
    private location: Location
  ) {}

  showSnackBarError(msg: string, duration: number) {
    this._snackBar.open(msg, '', {
      duration: duration,
      panelClass: ['multiline-snackbar'],
    });
  }

  public submit(): void {
    const postRequest = this.form.value as PostSingleRequest;
    this.postService
      .postPost(postRequest)
      .pipe(take(1))
      .subscribe({
        next: (response: PostSingle) => {
          const msg = 'Your article ' + response.title + ' is created!';
          this.showSnackBarError(msg, 2000);
          this.router.navigate(['posts']);
        },
        error: (error) => this.errorHandler.handleError(error),
      });
  }

  ngOnInit(): void {
    this.subjects$ = this.subjectService.getSubjects().pipe(
      take(1),
      catchError((error) => {
        return this.errorHandler.handleError(error);
      })
    );
  }

  /**
   * Return to previous page
   */
  goBack() {
    this.location.back();
  }
}
