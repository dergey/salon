import { RegionStatus } from 'app/shared/model/enumerations/region-status.model';

export interface IRegion {
  id?: number;
  status?: RegionStatus;
  name?: string;
}

export const defaultValue: Readonly<IRegion> = {};
