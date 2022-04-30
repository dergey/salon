import { ILocation, ILocationRequest } from 'app/shared/model/location.model';

export interface ISalon {
  id?: number;
  title?: string;
  location?: ILocation;
}

export interface ISalonRequest {
  title?: string;
  location?: ILocationRequest;
}

export const defaultValue: Readonly<ISalon> = { location: { country: {} } };
