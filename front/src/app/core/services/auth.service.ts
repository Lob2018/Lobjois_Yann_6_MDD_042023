import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { RegisterRequest } from '../models/auth/registerRequest.interface';
import { LoginRequest } from '../models/auth/loginRequest.interface';
import { TokenResponse } from '../models/auth/token.interface';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private pathService = 'http://localhost:8080/api/auth';

  constructor(private httpClient: HttpClient) {}

  public register(registerRequest: RegisterRequest): Observable<TokenResponse> {
    return this.httpClient.post<TokenResponse>(
      `${this.pathService}/register`,
      registerRequest
    );
  }

  public login(loginRequest: LoginRequest): Observable<TokenResponse> {
    return this.httpClient.post<TokenResponse>(
      `${this.pathService}/login`,
      loginRequest
    );
  }
}
