import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IServiceProvided, defaultValue } from 'app/shared/model/service-provided.model';

export const ACTION_TYPES = {
  FETCH_SERVICEPROVIDED_LIST: 'serviceProvided/FETCH_SERVICEPROVIDED_LIST',
  FETCH_SERVICEPROVIDED: 'serviceProvided/FETCH_SERVICEPROVIDED',
  CREATE_SERVICEPROVIDED: 'serviceProvided/CREATE_SERVICEPROVIDED',
  UPDATE_SERVICEPROVIDED: 'serviceProvided/UPDATE_SERVICEPROVIDED',
  DELETE_SERVICEPROVIDED: 'serviceProvided/DELETE_SERVICEPROVIDED',
  RESET: 'serviceProvided/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IServiceProvided>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type ServiceProvidedState = Readonly<typeof initialState>;

// Reducer

export default (state: ServiceProvidedState = initialState, action): ServiceProvidedState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_SERVICEPROVIDED_LIST):
    case REQUEST(ACTION_TYPES.FETCH_SERVICEPROVIDED):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_SERVICEPROVIDED):
    case REQUEST(ACTION_TYPES.UPDATE_SERVICEPROVIDED):
    case REQUEST(ACTION_TYPES.DELETE_SERVICEPROVIDED):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_SERVICEPROVIDED_LIST):
    case FAILURE(ACTION_TYPES.FETCH_SERVICEPROVIDED):
    case FAILURE(ACTION_TYPES.CREATE_SERVICEPROVIDED):
    case FAILURE(ACTION_TYPES.UPDATE_SERVICEPROVIDED):
    case FAILURE(ACTION_TYPES.DELETE_SERVICEPROVIDED):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_SERVICEPROVIDED_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10)
      };
    case SUCCESS(ACTION_TYPES.FETCH_SERVICEPROVIDED):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_SERVICEPROVIDED):
    case SUCCESS(ACTION_TYPES.UPDATE_SERVICEPROVIDED):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_SERVICEPROVIDED):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {}
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState
      };
    default:
      return state;
  }
};

const apiUrl = 'api/service-provideds';

// Actions

export const getEntities: ICrudGetAllAction<IServiceProvided> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_SERVICEPROVIDED_LIST,
    payload: axios.get<IServiceProvided>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<IServiceProvided> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_SERVICEPROVIDED,
    payload: axios.get<IServiceProvided>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IServiceProvided> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_SERVICEPROVIDED,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IServiceProvided> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_SERVICEPROVIDED,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IServiceProvided> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_SERVICEPROVIDED,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
