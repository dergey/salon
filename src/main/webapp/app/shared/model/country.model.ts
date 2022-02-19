import { IRegion } from 'app/shared/model/region.model';

export interface ICountry {
  code?: string;
  name?: string;
  region?: IRegion;
}

export interface ICountryRequest {
  name?: string;
  regionId?: number;
}

export interface ICountryModel {
  code?: string;
  name?: string;
  regionId?: number;
}

export const defaultValue: Readonly<ICountry> = { region: {} };
