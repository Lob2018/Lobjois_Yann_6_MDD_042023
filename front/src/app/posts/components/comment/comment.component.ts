import { Component, Input, OnInit } from '@angular/core';
import { PostComment } from 'src/app/core/models/post/postComment.interface';

@Component({
  selector: 'app-comment',
  templateUrl: './comment.component.html',
  styleUrls: ['./comment.component.css'],
})
export class CommentComponent implements OnInit {
  @Input() comment!: PostComment;

  constructor() {}

  ngOnInit(): void {}
}
