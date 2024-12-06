import { Component } from '@angular/core';
import { SignupService } from '../signup.service';
import { Router } from '@angular/router';
import { User, UsersService } from '../users.service';

@Component({
  selector: 'app-user-not-approved',
  templateUrl: './user-not-approved.component.html',
  styleUrls: ['./user-not-approved.component.css']
})
export class UserNotApprovedComponent {
  selectedRole: string = '';  // Rôle sélectionné

  users: User[] = [];
  registrations: any[] = [];  // Liste des inscriptions
  isLoading = false;  // Indicateur de chargement
  errorMessage: string | null = null;  // Message d'erreur
  formattedUsers: any[] = [];  // Tableau des utilisateurs formatés
  firstName: string = '';
  lastName: string = '';
  searchQuery: string = '';
  roles = ['EMPLOYEE', 'MANAGER'];  // Liste des rôles disponibles

  constructor(private signupService: SignupService, private router: Router, private userService : UsersService) {}

  // Méthode pour charger les inscriptions en attente
  ngOnInit(): void {
    this.loadNotApprovedUsers();
  }
  getRole(user: any) {
    return user.authorities ? user.authorities[0].authority : 'No role'; // Vérifiez la structure de 'authorities'
  }
  // Charger les utilisateurs non approuvés
  loadNotApprovedUsers(): void {
    this.userService.getNotApprovedUsers().subscribe(
      (data) => {
        console.log(data);  // Ajouter ceci pour vérifier la structure des données
        this.users = data;
        this.formatUserDates(); 
        this.isLoading = false;
      },
      (error) => {
        this.errorMessage = 'Erreur lors du chargement des utilisateurs approuvés.';
        this.isLoading = false;
      }
    );
  }

    // Fonction pour effectuer la recherche par rôle
    searchByRole() {
      if (this.selectedRole) {
        this.userService.searchNotApprovedByRole(this.selectedRole).subscribe(
          (data: any) => {
            this.users = data;
            this.formatUserDates(); 
          },
          (error) => {
            console.error('Error retrieving users by role', error);
          }
        );
      } else {
        console.log('Veuillez sélectionner un rôle');
      }
    }



  OnSearch(): void {
    const searchTerms = this.searchQuery.trim().split(' '); 
  
  const firstName = searchTerms[0] || ''; 
  const lastName = searchTerms[1] || '';  
  
 

  this.userService.searchNotApprovedUsers(firstName, lastName).subscribe(
    (data) => {
    
      this.users = data;
      this.formatUserDates(); 
    },
    (error) => {
   
      this.errorMessage = 'Erreur lors de la recherche des utilisateurs non approuvés.';
    }
  );
}

   // Approuver une inscription
   approveRegistration(userId: number): void {
    const confirmation = window.confirm("Are you sure that you want to approve this user?");
    this.isLoading = true;
    if (confirmation) {
      this.signupService.approveRegistration(userId).subscribe(
        (response: string) => {  
          
          console.log('Inscription approuvée:', response); 
          this.loadNotApprovedUsers();  
        alert('User approved successfully.');
        this.isLoading = false;
        },
        (error) => {
          console.error('Erreur lors de l\'approbation:', error);
          this.errorMessage = 'Erreur lors de l\'approbation de l\'inscription.';
          this.isLoading = false;
        }
      );
    } else {
      console.log('Approbation annulée');
    }
  }
  

  // Rejeter une inscription
  rejectRegistration(id: number): void {
    this.signupService.rejectRegistration(id).subscribe(
      (response) => {
        console.log('Inscription rejetée:', response);
        this.loadNotApprovedUsers();  // Recharger les inscriptions après rejet
      },
      (error) => {
        console.error('Erreur lors du rejet:', error);
        this.errorMessage = 'Erreur lors du rejet de l\'inscription.';
      }
    );
  }
  formatUserDates() {
    this.formattedUsers = this.users.map(user => {
      // Convertir createdAt en un objet Date
      const createdAtDate = new Date(user.createdAt);  // Assurez-vous que user.createdAt est bien une chaîne ISO
      user.createdAt = createdAtDate.toLocaleString();  // Convertir en une chaîne lisible
      return user;
    });
  }


}
