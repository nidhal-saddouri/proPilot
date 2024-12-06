import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { catchError, Observable, throwError } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AdminService {
  private adminURL = 'http://localhost:8080/api/admin';
  constructor(private http: HttpClient,private router: Router) { }


  loginAdmin(credentials: { email: string, password: string }): Observable<any> {
    return this.http.post(`${this.adminURL}/login`, credentials).pipe(
      catchError((error: any) => {
        console.error('Backend error:', error); // Affichez les erreurs du backend
        return throwError(error);
      })
    );
  }
}
