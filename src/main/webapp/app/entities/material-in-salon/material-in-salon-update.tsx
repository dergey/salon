import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IMaterial } from 'app/shared/model/material.model';
import { getEntities as getMaterials } from 'app/entities/material/material.reducer';
import { ISalon } from 'app/shared/model/salon.model';
import { getEntities as getSalons } from 'app/entities/salon/salon.reducer';
import { getEntity, updateEntity, createEntity, reset } from './material-in-salon.reducer';
import { IMaterialInSalon } from 'app/shared/model/material-in-salon.model';
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IMaterialInSalonUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IMaterialInSalonUpdateState {
  isNew: boolean;
  materialId: string;
  salonId: string;
}

export class MaterialInSalonUpdate extends React.Component<IMaterialInSalonUpdateProps, IMaterialInSalonUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      materialId: '0',
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

    this.props.getMaterials();
    this.props.getSalons();
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { materialInSalonEntity } = this.props;
      const entity = {
        ...materialInSalonEntity,
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
    this.props.history.push('/material-in-salon');
  };

  render() {
    const { materialInSalonEntity, materials, salons, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="salonApp.materialInSalon.home.createOrEditLabel">{isNew ? 'Добавить материал в салон' : 'Редактировать материал в салоне'}</h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Загрузка...</p>
            ) : (
              <AvForm model={isNew ? {} : materialInSalonEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="material-in-salon-id">Номер</Label>
                    <AvInput id="material-in-salon-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="countLabel" for="material-in-salon-count">
                    Количество
                  </Label>
                  <AvField
                    id="material-in-salon-count"
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
                  <Label for="material-in-salon-material">Материал</Label>
                  <AvInput id="material-in-salon-material" type="select" className="form-control" name="material.id">
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
                  <Label for="material-in-salon-salon">Салон</Label>
                  <AvInput id="material-in-salon-salon" type="select" className="form-control" name="salon.id">
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
                <Button tag={Link} id="cancel-save" to="/material-in-salon" replace color="info">
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
  materials: storeState.material.entities,
  salons: storeState.salon.entities,
  materialInSalonEntity: storeState.materialInSalon.entity,
  loading: storeState.materialInSalon.loading,
  updating: storeState.materialInSalon.updating,
  updateSuccess: storeState.materialInSalon.updateSuccess
});

const mapDispatchToProps = {
  getMaterials,
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
)(MaterialInSalonUpdate);
