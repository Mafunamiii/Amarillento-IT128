import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Post } from '../../models/post.model';

import { UserStatusService } from '../../services/user-status.service';


@Component({
  selector: 'app-list-posts',
  templateUrl: './list-posts.component.html',
  styleUrls: ['./list-posts.component.css']
})
export class ListPostsComponent {
  posts?: Post[] = [];

  constructor(private http: HttpClient, private userStatusService: UserStatusService) {}

  ngOnInit(): void {
    this.initData();
    
  }

  initData(): void {
    this.http.get<Post[]>('https://localhost:7248/api/post')
    .subscribe({
      next: (data: Post[]) => {
        this.posts = data;
        console.log(this.posts);
      }
    })
  }
}
