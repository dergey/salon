import { IPayload, IPayloadResult } from 'react-jhipster/src/type/redux-action.type';

export type ICrudPutIdAction<T, R> = (id: string | number, data?: T) => IPayload<R> | IPayloadResult<R>;
