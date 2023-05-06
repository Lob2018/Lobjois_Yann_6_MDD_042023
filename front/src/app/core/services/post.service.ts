import { Injectable } from '@angular/core';
import { Observable, map } from 'rxjs';
import { PostCard } from '../models/post/postCard.interface';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root',
})
export class PostService {
  private pathService = 'http://localhost:8080/api/';

  constructor(private http: HttpClient) {}

  getMyPosts(): Observable<PostCard[]> {
    return this.http
      .get<{ posts: PostCard[] }>(this.pathService + 'subject/user/posts')
      .pipe(map((json) => json.posts));
  }
}
