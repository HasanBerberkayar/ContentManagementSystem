import { Component, ChangeDetectorRef, NgZone } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import { AuthService } from '../Services/AuthService';

@Component({
  selector: 'app-auth',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterModule],
  templateUrl: './auth.html',
  styleUrl: './auth.css'
})
export class AuthComponent {
  authForm: FormGroup;
  isLoginMode = true;
  errorMessage = '';
  successMessage = '';

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private router: Router,
    private cdr: ChangeDetectorRef,
    private ngZone: NgZone
  ) {
    this.authForm = this.fb.group({
      username: ['', Validators.required],
      password: ['', Validators.required]
    });
  }

  toggleMode() {
    this.isLoginMode = !this.isLoginMode;
    this.errorMessage = '';
    this.successMessage = '';
  }

  onSubmit() {
    if (this.authForm.invalid) return;

    const request = this.authForm.value;

    if (this.isLoginMode) {
      this.authService.login(request).subscribe({
        next: (res) => {
          this.authService.storeToken(res);
          this.ngZone.run(() => {
            this.successMessage = 'Login successful!';
            this.errorMessage = '';
            this.cdr.detectChanges();
            setTimeout(() => this.router.navigate(['/content']), 1000);
          });
        },
        error: (err) => {
          this.ngZone.run(() => {
            if (err.status === 401) {
              this.errorMessage = 'Incorrect username or password.';
            } else {
              this.errorMessage = 'Login failed. Please try again.';
            }
            this.successMessage = '';
            this.cdr.detectChanges();
          });
        }
      });
    } else {
      this.authService.register(request).subscribe({
        next: () => {
          this.ngZone.run(() => {
            this.successMessage = 'Registration successful. Redirecting to login...';
            this.errorMessage = '';
            this.cdr.detectChanges();

            setTimeout(() => {
              this.ngZone.run(() => {  
                this.isLoginMode = true;
                this.successMessage = '';
                this.cdr.detectChanges();
              });
            }, 1000);
          });
        },
        error: (err) => {
          this.ngZone.run(() => {
            if (err.status === 409) {
              this.errorMessage = 'Username already exists. Please choose another one.';
            } else {
              this.errorMessage = 'Registration failed. Please try again.';
            }
            this.successMessage = '';
            this.cdr.detectChanges();
          });
        }
      });
    }
  }
}