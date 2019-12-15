import { IEmployee } from 'app/shared/model/employee.model';
import { IService } from 'app/shared/model/service.model';

export interface ISpecialization {
  id?: number;
  note?: string;
  employee?: IEmployee;
  service?: IService;
}

export const defaultValue: Readonly<ISpecialization> = {};
