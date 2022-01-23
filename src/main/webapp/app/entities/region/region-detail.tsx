import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './region.reducer';
import { RegionStatus } from "app/shared/model/enumerations/region-status.model";
import { hasAnyAuthority } from "app/shared/auth/private-route";
import { AUTHORITIES } from "app/config/constants";

export interface IRegionDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class RegionDetail extends React.Component<IRegionDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { regionEntity, isAdmin } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            Регион <b>№{regionEntity.id}</b>
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="regionName">Название региона</span>
            </dt>
            <dd>{regionEntity.regionName}</dd>
          </dl>
          <dl className="jh-entity-details">
            <dt>
              <span id="status">Статус</span>
            </dt>
            <dd>{regionEntity.status === RegionStatus.ACTIVATED ? 'Активирован' : 'Деактивирован'}</dd>
          </dl>
          <Button tag={Link} to="/region" replace color="info">
            <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Назад</span>
          </Button>
          &nbsp;
          {isAdmin && <Button tag={Link} to={`/region/${regionEntity.id}/changeStatus`} replace color="primary">
            <FontAwesomeIcon icon="sync" /> <span className="d-none d-md-inline">Активировать/Деактивировать</span>
          </Button>}
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ authentication: { account }, region }: IRootState) => ({
  regionEntity: region.entity,
  isAdmin: hasAnyAuthority(account.authorities, [AUTHORITIES.ADMIN])
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(RegionDetail);
