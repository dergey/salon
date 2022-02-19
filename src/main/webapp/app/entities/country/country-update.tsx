import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Label, Row } from 'reactstrap';
import { AvField, AvForm, AvGroup, AvInput } from 'availity-reactstrap-validation';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';
import { getEntities as getRegions } from 'app/entities/region/region.reducer';
import { createEntity, getEntity, reset, updateEntity } from './country.reducer';
import { ICountry, ICountryRequest } from 'app/shared/model/country.model';

export interface ICountryUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface ICountryUpdateState {
  isNew: boolean;
  regionId: string;
}

export class CountryUpdate extends React.Component<ICountryUpdateProps, ICountryUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      regionId: '0',
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

    this.props.getRegions();
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...values
      };
      if (this.state.isNew) {
        this.props.createEntity(entity);
      } else {
        this.props.updateEntity(entity.code, this.toRequest(entity));
      }
    }
  };

  handleClose = () => {
    this.props.history.push('/country');
  };

  toRequest = (entity: Readonly<ICountry>) => {
    return {
      name: entity.name,
      regionId: entity.region.id
    } as ICountryRequest;
  };

  render() {
    const { countryEntity, regions, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="salonApp.country.home.createOrEditLabel">{isNew ? 'Создать' : 'Редактировать'} страну</h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={ isNew ? {} : countryEntity } onSubmit={this.saveEntity}>
                <AvGroup>
                  <Label id="countryCodeLabel" for="country-code">
                    Код страны
                  </Label>
                  <AvField
                    id="country-code"
                    type="text"
                    name="code"
                    validate={{
                      required: { value: true, errorMessage: 'Это поле не может быть пустым.' },
                      minLength: { value: 2, errorMessage: 'Код страны может содержать только 2 символа.' }
                    }}
                    disabled={!isNew}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="countryNameLabel" for="country-name">
                    Название страны
                  </Label>
                  <AvField
                    id="country-name"
                    type="text"
                    name="name"
                    validate={{
                      required: { value: true, errorMessage: 'Это поле не может быть пустым.' }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label for="country-region">Регион</Label>
                  <AvInput id="country-region" type="select" className="form-control" name="region.id">
                    <option value="" key="0" />
                    {regions
                      ? regions.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.name}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/country" replace color="info">
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
  regions: storeState.region.entities,
  countryEntity: storeState.country.entity,
  loading: storeState.country.loading,
  updating: storeState.country.updating,
  updateSuccess: storeState.country.updateSuccess
});

const mapDispatchToProps = {
  getRegions,
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
)(CountryUpdate);
