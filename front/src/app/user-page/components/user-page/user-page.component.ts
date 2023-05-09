import {
  BreakpointObserver,
  BreakpointState,
  Breakpoints,
} from '@angular/cdk/layout';
import { Component, OnInit } from '@angular/core';
import { Validators, FormBuilder } from '@angular/forms';
import { Router } from '@angular/router';
import { Observable, catchError, map, of, take, tap } from 'rxjs';
import { RegisterRequest } from 'src/app/core/models/auth/registerRequest.interface';
import { TokenResponse } from 'src/app/core/models/auth/tokenResponse.interface';
import { SubjectCard } from 'src/app/core/models/subject/subjectCard.interface';
import { UserGetResponse } from 'src/app/core/models/user/userGetResponse.interface';
import { AuthService } from 'src/app/core/services/auth.service';
import { ErrorHandlerService } from 'src/app/core/services/error-handler.service';
import { LocalStorageService } from 'src/app/core/services/local-storage.service';
import { SubjectService } from 'src/app/core/services/subject.service';
import { UserService } from 'src/app/core/services/user.service';

@Component({
  selector: 'app-user-page',
  templateUrl: './user-page.component.html',
  styleUrls: ['./user-page.component.css'],
})
export class UserPageComponent implements OnInit {
  isDesktop!: boolean;
  subjects$!: Observable<SubjectCard[]>;
  user$!: Observable<UserGetResponse>;
  public hide = true;
  public onError = false;
  public usernamePattern = /^[\w]{1,254}$/;
  public passwordPattern =
    /^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[\!\@\#\&\(\)\-\[\{\}\]\:\;\'\,\?\/\*\~\$\^\+\=\<\>]).{8,254}$/;
  public form = this.fb.group({
    username: [
      '',
      [Validators.required, Validators.pattern(this.usernamePattern)],
    ],
    email: ['', [Validators.required, Validators.email]],
    password: [
      '',
      [Validators.required, Validators.pattern(this.passwordPattern)],
    ],
  });

  constructor(
    private userService: UserService,
    private subjectService: SubjectService,
    private errorHandler: ErrorHandlerService,
    private breakpointObserver: BreakpointObserver,
    private authService: AuthService,
    private fb: FormBuilder,
    private localStorageService: LocalStorageService,
    private router: Router
  ) {}

  public submit(): void {
    const registerRequest = this.form.value as RegisterRequest;
    // service with errorHandler
    this.authService.register(registerRequest).subscribe({
      next: (response: TokenResponse) => {
        this.localStorageService.setToken(response.token);
        // message
      },
      error: (error) => this.errorHandler.handleError(error),
    });
  }
  ngOnInit(): void {
    this.breakpointObserver
      .observe([Breakpoints.XSmall])
      .subscribe((result: BreakpointState) => {
        if (result.matches) {
          this.isDesktop = false;
        } else {
          this.isDesktop = true;
        }
      });
    this.refresh();
    this.user$ = this.userService.getUser().pipe(
      tap((response) => {
        this.form.setValue({
          username: response.username,
          email: response.email,
          password: '',
        });
      }),
      catchError((error) => {
        return this.errorHandler.handleError(error);
      })
    );
  }
  refresh() {
    this.subjects$ = this.subjectService.getSubjects().pipe(
      map((subjects) => subjects.filter((subject) => subject.subscribe)),
      catchError((error) => {
        return this.errorHandler.handleError(error);
      })
    );
  }
  disconnect() {
    this.localStorageService.removeToken();
    this.router.navigate(['/auth/login']);
  }
  onUnsubscribe(subscribeBubbleUp: number) {
    this.subjectService
      .unsubscribeSubject(subscribeBubbleUp)
      .pipe(take(1))
      .subscribe({
        next: (response) => {
          if (response.message == 'User no longer subscribe !') {
            this.refresh();
          }
          return response;
        },
        error: (error) => {
          return this.errorHandler.handleError(error);
        },
      });
  }
}
