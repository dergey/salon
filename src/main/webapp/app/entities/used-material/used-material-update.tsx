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
import { IMaterial } from 'app/shared/model/material.model';
import { getEntities as getMaterials } from 'app/entities/material/material.reducer';
import { IEmployee } from 'app/shared/model/employee.model';
import { getEntities as getEmployees } from 'app/entities/employee/employee.reducer';
import { getEntity, updateEntity, createEntity, reset } from './used-material.reducer';
import { IUsedMaterial } from 'app/shared/model/used-material.model';
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IUsedMaterialUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IUsedMaterialUpdateState {
  isNew: boolean;
  orderId: string;
  materialId: string;
  employeeId: string;
}

export class UsedMaterialUpdate extends React.Component<IUsedMaterialUpdateProps, IUsedMaterialUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      orderId: '0',
      materialId: '0',
      employeeId: '0',
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
    this.props.getMaterials();
    this.props.getEmployees();
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { usedMaterialEntity } = this.props;
      const entity = {
        ...usedMaterialEntity,
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
    this.props.history.push('/used-material');
  };

  render() {
    const { usedMaterialEntity, orders, materials, employees, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="salonApp.usedMaterial.home.createOrEditLabel">{isNew ? 'Создать' : 'Редактировать'} использованный материал</h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Загрузка...</p>
            ) : (
              <AvForm model={isNew ? {} : usedMaterialEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="used-material-id">Номер</Label>
                    <AvInput id="used-material-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="countLabel" for="used-material-count">
                    Количество
                  </Label>
                  <AvField
                    id="used-material-count"
                    type="string"
                    className="form-control"
                    name="count"
                    validate={{
                      required: { value: true, errorMessage: 'Это поле не может быть пустым.' },
                      number: { value: true, errorMessage: 'Это поле должно быть числом.' }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="decommissionLabel" check>
                    <AvInput id="used-material-decommission" type="checkbox" className="form-control" name="decommission" />
                    Вывод из эксплуатации?
                  </Label>
                </AvGroup>
                <AvGroup>
                  <Label for="used-material-order">Заказ</Label>
                  <AvInput id="used-material-order" type="select" className="form-control" name="order.id">
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
                  <Label for="used-material-material">Материал</Label>
                  <AvInput id="used-material-material" type="select" className="form-control" name="material.id">
                    <option value="" key="0" />
                    {materials
                      ? materials.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.title}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label for="used-material-employee">Сотрудник</Label>
                  <AvInput id="used-material-employee" type="select" className="form-control" name="employee.id">
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
                <Button tag={Link} id="cancel-save" to="/used-material" replace color="info">
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
  materials: storeState.material.entities,
  employees: storeState.employee.entities,
  usedMaterialEntity: storeState.usedMaterial.entity,
  loading: storeState.usedMaterial.loading,
  updating: storeState.usedMaterial.updating,
  updateSuccess: storeState.usedMaterial.updateSuccess
});

const mapDispatchToProps = {
  getOrders,
  getMaterials,
  getEmployees,
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
)(UsedMaterialUpdate);
