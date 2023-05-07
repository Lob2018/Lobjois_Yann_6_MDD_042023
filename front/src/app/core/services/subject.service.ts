import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, map } from 'rxjs';
import { SubjectCard } from '../models/subject/subjectCard.interface';

@Injectable({
  providedIn: 'root',
})
export class SubjectService {
  private pathService = 'http://localhost:8080/api/';

  constructor(private http: HttpClient) {}

  getSubjects(): Observable<SubjectCard[]> {
    return this.http
      .get<{ subjects: SubjectCard[] }>(this.pathService + 'subject/user')
      .pipe(map((json) => json.subjects));
  }
}
