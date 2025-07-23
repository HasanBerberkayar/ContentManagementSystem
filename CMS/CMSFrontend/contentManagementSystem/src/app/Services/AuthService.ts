import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AuthRequest } from '../Models/AuthRequest';

@Injectable({ providedIn: 'root' })
export class AuthService {
  private apiUrl = 'http://localhost:8081/auth';

  constructor(private http: HttpClient) {}

  register(request: AuthRequest): Observable<string> {
    console.log('Registration Started', request);
    return this.http.post(`${this.apiUrl}/register`, request, { responseType: 'text' });
  }

  login(request: AuthRequest): Observable<string> {
    console.log('Login Started', request);
    return this.http.post(this.apiUrl + '/login', request, { responseType: 'text' });
  }

  storeToken(token: string) {
    localStorage.setItem('jwt_token', token);
  }

  getToken(): string | null {
    return localStorage.getItem('jwt_token');
  }

  logout() {
    localStorage.removeItem('jwt_token');
  }
} 