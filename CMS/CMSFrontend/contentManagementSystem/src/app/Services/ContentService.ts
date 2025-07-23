import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Content } from '../Models/Content';
import { Casts } from '../Models/Casts';
import { Metadata } from '../Models/Metadata';
import { map } from 'rxjs/operators'; 

@Injectable({
  providedIn: 'root'
})
export class ContentService {
  private apiUrl = 'http://localhost:8081/content'; 
  private apiUrlImdb = 'http://www.omdbapi.com/?apikey=b8a2b750&t='; 

  constructor(private http: HttpClient) {}

  getAllContents(): Observable<Content[]> {
    return this.http.get<Content[]>(this.apiUrl);
  }

  deleteContent(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }

  addContent(contentRequest: any): Observable<void> {
    return this.http.post<void>(this.apiUrl, contentRequest);
  }

  updateContent(id: number, params: any): Observable<void> {
    return this.http.put<void>(`${this.apiUrl}/${id}`, null, { params });
  }

  getContentFromImdb(title: string): Observable<Content> {
  return this.http.get<any>(`${this.apiUrlImdb}${title}`).pipe(
    map((apiResponse: any) => { // <-- Tip belirtildi
      const metadata: Metadata = {
        title: apiResponse.Title,
        plot: apiResponse.Plot,
        poster: apiResponse.Poster,
        year: parseInt(apiResponse.Year),
        language: apiResponse.Language,
        country: apiResponse.Country
      };

      const director: Casts = {
        name: apiResponse.Director,
        poster: '',
        role: 'director'
      };

      const actors: Casts[] = apiResponse.Actors?.split(',').map((name: string) => ({
        name: name.trim(),
        poster: '',
        role: 'actor'
      })) || [];

      return {
        metadata,
        director,
        actors
      } as Content;
    })
  );
}
  //cronjob
}