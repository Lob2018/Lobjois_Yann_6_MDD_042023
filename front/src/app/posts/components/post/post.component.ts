import { Component, Input, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { PostCard } from 'src/app/core/models/post/postCard.interface';

@Component({
  selector: 'app-post',
  templateUrl: './post.component.html',
  styleUrls: ['./post.component.css'],
})
export class PostComponent implements OnInit {
  @Input() post!: PostCard;

  constructor(private router: Router) {}

  ngOnInit(): void {}

  onViewPost() {
    this.router.navigateByUrl(`posts/${this.post.id}`);
  }
}
