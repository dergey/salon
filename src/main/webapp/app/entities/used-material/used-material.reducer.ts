import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IUsedMaterial, defaultValue } from 'app/shared/model/used-material.model';

export const ACTION_TYPES = {
  FETCH_USEDMATERIAL_LIST: 'usedMaterial/FETCH_USEDMATERIAL_LIST',
  FETCH_USEDMATERIAL: 'usedMaterial/FETCH_USEDMATERIAL',
  CREATE_USEDMATERIAL: 'usedMaterial/CREATE_USEDMATERIAL',
  UPDATE_USEDMATERIAL: 'usedMaterial/UPDATE_USEDMATERIAL',
  DELETE_USEDMATERIAL: 'usedMaterial/DELETE_USEDMATERIAL',
  RESET: 'usedMaterial/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IUsedMaterial>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type UsedMaterialState = Readonly<typeof initialState>;

// Reducer

export default (state: UsedMaterialState = initialState, action): UsedMaterialState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_USEDMATERIAL_LIST):
    case REQUEST(ACTION_TYPES.FETCH_USEDMATERIAL):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_USEDMATERIAL):
    case REQUEST(ACTION_TYPES.UPDATE_USEDMATERIAL):
    case REQUEST(ACTION_TYPES.DELETE_USEDMATERIAL):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_USEDMATERIAL_LIST):
    case FAILURE(ACTION_TYPES.FETCH_USEDMATERIAL):
    case FAILURE(ACTION_TYPES.CREATE_USEDMATERIAL):
    case FAILURE(ACTION_TYPES.UPDATE_USEDMATERIAL):
    case FAILURE(ACTION_TYPES.DELETE_USEDMATERIAL):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_USEDMATERIAL_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10)
      };
    case SUCCESS(ACTION_TYPES.FETCH_USEDMATERIAL):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_USEDMATERIAL):
    case SUCCESS(ACTION_TYPES.UPDATE_USEDMATERIAL):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_USEDMATERIAL):
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

const apiUrl = 'api/used-materials';

// Actions

export const getEntities: ICrudGetAllAction<IUsedMaterial> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_USEDMATERIAL_LIST,
    payload: axios.get<IUsedMaterial>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<IUsedMaterial> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_USEDMATERIAL,
    payload: axios.get<IUsedMaterial>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IUsedMaterial> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_USEDMATERIAL,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IUsedMaterial> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_USEDMATERIAL,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IUsedMaterial> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_USEDMATERIAL,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
