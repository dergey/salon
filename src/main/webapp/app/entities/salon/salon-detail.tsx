import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './salon.reducer';
import { ISalon } from 'app/shared/model/salon.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ISalonDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class SalonDetail extends React.Component<ISalonDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { salonEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            Салон <b>№{salonEntity.id}</b>
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="title">Название</span>
            </dt>
            <dd>{salonEntity.title}</dd>
            <dt>Адрес</dt>
            <dd>
              {salonEntity.location ? salonEntity.location.country.countryName + ', ' + salonEntity.location.city + ', ' + salonEntity.location.address : ''}
            </dd>
          </dl>
          <Button tag={Link} to="/salon" replace color="info">
            <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Назад</span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/salon/${salonEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Редактировать</span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ salon }: IRootState) => ({
  salonEntity: salon.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(SalonDetail);
