import axios from 'axios';
import { ICrudDeleteAction, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { FAILURE, REQUEST, SUCCESS } from 'app/shared/reducers/action-type.util';

import { defaultValue, ICountry, ICountryRequest } from 'app/shared/model/country.model';
import { ICrudPutIdAction } from 'app/shared/reducers/action.type';

export const ACTION_TYPES = {
  FETCH_COUNTRY_LIST: 'country/FETCH_COUNTRY_LIST',
  FETCH_COUNTRY: 'country/FETCH_COUNTRY',
  CREATE_COUNTRY: 'country/CREATE_COUNTRY',
  UPDATE_COUNTRY: 'country/UPDATE_COUNTRY',
  DELETE_COUNTRY: 'country/DELETE_COUNTRY',
  RESET: 'country/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<ICountry>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type CountryState = Readonly<typeof initialState>;

// Reducer

export default (state: CountryState = initialState, action): CountryState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_COUNTRY_LIST):
    case REQUEST(ACTION_TYPES.FETCH_COUNTRY):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_COUNTRY):
    case REQUEST(ACTION_TYPES.UPDATE_COUNTRY):
    case REQUEST(ACTION_TYPES.DELETE_COUNTRY):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_COUNTRY_LIST):
    case FAILURE(ACTION_TYPES.FETCH_COUNTRY):
    case FAILURE(ACTION_TYPES.CREATE_COUNTRY):
    case FAILURE(ACTION_TYPES.UPDATE_COUNTRY):
    case FAILURE(ACTION_TYPES.DELETE_COUNTRY):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_COUNTRY_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_COUNTRY):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_COUNTRY):
    case SUCCESS(ACTION_TYPES.UPDATE_COUNTRY):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_COUNTRY):
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

const apiUrl = 'api/countries';

// Actions

export const getEntities: ICrudGetAllAction<ICountry> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_COUNTRY_LIST,
  payload: axios.get<ICountry>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<ICountry> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_COUNTRY,
    payload: axios.get<ICountry>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<ICountry> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_COUNTRY,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutIdAction<ICountryRequest, ICountry> = (code, requestEntity) => async dispatch => {
  const requestUrl = `${apiUrl}/${code}`;
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_COUNTRY,
    payload: axios.put(requestUrl, cleanEntity(requestEntity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<ICountry> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_COUNTRY,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
