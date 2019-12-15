import { Moment } from 'moment';
import { IClient } from 'app/shared/model/client.model';

export interface IOrder {
  id?: number;
  date?: Moment;
  client?: IClient;
}

export const defaultValue: Readonly<IOrder> = {};
