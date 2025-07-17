import { Routes } from '@angular/router';

export const routes: Routes = [
  {
    path: '',
    redirectTo: 'content',
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