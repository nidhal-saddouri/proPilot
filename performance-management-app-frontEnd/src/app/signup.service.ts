import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { catchError, Observable, throwError } from 'rxjs';
export interface Role {
  id: number;
  RoleName: string;
}
export interface User {
  id: number;
  firstName: string;
  lastName: string;
  email: string;
  password: string;
  verified: boolean;  // Correspond à la propriété envoyée par l'API
  loggedIn: boolean;   // Correspond à la propriété envoyée par l'API
  verificationToken: string;
  role : Role;
 }
@Injectable({
  providedIn: 'root'
})
export class SignupService {
  private baseUrl = 'http://localhost:8080/api/auth';
  constructor(private http: HttpClient,private router: Router) { }
  register(user: { firstName: string, lastName: string, email: string, password: string, confirmPassword: string, role: {  roleName: string }}): Observable<any> {
    console.log('Données envoyées au backend:', user); // Vérifiez l'objet envoyé
    return this.http.post(`${this.baseUrl}/register`, user)
      .pipe(
        catchError((error: any) => throwError(error))
      );
  }
  
  // Méthode pour approuver une inscription
  approveRegistration(id: number): Observable<any> {
    return this.http.put(`${this.baseUrl}/approve/${id}`, {});
  }

  // Méthode pour rejeter une inscription
  rejectRegistration(id: number): Observable<any> {
    return this.http.put(`${this.baseUrl}/reject/${id}`, {});
  }

}
