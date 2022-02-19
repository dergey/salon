import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './region.reducer';
import { RegionStatus } from "app/shared/model/enumerations/region-status.model";
import { hasAnyAuthority } from "app/shared/auth/private-route";
import { AUTHORITIES } from "app/config/constants";

export interface IRegionProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export class Region extends React.Component<IRegionProps> {
  componentDidMount() {
    this.props.getEntities();
  }

  render() {
    const { regionList, isAdmin, match } = this.props;
    return (
      <div>
        <h2 id="region-heading">
          Регионы
        </h2>
        <div className="table-responsive">
          {regionList && regionList.length > 0 ? (
            <Table responsive aria-describedby="region-heading">
              <thead>
                <tr>
                  <th>Номер</th>
                  <th>Название</th>
                  <th>Статус</th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {regionList.map((region, i) => (
                  <tr key={`entity-${i}`}>
                    <td>
                      <Button tag={Link} to={`${match.url}/${region.id}`} color="link" size="sm">
                        {region.id}
                      </Button>
                    </td>
                    <td>{region.name}</td>
                    <td>{region.status === RegionStatus.ACTIVATED ? 'Активирован' : 'Деактивирован'}</td>
                    <td className="text-right">
                      <div className="btn-group flex-btn-group-container">
                        <Button tag={Link} to={`${match.url}/${region.id}`} color="info" size="sm">
                          <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">Просмотр</span>
                        </Button>
                        {isAdmin && <Button tag={Link} to={`${match.url}/${region.id}/changeStatus`} color="primary" size="sm">
                          <FontAwesomeIcon icon="sync" /> <span className="d-none d-md-inline">Активировать/Деактивировать</span>
                        </Button>}
                      </div>
                    </td>
                  </tr>
                ))}
              </tbody>
            </Table>
          ) : (
            <div className="alert alert-warning">Не найдено ни одного региона</div>
          )}
        </div>
      </div>
    );
  }
}

const mapStateToProps = ({ authentication: { account }, region }: IRootState) => ({
  regionList: region.entities,
  isAdmin: hasAnyAuthority(account.authorities, [AUTHORITIES.ADMIN])
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(Region);
