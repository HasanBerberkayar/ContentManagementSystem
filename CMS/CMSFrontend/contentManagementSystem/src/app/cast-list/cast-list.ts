import { Component, OnInit, NgZone } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CastService } from '../Services/CastService';
import { Casts } from '../Models/Casts';
import { FormsModule } from '@angular/forms';
import { CastRequest } from '../Models/CastRequest';
import { RouterModule } from '@angular/router';
import { ChangeDetectorRef } from '@angular/core';
import './cast-list.css';

@Component({
  selector: 'app-cast-list',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './cast-list.html',
  styleUrls: ['./cast-list.css']
})
export class CastListComponent implements OnInit {
  casts: Casts[] = [];
  newCastName = '';
  newCastPoster = '';
  newCastRole: 'director' | 'actor' = 'actor';
  isAddPopupOpen = false;

  constructor(
    private cdr: ChangeDetectorRef,
    private castService: CastService,
    private ngZone: NgZone
  ) {}

  ngOnInit(): void {
    this.loadCasts();
  }

  loadCasts(): void {
    this.castService.getAllCasts().subscribe(data => {
      this.casts = data;
      this.cdr.detectChanges();
    });
  }

  openAddPopup(): void {
    this.isAddPopupOpen = true;
  }

  closePopup(): void {
    this.isAddPopupOpen = false;
    this.newCastName = '';
    this.newCastPoster = '';
    this.newCastRole = 'actor';

    this.ngZone.run(() => {
      this.cdr.detectChanges();
    });
  }

  addCast(): void {
    if (!this.newCastName.trim()) {
      alert('Name is required.');
      return;
    }
    const castRequest: CastRequest = {
      name: this.newCastName,
      poster: this.newCastPoster,
      role: this.newCastRole
    };
    this.castService.addCast(castRequest).subscribe(() => {
      this.closePopup();
      this.loadCasts();
    });
  }

  deleteCast(castId: number): void {
    this.castService.deleteCast(castId).subscribe(() => {
      this.loadCasts();
    });
  }
}
