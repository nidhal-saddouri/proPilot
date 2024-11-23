import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
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
 searchByRole(roleName: string): Observable<any[]> {
  return this.http.get<any[]>(`${this.baseUrl}/searchRole?roleName=${roleName}`);
}
searchByIsApproved(isapproved: boolean): Observable<any[]> {
  return this.http.get<any[]>(`${this.baseUrl}/searchApproved?isapproved=${isapproved}`);
}
}
