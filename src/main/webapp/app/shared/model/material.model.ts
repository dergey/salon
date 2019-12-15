import { Unit } from 'app/shared/model/enumerations/unit.model';

export interface IMaterial {
  id?: number;
  title?: string;
  unit?: Unit;
  price?: number;
}

export const defaultValue: Readonly<IMaterial> = {};
