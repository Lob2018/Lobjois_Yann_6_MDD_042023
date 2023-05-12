import {
  BreakpointObserver,
  BreakpointState,
  Breakpoints,
} from '@angular/cdk/layout';
import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
})
export class AppComponent {
  isDesktop!: boolean;
  private breakpointSubscription!: Subscription;

  constructor(
    private router: Router,
    private breakpointObserver: BreakpointObserver
  ) {}

  ngOnInit() {
    this.breakpointSubscription = this.breakpointObserver
      .observe([Breakpoints.XSmall])
      .subscribe((result: BreakpointState) => {
        if (result.matches) {
          this.isDesktop = false;
        } else {
          this.isDesktop = true;
        }
      });
  }

  shouldShowHeader(): boolean {
    const currentUrl = this.router.url;
    // hide the header for the landing page, and only mobile for login and register
    if (
      currentUrl === '/' ||
      (!this.isDesktop && currentUrl === '/auth/login') ||
      (!this.isDesktop && currentUrl === '/auth/register')
    ) {
      return false;
    }
    return true;
  }

  ngOnDestroy() {
    if (this.breakpointSubscription) {
      this.breakpointSubscription.unsubscribe();
    }
  }
}
