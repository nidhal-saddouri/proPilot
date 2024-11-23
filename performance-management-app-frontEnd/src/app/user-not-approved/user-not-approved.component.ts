import { Component } from '@angular/core';
import { SignupService } from '../signup.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-user-not-approved',
  templateUrl: './user-not-approved.component.html',
  styleUrls: ['./user-not-approved.component.css']
})
export class UserNotApprovedComponent {

  registrations: any[] = [];  // Liste des inscriptions
  isLoading = false;  // Indicateur de chargement
  errorMessage: string | null = null;  // Message d'erreur

  constructor(private signupService: SignupService, private router: Router) {
    this.loadRegistrations();  // Charger les inscriptions lors de l'initialisation
  }

  // Méthode pour charger les inscriptions en attente
  loadRegistrations(): void {
    this.isLoading = true;
    this.signupService.getPendingRegistrations().subscribe(
      (data) => {
        this.registrations = data;
        this.isLoading = false;
      },
      (error) => {
        this.errorMessage = 'Erreur lors du chargement des inscriptions.';
        this.isLoading = false;
      }
    );
  }

   // Approuver une inscription
   approveRegistration(id: number): void {
    this.signupService.approveRegistration(id).subscribe(
      (response) => {
        console.log('Inscription approuvée:', response);
        this.loadRegistrations();  // Recharger les inscriptions après approbation
      },
      (error) => {
        console.error('Erreur lors de l\'approbation:', error);
        this.errorMessage = 'Erreur lors de l\'approbation de l\'inscription.';
      }
    );
  }

  // Rejeter une inscription
  rejectRegistration(id: number): void {
    this.signupService.rejectRegistration(id).subscribe(
      (response) => {
        console.log('Inscription rejetée:', response);
        this.loadRegistrations();  // Recharger les inscriptions après rejet
      },
      (error) => {
        console.error('Erreur lors du rejet:', error);
        this.errorMessage = 'Erreur lors du rejet de l\'inscription.';
      }
    );
  }

}
