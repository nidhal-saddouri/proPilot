import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { SignupComponent } from './signup/signup.component';
import { HttpClientModule } from '@angular/common/http';
import { ReactiveFormsModule } from '@angular/forms';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { UpdateUserComponent } from './update-user/update-user.component';

import { DashboardadminComponent } from './dashboardadmin/dashboardadmin.component';
import { UsersComponent } from './users/users.component';
import { FormsModule } from '@angular/forms';

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
    UserNotApprovedComponent,
    ProfilComponent,
    AddUserComponent


  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    ReactiveFormsModule,
        FormsModule,

  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
