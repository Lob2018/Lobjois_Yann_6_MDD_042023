import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { PostCard } from 'src/app/core/models/post/postCard.interface';
import { PostService } from 'src/app/core/services/post.service';

@Component({
  selector: 'app-post-list',
  templateUrl: './post-list.component.html',
  styleUrls: ['./post-list.component.css'],
})
export class PostListComponent implements OnInit {
  posts$!: Observable<PostCard[]>;

  constructor(private postService: PostService) {}

  ngOnInit(): void {
    this.posts$ = this.postService.getMyPosts();
    console.log(this.posts$)
  }
}
