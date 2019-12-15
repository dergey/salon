import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import MaterialInSalon from './material-in-salon';
import MaterialInSalonDetail from './material-in-salon-detail';
import MaterialInSalonUpdate from './material-in-salon-update';
import MaterialInSalonDeleteDialog from './material-in-salon-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={MaterialInSalonUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={MaterialInSalonUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={MaterialInSalonDetail} />
      <ErrorBoundaryRoute path={match.url} component={MaterialInSalon} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={MaterialInSalonDeleteDialog} />
  </>
);

export default Routes;
