import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { AppRoutingModule } from './app-routing.module';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

import { AppComponent } from './app.component';
import { LoginComponent } from './login/login.component';
import { ForgotPasswordComponent } from './forgot-password/forgot-password.component';
import { ResetPasswordComponent } from './reset-password/reset-password.component';

import { BrowserModule } from '@angular/platform-browser';
import { SignupComponent } from './signup/signup.component';
import { HttpClientModule } from '@angular/common/http';
import { ReactiveFormsModule } from '@angular/forms';

import { UpdateUserComponent } from './update-user/update-user.component';

import { DashboardadminComponent } from './dashboardadmin/dashboardadmin.component';
import { UsersComponent } from './users/users.component';

import { UserNotApprovedComponent } from './user-not-approved/user-not-approved.component';
import { ProfilComponent } from './profil/profil.component';
import { AddUserComponent } from './add-user/add-user.component';



@NgModule({
  declarations: [
    AppComponent,
    SignupComponent,
    UpdateUserComponent,
    DashboardadminComponent,
    UsersComponent,
    UserNotApprovedComponent,
    ProfilComponent,
    AddUserComponent,
    LoginComponent,
    ForgotPasswordComponent,
    ResetPasswordComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    ReactiveFormsModule,
        FormsModule,
        BrowserAnimationsModule,

  ],

  providers: [],
  bootstrap: [AppComponent]
})

export class AppModule { }
