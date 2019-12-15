import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import ServiceProvided from './service-provided';
import ServiceProvidedDetail from './service-provided-detail';
import ServiceProvidedUpdate from './service-provided-update';
import ServiceProvidedDeleteDialog from './service-provided-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ServiceProvidedUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ServiceProvidedUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ServiceProvidedDetail} />
      <ErrorBoundaryRoute path={match.url} component={ServiceProvided} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={ServiceProvidedDeleteDialog} />
  </>
);

export default Routes;
