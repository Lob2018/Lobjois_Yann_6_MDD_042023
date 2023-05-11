import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { UserPageComponent } from './components/user-page/user-page.component';
import { UserPageRoutingModule } from './user-page-routing.module';
import { SubjectsModule } from '../subjects/subjects.module';
import { MatButtonModule } from '@angular/material/button';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatDividerModule } from '@angular/material/divider';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';

const materialModules = [
  MatButtonModule,
  MatProgressSpinnerModule,
  MatDividerModule,
  MatIconModule,
  MatInputModule,
];

@NgModule({
  declarations: [UserPageComponent],
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    UserPageRoutingModule,
    SubjectsModule,
    ...materialModules,
  ],

  exports: [UserPageComponent],
})
export class UserPageModule {}
