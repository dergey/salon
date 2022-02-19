import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Label, Row } from 'reactstrap';
import { AvField, AvForm, AvGroup, AvInput } from 'availity-reactstrap-validation';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntities as getLocations } from 'app/entities/location/location.reducer';
import { createEntity, getEntity, reset, updateEntity } from './salon.reducer';
import { ISalon, ISalonRequest } from 'app/shared/model/salon.model';

export interface ISalonUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface ISalonUpdateState {
  isNew: boolean;
  locationId: string;
}

export class SalonUpdate extends React.Component<ISalonUpdateProps, ISalonUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      locationId: '0',
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

    this.props.getLocations();
  }

  saveEntity = (event, errors, values) => {
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
    this.props.history.push('/salon');
  };

  toRequestEntity = (entity: ISalon) => {
    return {
      title: entity.title,
      locationId: entity.location.id
    } as ISalonRequest;
  };

  render() {
    const { salonEntity, locations, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="salonApp.salon.home.createOrEditLabel">{isNew ? 'Создать' : 'Редактировать'} салон</h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : this.toRequestEntity(salonEntity)} onSubmit={this.saveEntity}>
                <AvGroup>
                  <Label id="titleLabel" for="salon-title">
                    Название
                  </Label>
                  <AvField
                    id="salon-title"
                    type="text"
                    name="title"
                    validate={{
                      required: { value: true, errorMessage: 'Это поле не может быть пустым.' }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label for="salon-location">Адрес</Label>
                  <AvInput id="salon-location" type="select" className="form-control" name="locationId">
                    <option value="" key="0" />
                    {locations
                      ? locations.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.country.name + ', ' + otherEntity.city + ', ' + otherEntity.address}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/salon" replace color="info">
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
  locations: storeState.location.entities,
  salonEntity: storeState.salon.entity,
  loading: storeState.salon.loading,
  updating: storeState.salon.updating,
  updateSuccess: storeState.salon.updateSuccess
});

const mapDispatchToProps = {
  getLocations,
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
)(SalonUpdate);
