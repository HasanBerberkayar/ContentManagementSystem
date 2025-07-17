import { Metadata } from './Metadata';
import { Casts } from './Casts';

export interface Content {
  id?: number;
  metadata: Metadata;
  director: Casts | null; 
  actors: Casts[];
  createdAt?: string; // ISO tarih formatı (ör: 2024-07-08T12:34:56)
}