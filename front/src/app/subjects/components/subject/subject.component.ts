import { Component, Input, OnInit } from '@angular/core';
import { SubjectCard } from 'src/app/core/models/subject/subjectCard.interface';

@Component({
  selector: 'app-subject',
  templateUrl: './subject.component.html',
  styleUrls: ['./subject.component.css'],
})
export class SubjectComponent implements OnInit {
  @Input() subject!: SubjectCard;

  constructor() {}

  ngOnInit(): void {}
}
