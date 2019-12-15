import { IOrder } from 'app/shared/model/order.model';
import { IMaterial } from 'app/shared/model/material.model';
import { IEmployee } from 'app/shared/model/employee.model';

export interface IUsedMaterial {
  id?: number;
  count?: number;
  decommission?: boolean;
  order?: IOrder;
  material?: IMaterial;
  employee?: IEmployee;
}

export const defaultValue: Readonly<IUsedMaterial> = {
  decommission: false
};
