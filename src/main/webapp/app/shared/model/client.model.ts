import { Sex } from 'app/shared/model/enumerations/sex.model';

export interface IClient {
  id?: number;
  firstName?: string;
  lastName?: string;
  email?: string;
  phoneNumber?: string;
  sex?: Sex;
}

export const defaultValue: Readonly<IClient> = {};
