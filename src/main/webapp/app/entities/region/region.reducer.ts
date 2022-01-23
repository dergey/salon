import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';

import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IRegion, defaultValue } from 'app/shared/model/region.model';
import { RegionStatus } from 'app/shared/model/enumerations/region-status.model';

export const ACTION_TYPES = {
  FETCH_REGION_LIST: 'region/FETCH_REGION_LIST',
  FETCH_REGION: 'region/FETCH_REGION',
  CHANGE_STATUS_REGION: 'region/CHANGE_STATUS_REGION',
  RESET: 'region/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IRegion>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type RegionState = Readonly<typeof initialState>;

// Reducer

export default (state: RegionState = initialState, action): RegionState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_REGION_LIST):
    case REQUEST(ACTION_TYPES.FETCH_REGION):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CHANGE_STATUS_REGION):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_REGION_LIST):
    case FAILURE(ACTION_TYPES.FETCH_REGION):
    case FAILURE(ACTION_TYPES.CHANGE_STATUS_REGION):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_REGION_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_REGION):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CHANGE_STATUS_REGION):
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

const apiUrl = 'api/regions';

// Actions

export const getEntities: ICrudGetAllAction<IRegion> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_REGION_LIST,
  payload: axios.get<IRegion>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<IRegion> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_REGION,
    payload: axios.get<IRegion>(requestUrl)
  };
};

export const changeStatusEntity: ICrudPutAction<IRegion> = entity => async dispatch => {
  const action = entity.status === RegionStatus.ACTIVATED ? 'deactivate' : 'activate';
  const requestUrl = `${apiUrl}/${entity.id}/${action}`;
  const result = await dispatch({
    type: ACTION_TYPES.CHANGE_STATUS_REGION,
    payload: axios.post(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
