import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { SignupService } from '../signup.service';
import { Router } from '@angular/router';
import { UsersService } from '../users.service';

@Component({
  selector: 'app-add-user',
  templateUrl: './add-user.component.html',
  styleUrls: ['./add-user.component.css']
})
export class AddUserComponent {
  addUserForm: FormGroup;
  errorMessage: string | null = null;
  successMessage: string | null = null;
  showPassword = false;
  showConfirmPassword = false;
  isLoading = false; // Indique si la requête est en cours
  constructor(private fb: FormBuilder, private userservice : UsersService, private router: Router) {
    this.addUserForm = this.fb.group({
      firstName: ['', Validators.required],
      lastName: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(8), Validators.pattern(/.*[0-9].*/)]],
      confirmPassword: ['', Validators.required],
      role: ['', Validators.required]
    },);
  }
  addUser(): void {
    if (this.addUserForm.valid) {
      // Activer le message d'attente
      const { firstName, lastName,password, confirmPassword, email, role } = this.addUserForm.value;

      // Appeler le service d'inscription
      this.userservice.AddUser({ firstName, lastName, email, password, confirmPassword,   role: { roleName: role } }).subscribe(
        response => {
          this.successMessage = 'User added successfully.';
          this.errorMessage = null;
        },
        error => {
          this.isLoading = false;
          this.errorMessage = error.error.message || 'Une erreur est survenue. Veuillez réessayer.';
          this.successMessage = null;
        }
      );
    }
  }

}
