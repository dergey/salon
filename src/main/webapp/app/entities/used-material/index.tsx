import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import UsedMaterial from './used-material';
import UsedMaterialDetail from './used-material-detail';
import UsedMaterialUpdate from './used-material-update';
import UsedMaterialDeleteDialog from './used-material-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={UsedMaterialUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={UsedMaterialUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={UsedMaterialDetail} />
      <ErrorBoundaryRoute path={match.url} component={UsedMaterial} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={UsedMaterialDeleteDialog} />
  </>
);

export default Routes;
