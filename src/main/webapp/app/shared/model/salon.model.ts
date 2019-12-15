import { ILocation } from 'app/shared/model/location.model';

export interface ISalon {
  id?: number;
  title?: string;
  location?: ILocation;
}

export const defaultValue: Readonly<ISalon> = {};
