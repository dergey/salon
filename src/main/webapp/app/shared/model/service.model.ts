export interface IService {
  id?: number;
  title?: string;
  price?: number;
}

export const defaultValue: Readonly<IService> = {};
