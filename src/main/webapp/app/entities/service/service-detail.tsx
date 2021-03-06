import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './service.reducer';
import { IService } from 'app/shared/model/service.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IServiceDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class ServiceDetail extends React.Component<IServiceDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { serviceEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            Услуга <b>№{serviceEntity.id}</b>
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="title">Название</span>
            </dt>
            <dd>{serviceEntity.title}</dd>
            <dt>
              <span id="sex">Пол</span>
            </dt>
            <dd>{serviceEntity.sex === "MAN" ? "муж" : serviceEntity.sex === "WOMAN" ? "жен" : ""}</dd>
            <dt>
              <span id="price">Цена</span>
            </dt>
            <dd>{serviceEntity.price}</dd>
          </dl>
          <Button tag={Link} to="/service" replace color="info">
            <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Назад</span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/service/${serviceEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Редактировать</span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ service }: IRootState) => ({
  serviceEntity: service.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(ServiceDetail);
