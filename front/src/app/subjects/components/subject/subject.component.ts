import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { take } from 'rxjs';
import { SubjectCard } from 'src/app/core/models/subject/subjectCard.interface';
import { ErrorHandlerService } from 'src/app/core/services/error-handler.service';
import { SubjectService } from 'src/app/core/services/subject.service';

@Component({
  selector: 'app-subject',
  templateUrl: './subject.component.html',
  styleUrls: ['./subject.component.css'],
})
export class SubjectComponent implements OnInit {
  @Input() subject!: SubjectCard;
  @Output() subscribeBubbleUp = new EventEmitter<number>();

  constructor(
    private subjectService: SubjectService,
    private errorHandler: ErrorHandlerService
  ) {}

  ngOnInit(): void {}

  subscribeSubject() {
    this.subscribeBubbleUp.emit(this.subject.id);

    // const subscribing = this.subjectService.subscribeSubject(this.subject.id);
    // subscribing.pipe(take(1)).subscribe({
    //   next: (response) => {
    //     if (response.message == 'User subscribed !')
    //     this.newSubscribe.emit(response.message);
    //     return response;
    //   },
    //   error: (error) => {
    //     return this.errorHandler.handleError(error);
    //   },
    // });
  }
}
