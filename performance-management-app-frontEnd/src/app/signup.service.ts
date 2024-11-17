import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { catchError, Observable, throwError } from 'rxjs';
export interface User {
  id: number;
  firstName: string;
  lastName: string;
  email: string;
  password: string;
  verified: boolean;  // Correspond à la propriété envoyée par l'API
  loggedIn: boolean;   // Correspond à la propriété envoyée par l'API
  verificationToken: string;
}
@Injectable({
  providedIn: 'root'
})
export class SignupService {
  private baseUrl = 'http://localhost:8080/api/users';
  constructor(private http: HttpClient,private router: Router) { }
  register(user: { firstName: string, lastName: string, email: string, password: string, confirmPassword: string }): Observable<any> {
    return this.http.post(`${this.baseUrl}/register`, user)
      .pipe(
        catchError((error: any) => throwError(error)) // Handle error appropriately
      );
  }

  verifyEmail(token: string): Observable<any> {
    return this.http.get(`${this.baseUrl}/verify?token=${token}`)
      .pipe(
        catchError((error: any) => throwError(error)) // Handle error appropriately
      );
  }
}
