import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, reset } from './material.reducer';
import { IMaterial } from 'app/shared/model/material.model';
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IMaterialUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IMaterialUpdateState {
  isNew: boolean;
}

export class MaterialUpdate extends React.Component<IMaterialUpdateProps, IMaterialUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
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
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { materialEntity } = this.props;
      const entity = {
        ...materialEntity,
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
    this.props.history.push('/material');
  };

  render() {
    const { materialEntity, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="salonApp.material.home.createOrEditLabel">{isNew ? 'Создать' : 'Редактировать'} материал</h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Загрузка...</p>
            ) : (
              <AvForm model={isNew ? {} : materialEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="material-id">Номер</Label>
                    <AvInput id="material-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="titleLabel" for="material-title">
                    Название
                  </Label>
                  <AvField
                    id="material-title"
                    type="text"
                    name="title"
                    validate={{
                      required: { value: true, errorMessage: 'Это поле не может быть пустым.' }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="unitLabel" for="material-unit">
                    Единица
                  </Label>
                  <AvInput
                    id="material-unit"
                    type="select"
                    className="form-control"
                    name="unit"
                    value={(!isNew && materialEntity.unit) || 'METER'}
                  >
                    <option value="METER">Метр</option>
                    <option value="KILOGRAM">Килограмм</option>
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label id="priceLabel" for="material-price">
                    Цена
                  </Label>
                  <AvField
                    id="material-price"
                    type="text"
                    name="price"
                    validate={{
                      required: { value: true, errorMessage: 'Это поле не может быть пустым.' },
                      number: { value: true, errorMessage: 'Это поле должно быть числом.' }
                    }}
                  />
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/material" replace color="info">
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
  materialEntity: storeState.material.entity,
  loading: storeState.material.loading,
  updating: storeState.material.updating,
  updateSuccess: storeState.material.updateSuccess
});

const mapDispatchToProps = {
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
)(MaterialUpdate);
