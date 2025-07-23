import { Routes } from '@angular/router';
import { AuthComponent } from './auth/auth';

export const routes: Routes = [
  
  {
    path: 'auth',
    loadComponent: () => import('./auth/auth').then(m => m.AuthComponent)
  },
  {
    path: '',
    redirectTo: 'auth',
    pathMatch: 'full'
  },
  {
    path: 'content',
    loadComponent: () => import('./content-list/content-list')
      .then(m => m.ContentListComponent)
  },
  {
    path: 'casts',
    loadComponent: () => import('./cast-list/cast-list')
      .then(m => m.CastListComponent)
  }
];