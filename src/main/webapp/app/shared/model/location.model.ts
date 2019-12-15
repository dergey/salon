import { ICountry } from 'app/shared/model/country.model';

export interface ILocation {
  id?: number;
  address?: string;
  postalCode?: string;
  city?: string;
  stateProvince?: string;
  country?: ICountry;
}

export const defaultValue: Readonly<ILocation> = {};
