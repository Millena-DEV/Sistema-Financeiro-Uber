import {Injectable }from'@angular/core';
import {HttpClient }from'@angular/common/http';
import { tap }from'rxjs/operators';
import { Observable } from 'rxjs';


@Injectable({
  providedIn: 'root'
})

export class LoginService {
private apiUrl = 'http://localhost:8080/auth/login';

  constructor(private http: HttpClient) {}

   login(email: string, password: string): Observable<any> {
    return this.http.post<any>(this.apiUrl, { email, senha: password })
.pipe(
      tap(response => {
        if (response.token) {
          localStorage.setItem('token', response.token); // salva JWT
        }
      })
    );
  }

  getToken(): string | null {
    return localStorage.getItem('token');
  }

  logout(): void {
    localStorage.removeItem('token');
  }

  isAuthenticated(): boolean {
    return !!this.getToken();
  }
}
