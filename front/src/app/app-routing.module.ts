import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LandingPageComponent } from './landing-page/components/landing-page/landing-page.component';
import { LoginComponent } from './auth/components/login/login.component';
import { RegisterComponent } from './auth/components/register/register.component';
import { NotFoundComponent } from './not-found/components/not-found/not-found.component';

const routes: Routes = [
  {
    path: 'posts',
    loadChildren: () =>
      import('./posts/posts.module').then((m) => m.PostsModule),
  }, // lazy loading for posts routes

  {
    path: 'subjects',
    loadChildren: () =>
      import('./subjects/subjects.module').then((m) => m.SubjectsModule),
  }, // lazy loading for subject route

  {
    path: 'user-page',
    loadChildren: () =>
      import('./user-page/user-page.module').then((m) => m.UserPageModule),
  }, // lazy loading for user-page route
  { path: 'auth/login', component: LoginComponent },
  { path: 'auth/register', component: RegisterComponent },
  { path: '', component: LandingPageComponent },
  { path: '404', component: NotFoundComponent },
  { path: '**', redirectTo: '404' },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
