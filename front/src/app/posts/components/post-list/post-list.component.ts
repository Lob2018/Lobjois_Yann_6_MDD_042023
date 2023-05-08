import { Component, OnInit } from '@angular/core';
import { Observable, catchError } from 'rxjs';
import { PostCard } from 'src/app/core/models/post/postCard.interface';
import { ErrorHandlerService } from 'src/app/core/services/error-handler.service';
import { PostService } from 'src/app/core/services/post.service';
import { map } from 'rxjs/operators';
import { Router } from '@angular/router';

@Component({
  selector: 'app-post-list',
  templateUrl: './post-list.component.html',
  styleUrls: ['./post-list.component.css'],
})
export class PostListComponent implements OnInit {
  posts$!: Observable<PostCard[]>;
  sortDirection: 'ascending' | 'descending' = 'ascending';

  constructor(
    private postService: PostService,
    private errorHandler: ErrorHandlerService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.posts$ = this.postService.getMyPosts().pipe(
      catchError((error) => {
        return this.errorHandler.handleError(error);
      })
    );
  }

  orderByDAte() {
    this.sortDirection =
      this.sortDirection === 'ascending' ? 'descending' : 'ascending';
    this.posts$ = this.posts$.pipe(map((postCard) => postCard.reverse()));
  }

  create() {
    this.router.navigateByUrl(`posts/create`);
  }
}
