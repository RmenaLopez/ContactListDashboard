import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { catchError, tap } from 'rxjs/operators';
import {environment} from "../environments/environment";

const apiUrl = environment.baseUrl + 'api/auth/';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private loggedInStatus = JSON.parse(localStorage.getItem('loggedIn') || 'false');
  redirectUrl: string;

  constructor(private http: HttpClient) {}

  login(data: any): Observable<any> {
    console.log("login method!");
    return this.http.post<any>(apiUrl + 'login', data)
      .pipe(
        tap(_ => localStorage.setItem('loggedIn', 'true')),
        catchError(this.handleError('login', []))
      );
  }

  logout(): Observable<any> {
    localStorage.removeItem('loggedIn')
    return this.http.get<any>(apiUrl + 'signout')
      .pipe(
        tap(),
        catchError(this.handleError('logout', []))
      );
  }

  register(data: any): Observable<any> {
    console.log("register method!");
    return this.http.post<any>(apiUrl + 'register', data)
      .pipe(
        tap(_ => this.log('login')),
        catchError(this.handleError('login', []))
      );
  }

  private handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {

      console.error(error); // log to console instead
      this.log(`${operation} failed: ${error.message}`);

      return of(result as T);
    };
  }

  private log(message: string) {
    console.log(message);
  }

  get isLoggedIn(){
    return localStorage.getItem('loggedIn');
  }
}
