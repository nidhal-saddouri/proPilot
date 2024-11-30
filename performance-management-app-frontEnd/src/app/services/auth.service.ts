import {HttpClient, HttpParams} from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private port = 8080
  private apiUrl = 'http://localhost:'+this.port+'/api/auth';

 constructor(private http:HttpClient) { }

 login(credentials: { email: string; password: string }): Observable<any> {
   return this.http.post<object>(`${this.apiUrl}/login`, credentials);
 }
  forgotPass(email: string) {
    console.log(`${this.apiUrl}/forgot-password`);
    return this.http.post<object>(`${this.apiUrl}/forgot-password`, { email });
  }

  resetPassword(token: string, newPassword: string) {
    const params = new HttpParams()
      .set('token', token)
      .set('newPassword', newPassword);

    return this.http.post<string>(`${this.apiUrl}/reset-password`, null, { params });
  }


  restPassword(token:string,newPass:string){
   return this.http.post<object>(`${this.apiUrl}/reset-password?token${token}&newPassword=${newPass}`,"");
 }

 saveToken(token: string): void {
  localStorage.setItem('authToken', token);
}

getToken(): string | null {
  return localStorage.getItem('authToken');
}

removeToken(): void {
  localStorage.removeItem('authToken');
}

}
