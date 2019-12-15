import { Moment } from 'moment';
import { IOrder } from 'app/shared/model/order.model';
import { IEmployee } from 'app/shared/model/employee.model';
import { IService } from 'app/shared/model/service.model';

export interface IServiceProvided {
  id?: number;
  startDate?: Moment;
  endDate?: Moment;
  note?: string;
  order?: IOrder;
  employee?: IEmployee;
  service?: IService;
}

export const defaultValue: Readonly<IServiceProvided> = {};
