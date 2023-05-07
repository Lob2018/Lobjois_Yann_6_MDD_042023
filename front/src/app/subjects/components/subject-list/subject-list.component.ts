import { Component, OnInit } from '@angular/core';
import { Observable, catchError } from 'rxjs';
import { SubjectCard } from 'src/app/core/models/subject/subjectCard.interface';
import { ErrorHandlerService } from 'src/app/core/services/error-handler.service';
import { SubjectService } from 'src/app/core/services/subject.service';

@Component({
  selector: 'app-subject-list',
  templateUrl: './subject-list.component.html',
  styleUrls: ['./subject-list.component.css'],
})
export class SubjectListComponent implements OnInit {
  subjects$!: Observable<SubjectCard[]>;

  constructor(
    private subjectService: SubjectService,
    private errorHandler: ErrorHandlerService
  ) {}

  ngOnInit(): void {
    this.subjects$ = this.subjectService.getSubjects().pipe(
      catchError((error) => {
        return this.errorHandler.handleError(error);
      })
    );
  }
}
