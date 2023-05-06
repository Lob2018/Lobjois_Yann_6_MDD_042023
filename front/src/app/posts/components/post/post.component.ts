import { Component, Input, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { PostCard } from 'src/app/core/models/post/postCard.interface';
import { PostService } from 'src/app/core/services/post.service';

@Component({
  selector: 'app-post',
  templateUrl: './post.component.html',
  styleUrls: ['./post.component.css'],
})
export class PostComponent implements OnInit {
  @Input() post!: PostCard;

  constructor(private postService: PostService, private router: Router) {}

  ngOnInit(): void {}

  onViewPost() {
    this.router.navigateByUrl(`posts/${this.post.id}`);
  }
}
