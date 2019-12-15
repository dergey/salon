import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { ISpecialization, defaultValue } from 'app/shared/model/specialization.model';

export const ACTION_TYPES = {
  FETCH_SPECIALIZATION_LIST: 'specialization/FETCH_SPECIALIZATION_LIST',
  FETCH_SPECIALIZATION: 'specialization/FETCH_SPECIALIZATION',
  CREATE_SPECIALIZATION: 'specialization/CREATE_SPECIALIZATION',
  UPDATE_SPECIALIZATION: 'specialization/UPDATE_SPECIALIZATION',
  DELETE_SPECIALIZATION: 'specialization/DELETE_SPECIALIZATION',
  RESET: 'specialization/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<ISpecialization>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type SpecializationState = Readonly<typeof initialState>;

// Reducer

export default (state: SpecializationState = initialState, action): SpecializationState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_SPECIALIZATION_LIST):
    case REQUEST(ACTION_TYPES.FETCH_SPECIALIZATION):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_SPECIALIZATION):
    case REQUEST(ACTION_TYPES.UPDATE_SPECIALIZATION):
    case REQUEST(ACTION_TYPES.DELETE_SPECIALIZATION):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_SPECIALIZATION_LIST):
    case FAILURE(ACTION_TYPES.FETCH_SPECIALIZATION):
    case FAILURE(ACTION_TYPES.CREATE_SPECIALIZATION):
    case FAILURE(ACTION_TYPES.UPDATE_SPECIALIZATION):
    case FAILURE(ACTION_TYPES.DELETE_SPECIALIZATION):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_SPECIALIZATION_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10)
      };
    case SUCCESS(ACTION_TYPES.FETCH_SPECIALIZATION):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_SPECIALIZATION):
    case SUCCESS(ACTION_TYPES.UPDATE_SPECIALIZATION):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_SPECIALIZATION):
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

const apiUrl = 'api/specializations';

// Actions

export const getEntities: ICrudGetAllAction<ISpecialization> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_SPECIALIZATION_LIST,
    payload: axios.get<ISpecialization>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<ISpecialization> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_SPECIALIZATION,
    payload: axios.get<ISpecialization>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<ISpecialization> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_SPECIALIZATION,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<ISpecialization> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_SPECIALIZATION,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<ISpecialization> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_SPECIALIZATION,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
