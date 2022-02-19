import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Label, Row } from 'reactstrap';
import { AvField, AvForm, AvGroup, AvInput } from 'availity-reactstrap-validation';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';
import { getEntities as getCountries } from 'app/entities/country/country.reducer';
import { createEntity, getEntity, reset, updateEntity } from './location.reducer';
import { ILocation, ILocationRequest } from 'app/shared/model/location.model';

export interface ILocationUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface ILocationUpdateState {
  isNew: boolean;
  countryId: string;
}

export class LocationUpdate extends React.Component<ILocationUpdateProps, ILocationUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      countryId: '0',
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

    this.props.getCountries();
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { locationEntity } = this.props;
      const requestEntity = {
        ...values
      };

      if (this.state.isNew) {
        this.props.createEntity(requestEntity);
      } else {
        this.props.updateEntity(locationEntity.id, requestEntity);
      }
    }
  };

  handleClose = () => {
    this.props.history.push('/location');
  };

  toRequestEntity = (entity: ILocation) => {
    return {
      address: entity.address,
      postalCode: entity.postalCode,
      city: entity.city,
      stateProvince: entity.stateProvince,
      countryCode: entity.country.code
    } as ILocationRequest;
  };

  render() {
    const { locationEntity, countries, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="salonApp.location.home.createOrEditLabel">{isNew ? 'Создать' : 'Редактировать'} адрес</h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : this.toRequestEntity(locationEntity)} onSubmit={this.saveEntity}>
                <AvGroup>
                  <Label id="addressLabel" for="location-address">
                    Адрес
                  </Label>
                  <AvField
                    id="location-address"
                    type="text"
                    name="address"
                    validate={{
                      required: { value: true, errorMessage: 'Это поле не может быть пустым.' }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="postalCodeLabel" for="location-postalCode">
                    Индекс
                  </Label>
                  <AvField id="location-postalCode" type="text" name="postalCode" />
                </AvGroup>
                <AvGroup>
                  <Label id="cityLabel" for="location-city">
                    Город
                  </Label>
                  <AvField
                    id="location-city"
                    type="text"
                    name="city"
                    validate={{
                      required: { value: true, errorMessage: 'Это поле не может быть пустым.' }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="stateProvinceLabel" for="location-stateProvince">
                    Область
                  </Label>
                  <AvField id="location-stateProvince" type="text" name="stateProvince" />
                </AvGroup>
                <AvGroup>
                  <Label for="location-country">Страна</Label>
                  <AvInput id="location-country" type="select" className="form-control" name="countryCode">
                    <option value="" key="0" />
                    {countries
                      ? countries.map(otherEntity => (
                          <option value={otherEntity.code} key={otherEntity.code}>
                            {otherEntity.name}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/location" replace color="info">
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
  countries: storeState.country.entities,
  locationEntity: storeState.location.entity,
  loading: storeState.location.loading,
  updating: storeState.location.updating,
  updateSuccess: storeState.location.updateSuccess
});

const mapDispatchToProps = {
  getCountries,
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
)(LocationUpdate);
