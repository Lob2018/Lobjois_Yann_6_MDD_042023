import { Component, OnInit } from '@angular/core';
import { Observable, catchError, of } from 'rxjs';
import { SubjectCard } from 'src/app/core/models/subject/subjectCard.interface';
import { UserGetResponse } from 'src/app/core/models/user/userGetResponse.interface';
import { ErrorHandlerService } from 'src/app/core/services/error-handler.service';
import { SubjectService } from 'src/app/core/services/subject.service';
import { UserService } from 'src/app/core/services/user.service';

@Component({
  selector: 'app-user-page',
  templateUrl: './user-page.component.html',
  styleUrls: ['./user-page.component.css'],
})
export class UserPageComponent implements OnInit {
  subjects$!: Observable<SubjectCard[]>;
  user$!: Observable<UserGetResponse>;

  constructor(
    private userService: UserService,
    private subjectService: SubjectService,
    private errorHandler: ErrorHandlerService
  ) {}

  ngOnInit(): void {
    this.subjects$ = this.subjectService.getSubjects().pipe(
      catchError((error) => {
        return this.errorHandler.handleError(error);
      })
    );
    this.user$ = this.userService.getUser().pipe(
      catchError((error) => {
        return this.errorHandler.handleError(error);
      })
    );
  }
}
