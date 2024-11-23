import { Component } from '@angular/core';
import { User, UsersService } from '../users.service';
import { Router } from '@angular/router';
import { Role } from '../users.service';
import { FilterService } from '../filter.service';

@Component({
  selector: 'app-users',
  templateUrl: './users.component.html',
  styleUrls: ['./users.component.css']
})
export class UsersComponent {
  users: User[] = [];
  role: Role[]= [];
  roles = ['EMPLOYEE', 'MANAGER'];  // Liste des rôles disponibles
  selectedRole: string = '';  // Rôle sélectionné
  searchTerm: string = '';
  firstName: string = '';  // Terme de recherche
  lastName: string = '';
  formattedUsers: any[] = [];  // Tableau des utilisateurs formatés
  filter: boolean | null = null;
  constructor(    private userService: UsersService, private router: Router, private filterService: FilterService) {}


  ngOnInit() {
       // Écoute les changements de filtre
       this.filterService.currentFilter.subscribe((filter) => {
        this.filter = filter;
        this.loadUsers(); // Recharge les utilisateurs en fonction du filtre
      });
  }

  // Méthode pour récupérer les utilisateurs
  loadUsers(): void {
    if (this.filter === null) {
      // Aucun filtre, charge tous les utilisateurs
      this.userService.getUsers().subscribe((data: User[]) => {
        console.log(data);
                this.users = data;
        this.formatUserDates();
      });
    } else {
      // Filtrage par statut d'approbation
      this.userService.searchByIsApproved(this.filter).subscribe(
        (data) => {
          this.users = data;
          this.formatUserDates();
        },
        (error) => {
          console.error('Error fetching approved users:', error);
        }
      );
    }
  }


  onSearch() {
      if (this.searchTerm.trim()) {
          // Divise l'entrée en mots pour distinguer prénom et nom
          const terms = this.searchTerm.split(' '); 
          const firstName = terms[0] || ''; // Premier mot = prénom
          const lastName = terms.slice(1).join(' ') || ''; // Reste = nom
          
          this.userService.searchUsersByName(firstName, lastName).subscribe(
              (data: User[]) => {
                  this.users = data;
                  this.formatUserDates(); 
              },
              (error) => {
                  console.error('Erreur lors de la recherche des utilisateurs :', error);
              }
          );
      } else {
          this.loadUsers(); // Recharge les utilisateurs si le champ est vide
      }
  }
  // Fonction pour effectuer la recherche par rôle
  searchByRole() {
    if (this.selectedRole) {
      this.userService.searchByRole(this.selectedRole).subscribe(
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
  formatUserDates() {
    this.formattedUsers = this.users.map(user => {
      const createdAtDate = new Date(user.createdAt);  // Convertir created_at en Date
      user.createdAt= createdAtDate.toLocaleString();  // Convertir en chaîne lisible
      return user;
    });
  }

 
}
