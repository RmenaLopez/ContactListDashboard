import { Component, OnInit } from '@angular/core';
import { Contact } from './contact';
import { ContactService } from '../contact.service';
import { AuthService } from '../auth.service';
import { Router } from '@angular/router';
@Component({
  selector: 'app-contacts',
  templateUrl: './contacts.component.html',
  styleUrls: ['./contacts.component.scss']
})
export class ContactsComponent implements OnInit {

  data: Contact[] = [];
  displayedColumns: string[] = ['name', 'age', 'nickname', 'phone'];
  isLoadingResults = true;

  constructor(private contactService: ContactService, private authService: AuthService, private router: Router) { }

  ngOnInit(): void {
    this.getContacts();
  }

  getContacts(): void {
    this.contactService.getContacts()
      .subscribe(contacts => {
        this.data = contacts;
        console.log(this.data);
        this.isLoadingResults = false;
      }, err => {
        console.log(err);
        this.isLoadingResults = false;
      });
  }

  logout() {
    localStorage.removeItem('token');
    this.router.navigate(['login']);
  }

}
