import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../services/auth.service';
import { AdminService } from '../admin.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  loginForm!: FormGroup;
  errorMessage: string | null = null;

  constructor(
    private fb: FormBuilder,
    private auth: AuthService,
    private router: Router,
    private adminService: AdminService
  ) {}

  ngOnInit(): void {
    this.loginForm = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(6)]]
    });
  }

  submit(): void {
    if (this.loginForm.valid) {
      const credentials = this.loginForm.value;

      // Vérifier si l'utilisateur est un admin ou un user
      if (credentials.email.includes('admin')) {
        // Authentification administrateur
        this.adminService.loginAdmin(credentials).subscribe(
          (response) => {
            console.log('Admin login successful', response);
            localStorage.setItem('currentAdmin', JSON.stringify(response.admin));
            this.router.navigate(['/admin-dashboard']); // Redirection admin
          },
          (error) => {
            console.error('Admin login failed:', error);
            this.errorMessage = 'Admin login failed. Please check your credentials.';
          }
        );
      } else {
        // Authentification utilisateur
        this.auth.login(credentials).subscribe(
          (response) => {
            console.log('User login successful', response);
            this.auth.saveToken(response.token); // Enregistrer le token utilisateur
            localStorage.setItem('currentUser', JSON.stringify(response.user));
            this.router.navigate(['/user-dashboard']); // Redirection utilisateur
          },
          (error) => {
            console.error('User login failed:', error);
            this.errorMessage = 'User login failed. Please check your credentials.';
          }
        );
      }
    } else {
      // Afficher les erreurs si le formulaire est invalide
      this.loginForm.markAllAsTouched();
    }
  }
}
