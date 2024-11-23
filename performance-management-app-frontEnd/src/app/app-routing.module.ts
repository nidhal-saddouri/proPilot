import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { SignupComponent } from './signup/signup.component'; // Remplacez par le chemin réel
import { UserNotApprovedComponent } from './user-not-approved/user-not-approved.component'; // Remplacez par le chemin réel

const routes: Routes = [
  { path: '', redirectTo: 'user-not-approved', pathMatch: 'full' },
  { path: 'signup', component: SignupComponent }, // Composant pour la page de signup
  { path: 'user-not-approved', component: UserNotApprovedComponent },
  { path: '**', redirectTo: 'signup' }, // Redirige les routes inconnues vers signup
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
