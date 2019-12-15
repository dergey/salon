import { Moment } from 'moment';
import { IEmployee } from 'app/shared/model/employee.model';
import { ISalon } from 'app/shared/model/salon.model';

export interface IEmployee {
  id?: number;
  firstName?: string;
  lastName?: string;
  email?: string;
  phoneNumber?: string;
  hireDate?: Moment;
  salary?: number;
  commissionPct?: number;
  manager?: IEmployee;
  salon?: ISalon;
}

export const defaultValue: Readonly<IEmployee> = {};
