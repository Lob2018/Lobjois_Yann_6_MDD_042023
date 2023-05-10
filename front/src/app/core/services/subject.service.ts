import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, map } from 'rxjs';
import { SubjectCard } from '../models/subject/subjectCard.interface';
import { MessageResponse } from '../models/message/messageResponse.interface';

@Injectable({
  providedIn: 'root',
})
export class SubjectService {
  private pathService = 'http://localhost:8080/api/';

  constructor(private httpClient: HttpClient) {}

  getSubjects(): Observable<SubjectCard[]> {
    return this.httpClient
      .get<{ subjects: SubjectCard[] }>(this.pathService + 'subject/user')
      .pipe(map((json) => json.subjects));
  }

  subscribeSubject(subjectId: number): Observable<MessageResponse> {
    return this.httpClient.post<MessageResponse>(
      this.pathService + `subject/${subjectId}/user`,
      null
    );
  }

  unsubscribeSubject(subjectId: number): Observable<MessageResponse> {
    return this.httpClient.delete<MessageResponse>(
      this.pathService + `subject/${subjectId}/user`
    );
  }
}
