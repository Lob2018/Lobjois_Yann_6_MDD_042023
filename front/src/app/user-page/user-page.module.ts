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
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatSnackBarModule } from '@angular/material/snack-bar';

const materialModules = [
  MatButtonModule,
  MatProgressSpinnerModule,
  MatDividerModule,
  MatSnackBarModule,
  MatCardModule,
  MatFormFieldModule,
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
