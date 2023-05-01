import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LandingPageComponent } from './landing-page/components/landing-page/landing-page.component';
import { LoginComponent } from './auth/components/login/login.component';
import { RegisterComponent } from './auth/components/register/register.component';
import { NotFoundComponent } from './not-found/components/not-found/not-found.component';

const routes: Routes = [
  // {
  //   path: 'facesnaps',
  //   loadChildren: () =>
  //     import('./face-snaps/face-snaps.module').then(
  //       (m) => m.FaceSnapsModule
  //     ),
  // }, // lazy loading pour routes commençant par facesnaps
  { path: 'auth/login', component: LoginComponent },
  { path: 'auth/register', component: RegisterComponent },
  { path: '', component: LandingPageComponent },
  { path: '404', component: NotFoundComponent },
  { path: '**', redirectTo: '404' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
