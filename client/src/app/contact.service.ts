import { Injectable } from '@angular/core';
import { Contact } from './contacts/contact';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { catchError, tap } from 'rxjs/operators';
import { Router } from '@angular/router';
import { environment } from './../environments/environment';



const apiUrl = environment.baseUrl + 'api/contacts';

@Injectable({
  providedIn: 'root'
})
export class ContactService {

  constructor(private http: HttpClient, private router: Router) { }

  getContacts(): Observable<Contact[]> {
    const res = this.http.get<Contact[]>(apiUrl)
      .pipe(
        tap(_ => this.log('fetched Contacts')),
        catchError(this.handleError('getContacts', []))
      );
    return res;
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

  deleteContact(contact: Contact): void {
    const options = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json'
      }),
      body: {
        id: contact.id,
        contactName: contact.contactName,
        age: contact.age,
        nickname: contact.nickname,
        phone: contact.phone
      }
    }
    this.http.delete<any>(apiUrl, options).subscribe({
      next: data => {
        console.log('Delete successful')
      },
      error: error => {
        console.error('There was an error!', error);
      }
    });
  }

  updateContact(contact: Contact): void {
    const options = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json'
      }),
      body: {
        id: contact.id,
        contactName: contact.contactName,
        age: contact.age,
        nickname: contact.nickname,
        phone: contact.phone
      }
    }
    this.http.put<any>(apiUrl, options).subscribe({
      next: data => {
        console.log('update successful')
      },
      error: error => {
        console.error('There was an error!', error);
      }
    });
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
