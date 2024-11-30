import { Component, EventEmitter, Output } from '@angular/core';
import { User, UsersService } from '../users.service';
import { Router } from '@angular/router';
import { Role } from '../users.service';

@Component({
  selector: 'app-users',
  templateUrl: './users.component.html',
  styleUrls: ['./users.component.css']
})
export class UsersComponent {
  users: User[] = [];
  isLoading = true;
  errorMessage: string = '';
  role: Role[]= [];
  roles = ['EMPLOYEE', 'MANAGER'];  // Liste des rôles disponibles
  selectedRole: string = '';  // Rôle sélectionné
  firstName: string = '';  // Terme de recherche
  lastName: string = '';
  formattedUsers: any[] = [];  // Tableau des utilisateurs formatés
  searchQuery: string = '';

  constructor(    private userService: UsersService, private router: Router) {}
  @Output() addUserEvent = new EventEmitter<void>();
  onAddUser() {
    // Émettre l'événement pour indiquer qu'on veut afficher le formulaire
    this.addUserEvent.emit();
  }
  ngOnInit() {
      this.loadUsers();
  }

  // Méthode pour récupérer les utilisateurs
  loadUsers(): void {
    this.userService.getApprovedUsers().subscribe(
      (data) => {
        console.log(data)
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
      this.userService.searchApprovedByRole(this.selectedRole).subscribe(
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



  this.userService.searchApprovedUsers(firstName, lastName).subscribe(
    (data) => {

      this.users = data;
      this.formatUserDates();
    },
    (error) => {

      this.errorMessage = 'Erreur lors de la recherche des utilisateurs non approuvés.';
    }
  );
}

  formatUserDates() {
    this.formattedUsers = this.users.map(user => {
      const createdAtDate = new Date(user.createdAt);  // Convertir created_at en Date
      user.createdAt= createdAtDate.toLocaleString();  // Convertir en chaîne lisible
      return user;
    });
  }


}
