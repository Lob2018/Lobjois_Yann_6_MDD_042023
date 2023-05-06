import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SubjectComponent } from './components/subject/subject.component';
import { SubjectListComponent } from './components/subject-list/subject-list.component';
import { SubjectsRoutingModule } from './subjects-routing.module';

@NgModule({
  declarations: [SubjectComponent, SubjectListComponent],
  imports: [CommonModule, SubjectsRoutingModule],
  exports: [SubjectComponent, SubjectListComponent],
})
export class SubjectsModule {}
