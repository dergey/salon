import { IMaterial } from 'app/shared/model/material.model';
import { ISalon } from 'app/shared/model/salon.model';

export interface IMaterialInSalon {
  id?: number;
  count?: number;
  material?: IMaterial;
  salon?: ISalon;
}

export const defaultValue: Readonly<IMaterialInSalon> = {};
