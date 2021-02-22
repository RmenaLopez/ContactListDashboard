import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroupDirective, FormBuilder, FormGroup, NgForm, Validators } from '@angular/forms';
import { AuthService } from '../../auth.service';
import { Router } from '@angular/router';
import { ErrorStateMatcher } from '@angular/material/core';
import {MatSnackBar} from '@angular/material/snack-bar';


/** Error when invalid control is dirty, touched, or submitted. */
export class MyErrorStateMatcher implements ErrorStateMatcher {
  isErrorState(control: FormControl | null, form: FormGroupDirective | NgForm | null): boolean {
    const isSubmitted = form && form.submitted;
    return !!(control && control.invalid && (control.dirty || control.touched || isSubmitted));
  }
}

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  loginForm: FormGroup;
  username = '';
  password = '';
  matcher = new MyErrorStateMatcher();
  isLoadingResults = false;

  constructor(private formBuilder: FormBuilder, private router: Router, private authService: AuthService, private invalidCredentialsSnackBar: MatSnackBar) { }

  ngOnInit() {
    if (this.authService.isLoggedIn)
    {
      this.router.navigate(['contacts']);
    } else {

      this.loginForm = this.formBuilder.group({
        'username': [null, Validators.required],
        'password': [null, Validators.required]
      });

      localStorage.removeItem('loggedIn');
      localStorage.removeItem('token');
    }
  }

  onFormSubmit(form: NgForm) {
    this.authService.login(form)
      .subscribe(res => {
        console.log(res);
        if (res.token) {
          localStorage.setItem('token', res.token);
          this.router.navigate(['contacts']);
        } else {
          this.invalidCredentialsPopup();
        }
      }, (err) => {

        console.log(err);
      });

  }

  invalidCredentialsPopup(){
    this.invalidCredentialsSnackBar.open("Invalid username / password", 'Dismiss');
  }

  register() {
    this.router.navigate(['register']);
  }

}
