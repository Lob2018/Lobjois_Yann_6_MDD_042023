import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SubjectComponent } from './components/subject/subject.component';
import { SubjectListComponent } from './components/subject-list/subject-list.component';
import { SubjectsRoutingModule } from './subjects-routing.module';
import { MatButtonModule } from '@angular/material/button';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatCardModule } from '@angular/material/card';

const materialModules = [
  MatButtonModule,
  MatProgressSpinnerModule,
  MatCardModule,
];

@NgModule({
  declarations: [SubjectComponent, SubjectListComponent],
  imports: [CommonModule, SubjectsRoutingModule, ...materialModules],
  exports: [SubjectComponent, SubjectListComponent],
})
export class SubjectsModule {}
