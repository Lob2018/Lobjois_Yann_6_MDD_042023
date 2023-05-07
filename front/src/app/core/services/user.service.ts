import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { UserGetResponse } from '../models/user/userGetResponse.interface';

@Injectable({
  providedIn: 'root',
})
export class UserService {
  register(registerRequest: any) {
    throw new Error('Method not implemented.');
  }
  private pathService = 'http://localhost:8080/api/';

  constructor(private http: HttpClient) {}

  getUser(): Observable<UserGetResponse> {
    return this.http.get<UserGetResponse>(this.pathService + 'user');
  }
}
