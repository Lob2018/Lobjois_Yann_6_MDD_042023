import {
  BreakpointObserver,
  BreakpointState,
  Breakpoints,
} from '@angular/cdk/layout';
import { Component, OnInit } from '@angular/core';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { LocalStorageService } from '../../services/local-storage.service';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css'],
})
export class HeaderComponent implements OnInit {
  isDesktop!: boolean;
  private breakpointSubscription!: Subscription;

  constructor(
    private router: Router,
    private breakpointObserver: BreakpointObserver,
    public dialog: MatDialog,
    private localStorageService: LocalStorageService
  ) {}

  /**
   * Opens the dialog menu for mobiles
   */
  openMenu(): void {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.height = '100%';
    dialogConfig.width = '184px';
    dialogConfig.position = { right: '0px', top: '0px' };
    dialogConfig.ariaLabel = 'Menu';
    dialogConfig.autoFocus = false;
    dialogConfig.panelClass = 'yl-dialog-container';
    this.dialog.open(HeaderMenuDialogContent, dialogConfig);
  }

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

  /**
   * Hide the menu for routes login, register, and 404 if not logged
   * @returns true if the menu needs to be hidden
   */
  shouldShowMenu(): boolean {
    const currentUrl = this.router.url;
    if (
      currentUrl === '/auth/login' ||
      currentUrl === '/auth/register' ||
      (currentUrl === '/404' && !this.localStorageService.getToken())
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

@Component({
  selector: 'header-menu-dialog-content',
  templateUrl: 'header-menu-dialog-content.html',
  styleUrls: ['./header.component.css'],
})
export class HeaderMenuDialogContent {
  constructor(public dialog: MatDialog, private router: Router) {}

  closeDialog() {
    this.dialog.closeAll();
  }
}
