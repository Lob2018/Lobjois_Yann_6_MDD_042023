import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { UserPageComponent } from './components/user-page/user-page.component';
import { UserPageRoutingModule } from './user-page-routing.module';
import { ReactiveFormsModule } from '@angular/forms';

@NgModule({
  declarations: [UserPageComponent],
  imports: [CommonModule, ReactiveFormsModule, UserPageRoutingModule],
  exports: [UserPageComponent],
})
export class UserPageModule {}
