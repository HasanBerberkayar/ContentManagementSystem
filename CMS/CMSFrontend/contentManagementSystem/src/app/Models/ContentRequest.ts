import { Metadata } from './Metadata';

export interface ContentRequest {
  metadata: Metadata;
  directorId: number;
  actorsIds: number[];
}