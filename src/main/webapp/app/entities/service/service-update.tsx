import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, reset } from './service.reducer';
import { IService } from 'app/shared/model/service.model';
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IServiceUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IServiceUpdateState {
  isNew: boolean;
}

export class ServiceUpdate extends React.Component<IServiceUpdateProps, IServiceUpdateState> {
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
      const { serviceEntity } = this.props;
      const entity = {
        ...serviceEntity,
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
    this.props.history.push('/service');
  };

  render() {
    const { serviceEntity, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="salonApp.service.home.createOrEditLabel">{isNew ? 'Создать' : 'Редактировать'} услугу</h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Загрузка...</p>
            ) : (
              <AvForm model={isNew ? {} : serviceEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="service-id">Номер</Label>
                    <AvInput id="service-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="titleLabel" for="service-title">
                    Название
                  </Label>
                  <AvField
                    id="service-title"
                    type="text"
                    name="title"
                    validate={{
                      required: { value: true, errorMessage: 'Это поле не может быть пустым.' }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="sexLabel" for="service-client-sex">
                    Пол
                  </Label>
                  <AvInput id="service-client-sex" type="select" className="form-control" name="sex" value={(!isNew && serviceEntity.sex) || 'MAN'}>
                    <option value="MAN">муж</option>
                    <option value="WOMAN">жен</option>
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label id="priceLabel" for="service-price">
                    Цена
                  </Label>
                  <AvField
                    id="service-price"
                    type="string"
                    className="form-control"
                    name="price"
                    validate={{
                      required: { value: true, errorMessage: 'Это поле не может быть пустым.' },
                      number: { value: true, errorMessage: 'Это поле должно быть числом.' }
                    }}
                  />
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/service" replace color="info">
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
  serviceEntity: storeState.service.entity,
  loading: storeState.service.loading,
  updating: storeState.service.updating,
  updateSuccess: storeState.service.updateSuccess
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
)(ServiceUpdate);
