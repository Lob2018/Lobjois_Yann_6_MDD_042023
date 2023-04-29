import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
})
export class AppComponent {
  constructor(private router: Router) {}

  shouldShowHeader(): boolean {
    const currentUrl = this.router.url;
    // hide the header for the landing page
    if (currentUrl === '/') {
      return false;
    }
    return true;
  }
}
