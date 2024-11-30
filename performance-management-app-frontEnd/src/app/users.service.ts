import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { catchError, Observable, throwError } from 'rxjs';
export interface Role {
  id: number;
  roleName: string;
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
  approved: boolean;
  createdAt: string;  // Changement de Date en string pour manipuler la date en format ISO
 }
@Injectable({
  providedIn: 'root'
})
export class UsersService {
  private baseUrl = 'http://localhost:8080/api/users';
  constructor(private http: HttpClient,private router: Router) { }

  getUsers(): Observable<User[]> {
    return this.http.get<User[]>(this.baseUrl);
  }
  searchUsersByName(firstname: string,lastname: string): Observable<User[]> {
    return this.http.get<User[]>(`${this.baseUrl}/search?firstname=${firstname}&lastname=${lastname}`);
 }
 searchNotApprovedByRole(roleName: string): Observable<any[]> {
  return this.http.get<any[]>(`${this.baseUrl}/searchRoleNotApproved?roleName=${roleName}`);
}

searchApprovedByRole(roleName: string): Observable<any[]> {
  return this.http.get<any[]>(`${this.baseUrl}/searchRoleApproved?roleName=${roleName}`);
}

getApprovedUsers(): Observable<any[]> {
  return this.http.get<any[]>(`${this.baseUrl}/users-approved`);
}
getNotApprovedUsers(): Observable<any[]> {
  return this.http.get<any[]>(`${this.baseUrl}/users-not-approved`);
}
searchNotApprovedUsers(firstName: string, lastName: string): Observable<any[]> {
  return this.http.get<any[]>(
    `${this.baseUrl}/searchNotApproved?firstName=${firstName}&lastName=${lastName}`
  );
}
searchApprovedUsers(firstName: string, lastName: string): Observable<any[]> {
  return this.http.get<any[]>(
    `${this.baseUrl}/searchApprovedUsers?firstName=${firstName}&lastName=${lastName}`
  );
}



AddUser(user: { firstName: string, lastName: string, email: string, password: string, confirmPassword: string, role: {  roleName: string }}): Observable<any> {
  console.log('Données envoyées au backend:', user); // Vérifiez l'objet envoyé
  return this.http.post(`${this.baseUrl}/AddUser`, user)
    .pipe(
      catchError((error: any) => throwError(error))
    );

} }
