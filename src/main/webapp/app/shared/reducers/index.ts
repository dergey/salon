import { combineReducers } from 'redux';
import { loadingBarReducer as loadingBar } from 'react-redux-loading-bar';

import authentication, { AuthenticationState } from './authentication';
import applicationProfile, { ApplicationProfileState } from './application-profile';

import administration, { AdministrationState } from 'app/modules/administration/administration.reducer';
import userManagement, { UserManagementState } from 'app/modules/administration/user-management/user-management.reducer';
import register, { RegisterState } from 'app/modules/account/register/register.reducer';
import activate, { ActivateState } from 'app/modules/account/activate/activate.reducer';
import password, { PasswordState } from 'app/modules/account/password/password.reducer';
import settings, { SettingsState } from 'app/modules/account/settings/settings.reducer';
import passwordReset, { PasswordResetState } from 'app/modules/account/password-reset/password-reset.reducer';
// prettier-ignore
import region, {
  RegionState
} from 'app/entities/region/region.reducer';
// prettier-ignore
import country, {
  CountryState
} from 'app/entities/country/country.reducer';
// prettier-ignore
import location, {
  LocationState
} from 'app/entities/location/location.reducer';
// prettier-ignore
import salon, {
  SalonState
} from 'app/entities/salon/salon.reducer';
// prettier-ignore
import employee, {
  EmployeeState
} from 'app/entities/employee/employee.reducer';
// prettier-ignore
import service, {
  ServiceState
} from 'app/entities/service/service.reducer';
// prettier-ignore
import order, {
  OrderState
} from 'app/entities/order/order.reducer';
// prettier-ignore
import serviceProvided, {
  ServiceProvidedState
} from 'app/entities/service-provided/service-provided.reducer';
// prettier-ignore
import material, {
  MaterialState
} from 'app/entities/material/material.reducer';
// prettier-ignore
import materialInSalon, {
  MaterialInSalonState
} from 'app/entities/material-in-salon/material-in-salon.reducer';
// prettier-ignore
import usedMaterial, {
  UsedMaterialState
} from 'app/entities/used-material/used-material.reducer';
// prettier-ignore
import specialization, {
  SpecializationState
} from 'app/entities/specialization/specialization.reducer';
// prettier-ignore
import client, {
  ClientState
} from 'app/entities/client/client.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

export interface IRootState {
  readonly authentication: AuthenticationState;
  readonly applicationProfile: ApplicationProfileState;
  readonly administration: AdministrationState;
  readonly userManagement: UserManagementState;
  readonly register: RegisterState;
  readonly activate: ActivateState;
  readonly passwordReset: PasswordResetState;
  readonly password: PasswordState;
  readonly settings: SettingsState;
  readonly region: RegionState;
  readonly country: CountryState;
  readonly location: LocationState;
  readonly salon: SalonState;
  readonly employee: EmployeeState;
  readonly service: ServiceState;
  readonly order: OrderState;
  readonly serviceProvided: ServiceProvidedState;
  readonly material: MaterialState;
  readonly materialInSalon: MaterialInSalonState;
  readonly usedMaterial: UsedMaterialState;
  readonly specialization: SpecializationState;
  readonly client: ClientState;
  /* jhipster-needle-add-reducer-type - JHipster will add reducer type here */
  readonly loadingBar: any;
}

const rootReducer = combineReducers<IRootState>({
  authentication,
  applicationProfile,
  administration,
  userManagement,
  register,
  activate,
  passwordReset,
  password,
  settings,
  region,
  country,
  location,
  salon,
  employee,
  service,
  order,
  serviceProvided,
  material,
  materialInSalon,
  usedMaterial,
  specialization,
  client,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
  loadingBar
});

export default rootReducer;
