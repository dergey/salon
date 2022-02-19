import { ILocation } from 'app/shared/model/location.model';

export interface ISalon {
  id?: number;
  title?: string;
  location?: ILocation;
}

export interface ISalonRequest {
  title?: string;
  locationId?: number;
}

export const defaultValue: Readonly<ISalon> = { location: {} };
