import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Label, Row } from 'reactstrap';
import { AvField, AvForm, AvGroup, AvInput } from 'availity-reactstrap-validation';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntities as getEmployees } from 'app/entities/employee/employee.reducer';
import { getEntities as getSalons } from 'app/entities/salon/salon.reducer';
import { createEntity, getEntity, reset, updateEntity } from './employee.reducer';
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { IEmployee, IEmployeeRequest } from "app/shared/model/employee.model";

export interface IEmployeeUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IEmployeeUpdateState {
  isNew: boolean;
  managerId: string;
  salonId: string;
}

export class EmployeeUpdate extends React.Component<IEmployeeUpdateProps, IEmployeeUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      managerId: '0',
      salonId: '0',
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

    this.props.getEmployees();
    this.props.getSalons();
  }

  saveEntity = (event, errors, values) => {
    values.hireDate = convertDateTimeToServer(values.hireDate);

    if (errors.length === 0) {
      const id  = this.props.match.params.id;
      const entity = {
        ...values
      };

      if (this.state.isNew) {
        this.props.createEntity(entity);
      } else {
        this.props.updateEntity(id, entity);
      }
    }
  };

  handleClose = () => {
    this.props.history.push('/employee');
  };

  toRequest = (entity: Readonly<IEmployee>) => {
    return {
      firstName: entity.firstName,
      lastName: entity.lastName,
      email: entity.email,
      phoneNumber: entity.phoneNumber,
      hireDate: entity.hireDate,
      salary: entity.salary,
      commissionPct: entity.commissionPct,
      managerId: entity.manager.id,
      salonId: entity.salon.id
    } as IEmployeeRequest;
  };

  render() {
    const { employeeEntity, employees, salons, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="salonApp.employee.home.createOrEditLabel">{isNew ? 'Создать' : 'Редактировать'} сотрудника</h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Загрузка...</p>
            ) : (
              <AvForm model={isNew ? {} : this.toRequest(employeeEntity)} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="employee-id">Номер</Label>
                    <AvInput id="employee-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="firstNameLabel" for="employee-firstName">
                    Имя
                  </Label>
                  <AvField
                    id="employee-firstName"
                    type="text"
                    name="firstName"
                    validate={{
                      required: { value: true, errorMessage: 'Это поле не может быть пустым.' }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="lastNameLabel" for="employee-lastName">
                    Фамилия
                  </Label>
                  <AvField
                    id="employee-lastName"
                    type="text"
                    name="lastName"
                    validate={{
                      required: { value: true, errorMessage: 'Это поле не может быть пустым.' }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="emailLabel" for="employee-email">
                    Email
                  </Label>
                  <AvField
                    id="employee-email"
                    type="text"
                    name="email"
                    validate={{
                      required: { value: true, errorMessage: 'Это поле не может быть пустым.' }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="phoneNumberLabel" for="employee-phoneNumber">
                    Телефоный номер
                  </Label>
                  <AvField
                    id="employee-phoneNumber"
                    type="text"
                    name="phoneNumber"
                    validate={{
                      required: { value: true, errorMessage: 'Это поле не может быть пустым.' }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="hireDateLabel" for="employee-hireDate">
                    Дата найма
                  </Label>
                  <AvInput
                    id="employee-hireDate"
                    type="datetime-local"
                    className="form-control"
                    name="hireDate"
                    placeholder={'YYYY-MM-DD HH:mm'}
                    value={isNew ? null : convertDateTimeFromServer(this.props.employeeEntity.hireDate)}
                    validate={{
                      required: { value: true, errorMessage: 'Это поле не может быть пустым.' }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="salaryLabel" for="employee-salary">
                    Зарплата
                  </Label>
                  <AvField
                    id="employee-salary"
                    type="string"
                    className="form-control"
                    name="salary"
                    validate={{
                      required: { value: true, errorMessage: 'Это поле не может быть пустым.' },
                      number: { value: true, errorMessage: 'Это поле должно быть числом.' }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="commissionPctLabel" for="employee-commissionPct">
                    Комиссионные
                  </Label>
                  <AvField
                    id="employee-commissionPct"
                    type="string"
                    className="form-control"
                    name="commissionPct"
                    validate={{
                      required: { value: true, errorMessage: 'Это поле не может быть пустым.' },
                      number: { value: true, errorMessage: 'Это поле должно быть числом.' }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label for="employee-manager">Менеджер</Label>
                  <AvInput id="employee-manager" type="select" className="form-control" name="managerId">
                    <option value="" key="0" />
                    {employees
                      ? employees.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.lastName} {otherEntity.firstName}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label for="employee-salon">Парикмахерская</Label>
                  <AvInput id="employee-salon" type="select" className="form-control" name="salonId">
                    <option value="" key="0" />
                    {salons
                      ? salons.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.title}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/employee" replace color="info">
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
  employees: storeState.employee.entities,
  salons: storeState.salon.entities,
  employeeEntity: storeState.employee.entity,
  loading: storeState.employee.loading,
  updating: storeState.employee.updating,
  updateSuccess: storeState.employee.updateSuccess
});

const mapDispatchToProps = {
  getEmployees,
  getSalons,
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
)(EmployeeUpdate);
