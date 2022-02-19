import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './location.reducer';

export interface ILocationDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class LocationDetail extends React.Component<ILocationDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { locationEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            Адрес <b>№{locationEntity.id}</b>
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="address">Адрес</span>
            </dt>
            <dd>{locationEntity.address}</dd>
            <dt>
              <span id="postalCode">Индекс</span>
            </dt>
            <dd>{locationEntity.postalCode}</dd>
            <dt>
              <span id="city">Город</span>
            </dt>
            <dd>{locationEntity.city}</dd>
            <dt>
              <span id="stateProvince">Область Штат</span>
            </dt>
            <dd>{locationEntity.stateProvince}</dd>
            <dt>Страна</dt>
            <dd>{locationEntity.country ? locationEntity.country.name : ''}</dd>
          </dl>
          <Button tag={Link} to="/location" replace color="info">
            <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Назад</span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/location/${locationEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Редактировать</span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ location }: IRootState) => ({
  locationEntity: location.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(LocationDetail);
