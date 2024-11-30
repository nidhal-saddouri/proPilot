import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { SignupComponent } from './signup/signup.component';
import {ForgotPasswordComponent} from "./forgot-password/forgot-password.component";
import {ResetPasswordComponent} from "./reset-password/reset-password.component";
import { UpdateUserComponent } from './update-user/update-user.component';
import { UserNotApprovedComponent } from './user-not-approved/user-not-approved.component';
import {DashboardadminComponent} from "./dashboardadmin/dashboardadmin.component"; // Remplacez par le chemin réel

const routes: Routes = [
  {path:"",component:LoginComponent},
  {path:"login",component:LoginComponent},
  {path:"signup",component :SignupComponent},
  { path: 'forgot-password', component: ForgotPasswordComponent },
  { path: 'reset-password', component: ResetPasswordComponent },
  { path: 'update-user/:id', component: UpdateUserComponent },
  { path: 'user-not-approved', component: UserNotApprovedComponent },
  { path: 'user-dashbord', component: DashboardadminComponent},


];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
