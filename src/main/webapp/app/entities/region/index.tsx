import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';
import PrivateRoute from "app/shared/auth/private-route";

import Region from './region';
import RegionDetail from './region-detail';
import RegionStatusDialog from "app/entities/region/region-status-dialog";
import { AUTHORITIES } from "app/config/constants";

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={RegionDetail} />
      <ErrorBoundaryRoute path={match.url} component={Region} />
    </Switch>
    <PrivateRoute path={`${match.url}/:id/changeStatus`} component={RegionStatusDialog} hasAnyAuthorities={[AUTHORITIES.ADMIN]} />
  </>
);

export default Routes;
