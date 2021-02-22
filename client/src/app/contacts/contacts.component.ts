import { Component, OnInit } from '@angular/core';
import { Contact } from './contact';
import { ContactService } from '../contact.service';
import { AuthService } from '../auth.service';
import { Router } from '@angular/router';
import { FormBuilder, FormGroup, NgForm, Validators} from "@angular/forms";
import { MyErrorStateMatcher } from "../auth/login/login.component";
import { MatTableDataSource } from '@angular/material/table';
import { AfterViewInit, ViewChild} from '@angular/core';
import { MatSort } from '@angular/material/sort';
import {MatPaginator} from '@angular/material/paginator';


@Component({
  selector: 'app-contacts',
  templateUrl: './contacts.component.html',
  styleUrls: ['./contacts.component.scss']
})
export class ContactsComponent implements OnInit , AfterViewInit{

  contactForm: FormGroup;
  matcher = new MyErrorStateMatcher();
  userContacts: Contact[] = [];
  displayedColumns: string[] = ['contactName', 'age', 'nickname', 'phone'];
  isLoadingResults = true;

  data = new MatTableDataSource<Contact>(this.userContacts);
  @ViewChild(MatPaginator) paginator: MatPaginator;

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
        this.userContacts = contacts;
        this.refresh();
        console.log(this.userContacts);
        this.isLoadingResults = false;
      }, err => {
        console.log(err);
        this.isLoadingResults = false;
      });
  }

  onFormSubmit(form: NgForm) {
    this.userContacts = this.userContacts.concat(new Contact(this.contactForm.value));
    this.contactService.addContact(form)
      .subscribe(res => {
        console.log(res);

      }, (err) => {
        console.log(err);
      });
    this.contactForm.reset();
    this.refresh();
  }

  logout() {
    this.authService.logout();
    this.router.navigate(['login']);
  }

  @ViewChild(MatSort) sort: MatSort;

  ngAfterViewInit() {
    this.data.sort = this.sort;
    this.data.paginator = this.paginator;
  }

  refresh() {
      this.data.data = this.userContacts;
  }

}
