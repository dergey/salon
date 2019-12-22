import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './service-provided.reducer';
import { IServiceProvided } from 'app/shared/model/service-provided.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IServiceProvidedDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class ServiceProvidedDetail extends React.Component<IServiceProvidedDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { serviceProvidedEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            Предоставленная услуга <b>№{serviceProvidedEntity.id}</b>
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="startDate">Дата начала</span>
            </dt>
            <dd>
              <TextFormat value={serviceProvidedEntity.startDate} type="date" format={APP_DATE_FORMAT} />
            </dd>
            <dt>
              <span id="endDate">Дата окончания</span>
            </dt>
            <dd>
              <TextFormat value={serviceProvidedEntity.endDate} type="date" format={APP_DATE_FORMAT} />
            </dd>
            <dt>Заказ</dt>
            <dd>{serviceProvidedEntity.order ? serviceProvidedEntity.order.id : ''}</dd>
            <dt>Услуга</dt>
            <dd>{serviceProvidedEntity.service ? serviceProvidedEntity.service.title : ''}</dd>
            <dt>Сотрудник</dt>
            <dd>{serviceProvidedEntity.employee ? serviceProvidedEntity.employee.lastName : ''}</dd>
            <dt>
              <span id="note">Примечание</span>
            </dt>
            <dd>{serviceProvidedEntity.note}</dd>
          </dl>
          <Button tag={Link} to="/service-provided" replace color="info">
            <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Назад</span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/service-provided/${serviceProvidedEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Редактировать</span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ serviceProvided }: IRootState) => ({
  serviceProvidedEntity: serviceProvided.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(ServiceProvidedDetail);
