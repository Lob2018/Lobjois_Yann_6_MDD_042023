import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { SubjectCard } from 'src/app/core/models/subject/subjectCard.interface';

@Component({
  selector: 'app-subject',
  templateUrl: './subject.component.html',
  styleUrls: ['./subject.component.css'],
})
export class SubjectComponent implements OnInit {
  @Input() isDesktop!: boolean;
  @Input() subject!: SubjectCard;
  @Output() subscribeBubbleUp = new EventEmitter<number>();

  constructor() {}

  ngOnInit(): void {}

  subscribeSubject() {
    this.subscribeBubbleUp.emit(this.subject.id);
  }
}
