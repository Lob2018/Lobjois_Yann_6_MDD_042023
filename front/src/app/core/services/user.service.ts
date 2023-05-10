import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { UserGetResponse } from '../models/user/userGetResponse.interface';
import { RegisterRequest } from '../models/auth/registerRequest.interface';
import { UserPutResponse } from '../models/user/userPutResponse.interface';

@Injectable({
  providedIn: 'root',
})
export class UserService {
  register(registerRequest: any) {
    throw new Error('Method not implemented.');
  }
  private pathService = 'http://localhost:8080/api/';

  constructor(private httpClient: HttpClient) {}

  getUser(): Observable<UserGetResponse> {
    return this.httpClient.get<UserGetResponse>(this.pathService + 'user');
  }

  public updateUser(registerRequest: RegisterRequest): Observable<UserPutResponse> {
    return this.httpClient.put<UserPutResponse>(
      `${this.pathService}user`,
      registerRequest
    );
  }


}
