import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { UserPageComponent } from './components/user-page/user-page.component';
import { UserPageRoutingModule } from './user-page-routing.module';
import { ReactiveFormsModule } from '@angular/forms';
import { SubjectsModule } from '../subjects/subjects.module';
import { MatButtonModule } from '@angular/material/button';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';

const materialModules = [MatButtonModule, MatProgressSpinnerModule];

@NgModule({
  declarations: [UserPageComponent],
  imports: [
    CommonModule,
    ReactiveFormsModule,
    UserPageRoutingModule,
    SubjectsModule,
    ...materialModules,
  ],

  exports: [UserPageComponent],
})
export class UserPageModule {}
