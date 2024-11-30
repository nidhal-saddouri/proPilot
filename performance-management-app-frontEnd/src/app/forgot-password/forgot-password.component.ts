import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import {AuthService} from "../services/auth.service";

@Component({
  selector: 'app-forgot-password',
  templateUrl: './forgot-password.component.html',
  styleUrls: ['./forgot-password.component.css']
})
export class ForgotPasswordComponent {
  forgotPasswordForm: FormGroup;
  isSubmitting = false;
  message = '';


  constructor(private fb: FormBuilder, private router: Router,private authService: AuthService) {
    this.forgotPasswordForm = this.fb.group({
      email: ['', [Validators.required, Validators.email]]
    });
  }

  onSubmit() {
    if (this.forgotPasswordForm.invalid) return;

    this.isSubmitting = true;
    const email = this.forgotPasswordForm.value.email;

    this.authService.forgotPass(email)
      .subscribe({
        next: (response: any) => {
          console.log(response)
          this.message = 'A password reset link has been sent to your email.';
          this.isSubmitting = false;
        },
        error: (err) => {
          this.message = 'An error occurred. Please try again.';
          this.isSubmitting = false;
        }
      });
  }
}
