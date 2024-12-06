import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { SignupService } from '../signup.service';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class SignupComponent {
  signupForm: FormGroup;
  errorMessage: string | null = null;
  successMessage: string | null = null;
  showPassword = false;
  showConfirmPassword = false;
  isLoading = false; // Indique si la requête est en cours
  constructor(private fb: FormBuilder, private signupService: SignupService, private router: Router) {
    this.signupForm = this.fb.group({
      firstName: ['', Validators.required],
      lastName: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(8), Validators.pattern(/.*[0-9].*/)]],
      confirmPassword: ['', Validators.required],
      role: ['', Validators.required]
    }, { validator: this.passwordMatchValidator });
  }

  ngOnInit(): void { }

  passwordMatchValidator(form: FormGroup): { [key: string]: boolean } | null {
    const password = form.get('password')!;
    const confirmPassword = form.get('confirmPassword')!;
    if (password.value !== confirmPassword.value) {
      confirmPassword.setErrors({ passwordMismatch: true });
      return { 'passwordMismatch': true };
    } else {
      confirmPassword.setErrors(null);
      return null;
    }
  }

  togglePasswordVisibility() {
    this.showPassword = !this.showPassword;
  }

  toggleConfirmPasswordVisibility() {
    this.showConfirmPassword = !this.showConfirmPassword;
  }

  submit(): void {
    if (this.signupForm.valid) {
      // Activer le message d'attente
      this.isLoading = true;
      const { firstName, lastName, email, password, confirmPassword, role } = this.signupForm.value;

      // Appeler le service d'inscription
      this.signupService.register({ firstName, lastName, email, password, confirmPassword,   role: role  }).subscribe(
        response => {
          // Désactiver le message d'attente et afficher un message de succès
          this.isLoading = false;
          this.successMessage = 'Un email de vérification a été envoyé. Veuillez vérifier votre boîte de réception.';
          this.errorMessage = null;
        },
        error => {
          // Désactiver le message d'attente et afficher un message d'erreur
          this.isLoading = false;
          this.errorMessage = error.error.message || 'Une erreur est survenue. Veuillez réessayer.';
          this.successMessage = null;
        }
      );
    }
  }
}
