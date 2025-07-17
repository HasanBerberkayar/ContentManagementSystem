import { Observable } from 'rxjs';
import { Injectable } from '@angular/core';     
import { Casts } from '../Models/Casts';
import { HttpClient } from '@angular/common/http';
import { CastRequest } from '../Models/CastRequest';

@Injectable({
  providedIn: 'root'
})
export class CastService {
  constructor(private http: HttpClient) {}

  private apiUrl = 'http://localhost:8080/api/v1/cast'; // Spring Boot API address

  // Method to get all casts
  getAllCasts(): Observable<Casts[]> {
    return this.http.get<Casts[]>(this.apiUrl);
  }

  addCast(cast: CastRequest): Observable<Casts> {
    return this.http.post<Casts>(this.apiUrl, cast);
  }
  deleteCast(castId: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${castId}`);
  }
}

