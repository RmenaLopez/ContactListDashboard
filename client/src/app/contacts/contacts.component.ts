import { Component, OnInit } from '@angular/core';
import { Contact } from './contact';
import { ContactService } from '../contact.service';
import { AuthService } from '../auth.service';
import { Router } from '@angular/router';
import {FormBuilder, FormGroup, NgForm, Validators} from "@angular/forms";
import {MyErrorStateMatcher} from "../auth/login/login.component";
@Component({
  selector: 'app-contacts',
  templateUrl: './contacts.component.html',
  styleUrls: ['./contacts.component.scss']
})
export class ContactsComponent implements OnInit {

  contactForm: FormGroup;
  matcher = new MyErrorStateMatcher();
  data: Contact[] = [];
  displayedColumns: string[] = ['contactName', 'age', 'nickname', 'phone'];
  isLoadingResults = true;

  constructor(private formBuilder: FormBuilder, private contactService: ContactService, private authService: AuthService, private router: Router) { }

  ngOnInit(): void {
    this.getContacts();
    this.contactForm = this.formBuilder.group({
      'contactName' : [null, Validators.required],
      'age' : [null],
      'nickname' : [null],
      'phone' : [null, Validators.required]
    });
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

  onFormSubmit(form: NgForm) {
    this.contactService.addContact(form)
      .subscribe(res => {
        console.log(res);

      }, (err) => {
        console.log(err);
      });
    this.contactForm.reset();
  }

  logout() {
    localStorage.removeItem('token');
    this.router.navigate(['login']);
  }

}
