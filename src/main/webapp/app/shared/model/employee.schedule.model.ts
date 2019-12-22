import { Moment } from 'moment';
import { IServiceProvided } from 'app/shared/model/service-provided.model';
import { DayOfWeek } from 'app/shared/model/enumerations/dayofweek.model';

export interface IEmployeeSchedule {
  startDate?: Moment;
  endDate?: Moment;
  schedule?: Map<DayOfWeek, Array<IServiceProvided>>;
}

export const defaultScheduleValue: Readonly<IEmployeeSchedule> = {
  schedule: new Map()
};
