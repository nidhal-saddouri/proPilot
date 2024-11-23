import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { SignupComponent } from './signup/signup.component';
import { HttpClientModule } from '@angular/common/http';
import { ReactiveFormsModule } from '@angular/forms';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';

import { DashboardadminComponent } from './dashboardadmin/dashboardadmin.component';
import { UsersComponent } from './users/users.component';
import { FormsModule } from '@angular/forms';

import { UserNotApprovedComponent } from './user-not-approved/user-not-approved.component';


@NgModule({
  declarations: [
    AppComponent,
    SignupComponent,

    DashboardadminComponent,
    UsersComponent,
    UserNotApprovedComponent,
    UserNotApprovedComponent

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
