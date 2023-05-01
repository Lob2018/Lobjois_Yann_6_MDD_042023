import { Component } from '@angular/core';
import { Validators, FormBuilder } from '@angular/forms';
import { Router } from '@angular/router';
import { LoginRequest } from 'src/app/core/models/auth/loginRequest.interface';
import { TokenResponse } from 'src/app/core/models/auth/token.interface';
import { AuthService } from 'src/app/core/services/auth.service';
import { ErrorHandlerService } from 'src/app/core/services/error-handler.service';
import { LocalStorageService } from 'src/app/core/services/local-storage.service';
import { Location } from '@angular/common';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
})
export class LoginComponent {
  public hide = true;
  public onError = false;
  public passwordPattern =
    /^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[\!\@\#\&\(\)\-\[\{\}\]\:\;\'\,\?\/\*\~\$\^\+\=\<\>]).{8,254}$/;
  public form = this.fb.group({
    email: ['', [Validators.required, Validators.email]],
    password: [
      '',
      [Validators.required, Validators.pattern(this.passwordPattern)],
    ],
  });

  constructor(
    private authService: AuthService,
    private errorHandler: ErrorHandlerService,
    private fb: FormBuilder,
    private router: Router,
    private localStorageService: LocalStorageService,
    private location: Location
  ) {}

  public submit(): void {
    const loginRequest = this.form.value as LoginRequest;
    this.authService.login(loginRequest).subscribe({
      next: (response: TokenResponse) => {
        this.localStorageService.setToken(response.token);
        this.router.navigate(['/posts']);
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
