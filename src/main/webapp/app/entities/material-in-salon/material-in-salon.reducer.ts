import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IMaterialInSalon, defaultValue } from 'app/shared/model/material-in-salon.model';

export const ACTION_TYPES = {
  FETCH_MATERIALINSALON_LIST: 'materialInSalon/FETCH_MATERIALINSALON_LIST',
  FETCH_MATERIALINSALON: 'materialInSalon/FETCH_MATERIALINSALON',
  CREATE_MATERIALINSALON: 'materialInSalon/CREATE_MATERIALINSALON',
  UPDATE_MATERIALINSALON: 'materialInSalon/UPDATE_MATERIALINSALON',
  DELETE_MATERIALINSALON: 'materialInSalon/DELETE_MATERIALINSALON',
  RESET: 'materialInSalon/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IMaterialInSalon>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type MaterialInSalonState = Readonly<typeof initialState>;

// Reducer

export default (state: MaterialInSalonState = initialState, action): MaterialInSalonState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_MATERIALINSALON_LIST):
    case REQUEST(ACTION_TYPES.FETCH_MATERIALINSALON):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_MATERIALINSALON):
    case REQUEST(ACTION_TYPES.UPDATE_MATERIALINSALON):
    case REQUEST(ACTION_TYPES.DELETE_MATERIALINSALON):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_MATERIALINSALON_LIST):
    case FAILURE(ACTION_TYPES.FETCH_MATERIALINSALON):
    case FAILURE(ACTION_TYPES.CREATE_MATERIALINSALON):
    case FAILURE(ACTION_TYPES.UPDATE_MATERIALINSALON):
    case FAILURE(ACTION_TYPES.DELETE_MATERIALINSALON):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_MATERIALINSALON_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10)
      };
    case SUCCESS(ACTION_TYPES.FETCH_MATERIALINSALON):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_MATERIALINSALON):
    case SUCCESS(ACTION_TYPES.UPDATE_MATERIALINSALON):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_MATERIALINSALON):
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

const apiUrl = 'api/material-in-salons';

// Actions

export const getEntities: ICrudGetAllAction<IMaterialInSalon> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_MATERIALINSALON_LIST,
    payload: axios.get<IMaterialInSalon>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<IMaterialInSalon> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_MATERIALINSALON,
    payload: axios.get<IMaterialInSalon>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IMaterialInSalon> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_MATERIALINSALON,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IMaterialInSalon> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_MATERIALINSALON,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IMaterialInSalon> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_MATERIALINSALON,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
