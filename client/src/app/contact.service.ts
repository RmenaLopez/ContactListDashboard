import { Injectable } from '@angular/core';
import { Contact } from './contacts/contact';
import { HttpClient } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { catchError, tap } from 'rxjs/operators';
import { Router } from '@angular/router';


const apiUrl = 'http://localhost:8080/api/contacts';

@Injectable({
  providedIn: 'root'
})
export class ContactService {

  constructor(private http: HttpClient, private router: Router) { }

  getContacts(): Observable<Contact[]> {
    return this.http.get<Contact[]>(apiUrl)
      .pipe(
        tap(_ => this.log('fetched Contacts')),
        catchError(this.handleError('getContacts', []))
      );
  }

  addContact(form: any): Observable<any> {
    const res = this.http.post<any>(apiUrl, form)
      .pipe(
        tap(_ => this.log('fetched Contacts')),
        catchError(this.handleError('addContact', []))
      );
    this.router.navigate(['contacts']);
    return res;
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
}
