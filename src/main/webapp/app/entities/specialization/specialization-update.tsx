import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IEmployee } from 'app/shared/model/employee.model';
import { getEntities as getEmployees } from 'app/entities/employee/employee.reducer';
import { IService } from 'app/shared/model/service.model';
import { getEntities as getServices } from 'app/entities/service/service.reducer';
import { getEntity, updateEntity, createEntity, reset } from './specialization.reducer';
import { ISpecialization } from 'app/shared/model/specialization.model';
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface ISpecializationUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface ISpecializationUpdateState {
  isNew: boolean;
  employeeId: string;
  serviceId: string;
}

export class SpecializationUpdate extends React.Component<ISpecializationUpdateProps, ISpecializationUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
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

    this.props.getEmployees();
    this.props.getServices();
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { specializationEntity } = this.props;
      const entity = {
        ...specializationEntity,
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
    this.props.history.push('/specialization');
  };

  render() {
    const { specializationEntity, employees, services, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="salonApp.specialization.home.createOrEditLabel">{isNew ? 'Создать' : 'Редактировать'} специализацию</h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Загрузка...</p>
            ) : (
              <AvForm model={isNew ? {} : specializationEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="specialization-id">Номер</Label>
                    <AvInput id="specialization-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label for="specialization-employee">Сотрудник</Label>
                  <AvInput id="specialization-employee" type="select" className="form-control" name="employee.id">
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
                  <Label for="specialization-service">Услуга</Label>
                  <AvInput id="specialization-service" type="select" className="form-control" name="service.id">
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
                  <Label id="noteLabel" for="specialization-note">
                    Заметка
                  </Label>
                  <AvField id="specialization-note" type="text" name="note" />
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/specialization" replace color="info">
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
  services: storeState.service.entities,
  specializationEntity: storeState.specialization.entity,
  loading: storeState.specialization.loading,
  updating: storeState.specialization.updating,
  updateSuccess: storeState.specialization.updateSuccess
});

const mapDispatchToProps = {
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
)(SpecializationUpdate);
