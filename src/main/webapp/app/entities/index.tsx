import React from 'react';
import { Switch } from 'react-router-dom';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Region from './region';
import Country from './country';
import Location from './location';
import Salon from './salon';
import Employee from './employee';
import Service from './service';
import Order from './order';
import ServiceProvided from './service-provided';
import Material from './material';
import MaterialInSalon from './material-in-salon';
import UsedMaterial from './used-material';
import Specialization from './specialization';
import Client from './client';
/* jhipster-needle-add-route-import - JHipster will add routes here */

const Routes = ({ match }) => (
  <div>
    <Switch>
      {/* prettier-ignore */}
      <ErrorBoundaryRoute path={`${match.url}region`} component={Region} />
      <ErrorBoundaryRoute path={`${match.url}country`} component={Country} />
      <ErrorBoundaryRoute path={`${match.url}location`} component={Location} />
      <ErrorBoundaryRoute path={`${match.url}salon`} component={Salon} />
      <ErrorBoundaryRoute path={`${match.url}employee`} component={Employee} />
      <ErrorBoundaryRoute path={`${match.url}service`} component={Service} />
      <ErrorBoundaryRoute path={`${match.url}order`} component={Order} />
      <ErrorBoundaryRoute path={`${match.url}service-provided`} component={ServiceProvided} />
      <ErrorBoundaryRoute path={`${match.url}material`} component={Material} />
      <ErrorBoundaryRoute path={`${match.url}material-in-salon`} component={MaterialInSalon} />
      <ErrorBoundaryRoute path={`${match.url}used-material`} component={UsedMaterial} />
      <ErrorBoundaryRoute path={`${match.url}specialization`} component={Specialization} />
      <ErrorBoundaryRoute path={`${match.url}client`} component={Client} />
      {/* jhipster-needle-add-route-path - JHipster will add routes here */}
    </Switch>
  </div>
);

export default Routes;
