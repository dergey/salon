import { Sex } from 'app/shared/model/enumerations/sex.model';

export interface IService {
  id?: number;
  title?: string;
  sex?: Sex;
  price?: number;
}

export const defaultValue: Readonly<IService> = {};
