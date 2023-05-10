import { Injectable } from '@angular/core';
import { Observable, map } from 'rxjs';
import { PostCard } from '../models/post/postCard.interface';
import { HttpClient } from '@angular/common/http';
import { PostComment } from '../models/post/postComment.interface';
import { PostSingle } from '../models/post/postSingle.interface';

@Injectable({
  providedIn: 'root',
})
export class PostService {
  private pathService = 'http://localhost:8080/api/';

  constructor(private httpClient: HttpClient) {}

  getMyPosts(): Observable<PostCard[]> {
    return this.httpClient
      .get<{ posts: PostCard[] }>(this.pathService + 'subject/user/posts')
      .pipe(map((json) => json.posts));
  }

  getPostWithComments(postId: number): Observable<[PostSingle, PostComment[]]> {
    return this.httpClient
      .get<{ comments: PostComment[]; post: PostSingle }>(
        this.pathService + `post/${postId}/comments`
      )
      .pipe(map((response) => [response.post, response.comments]));
  }
}
