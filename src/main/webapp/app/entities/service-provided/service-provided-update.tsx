import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IOrder } from 'app/shared/model/order.model';
import { getEntities as getOrders } from 'app/entities/order/order.reducer';
import { IEmployee } from 'app/shared/model/employee.model';
import { getEntities as getEmployees } from 'app/entities/employee/employee.reducer';
import { IService } from 'app/shared/model/service.model';
import { getEntities as getServices } from 'app/entities/service/service.reducer';
import { getEntity, updateEntity, createEntity, reset } from './service-provided.reducer';
import { IServiceProvided } from 'app/shared/model/service-provided.model';
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IServiceProvidedUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IServiceProvidedUpdateState {
  isNew: boolean;
  orderId: string;
  employeeId: string;
  serviceId: string;
}

export class ServiceProvidedUpdate extends React.Component<IServiceProvidedUpdateProps, IServiceProvidedUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      orderId: '0',
      employeeId: '0',
      serviceId: '0',
      isNew: !this.props.match.params || !this.props.match.params.id
    };
  }

  componentWillUpdate(nextProps, nextState) {
    if (nextProps.updateSuccess !== this.props.updateSuccess && nextProps.updateSuccess) {
      this.handleClose();
    }
  }

  componentDidMount() {
    if (this.state.isNew) {
      this.props.reset();
    } else {
      this.props.getEntity(this.props.match.params.id);
    }

    this.props.getOrders();
    this.props.getEmployees();
    this.props.getServices();
  }

  saveEntity = (event, errors, values) => {
    values.startDate = convertDateTimeToServer(values.startDate);
    values.endDate = convertDateTimeToServer(values.endDate);

    if (errors.length === 0) {
      const { serviceProvidedEntity } = this.props;
      const entity = {
        ...serviceProvidedEntity,
        ...values
      };

      if (this.state.isNew) {
        this.props.createEntity(entity);
      } else {
        this.props.updateEntity(entity);
      }
    }
  };

  handleClose = () => {
    this.props.history.push('/service-provided');
  };

  render() {
    const { serviceProvidedEntity, orders, employees, services, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="salonApp.serviceProvided.home.createOrEditLabel">{isNew ? 'Создать' : 'Редактировать'} оказанную услугу</h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Загрузка...</p>
            ) : (
              <AvForm model={isNew ? {} : serviceProvidedEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="service-provided-id">Номер</Label>
                    <AvInput id="service-provided-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="startDateLabel" for="service-provided-startDate">
                    Дата начала
                  </Label>
                  <AvInput
                    id="service-provided-startDate"
                    type="datetime-local"
                    className="form-control"
                    name="startDate"
                    placeholder={'YYYY-MM-DD HH:mm'}
                    value={isNew ? null : convertDateTimeFromServer(this.props.serviceProvidedEntity.startDate)}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="endDateLabel" for="service-provided-endDate">
                    Дата окончания
                  </Label>
                  <AvInput
                    id="service-provided-endDate"
                    type="datetime-local"
                    className="form-control"
                    name="endDate"
                    placeholder={'YYYY-MM-DD HH:mm'}
                    value={isNew ? null : convertDateTimeFromServer(this.props.serviceProvidedEntity.endDate)}
                  />
                </AvGroup>
                <AvGroup>
                  <Label for="service-provided-order">Заказ</Label>
                  <AvInput id="service-provided-order" type="select" className="form-control" name="order.id">
                    <option value="" key="0" />
                    {orders
                      ? orders.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label for="service-provided-service">Услуга</Label>
                  <AvInput id="service-provided-service" type="select" className="form-control" name="service.id">
                    <option value="" key="0" />
                    {services
                      ? services.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.title}
                        </option>
                      ))
                      : null}
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label for="service-provided-employee">Сотрудник</Label>
                  <AvInput id="service-provided-employee" type="select" className="form-control" name="employee.id">
                    <option value="" key="0" />
                    {employees
                      ? employees.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.lastName + ' ' + otherEntity.firstName}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label id="noteLabel" for="service-provided-note">
                    Примечание
                  </Label>
                  <AvField id="service-provided-note" type="text" name="note" />
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/service-provided" replace color="info">
                  <FontAwesomeIcon icon="arrow-left" />
                  &nbsp;
                  <span className="d-none d-md-inline">Назад</span>
                </Button>
                &nbsp;
                <Button color="primary" id="save-entity" type="submit" disabled={updating}>
                  <FontAwesomeIcon icon="save" />
                  &nbsp; Сохранить
                </Button>
              </AvForm>
            )}
          </Col>
        </Row>
      </div>
    );
  }
}

const mapStateToProps = (storeState: IRootState) => ({
  orders: storeState.order.entities,
  employees: storeState.employee.entities,
  services: storeState.service.entities,
  serviceProvidedEntity: storeState.serviceProvided.entity,
  loading: storeState.serviceProvided.loading,
  updating: storeState.serviceProvided.updating,
  updateSuccess: storeState.serviceProvided.updateSuccess
});

const mapDispatchToProps = {
  getOrders,
  getEmployees,
  getServices,
  getEntity,
  updateEntity,
  createEntity,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(ServiceProvidedUpdate);
