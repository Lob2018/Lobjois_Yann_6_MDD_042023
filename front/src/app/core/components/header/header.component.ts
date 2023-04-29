import {
  BreakpointObserver,
  BreakpointState,
  Breakpoints,
} from '@angular/cdk/layout';
import { Component, OnInit } from '@angular/core';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { Router } from '@angular/router';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css'],
})
export class HeaderComponent implements OnInit {
  isDesktop!: boolean;

  constructor(
    private router: Router,
    private breakpointObserver: BreakpointObserver,
    public dialog: MatDialog
  ) {}

  isShowDivIf = true;
  toggleDisplayDivIf() {
    this.isShowDivIf = !this.isShowDivIf;
  }

  openMenu(): void {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.height = '100%';
    dialogConfig.width = '184px';
    dialogConfig.position = { right: '0px', top: '0px' };
    dialogConfig.ariaLabel = 'Menu';
    dialogConfig.autoFocus = false;
    dialogConfig.panelClass = 'yl-dialog-container';
    this.dialog.open(HeaderMenuDialogContent, dialogConfig);
    //     enterAnimationDuration: string
    // exitAnimationDuration: string

    // dialogConfig.animation = {
    //   value: 'my-dialog-enter',
    //   duration: 300,
    // };
    //const dialogRef =...
    // dialogRef.afterClosed().subscribe((result: any) => {
    //   console.log(`Dialog result: ${result}`);
    // });
  }

  ngOnInit() {
    this.breakpointObserver
      .observe([Breakpoints.XSmall])
      .subscribe((result: BreakpointState) => {
        if (result.matches) {
          this.isDesktop = false;
        } else {
          this.isDesktop = true;
        }
      });
  }

  // onAddNewFaceSnap() {
  //   this.router.navigateByUrl('facesnaps/create');
  // }
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
