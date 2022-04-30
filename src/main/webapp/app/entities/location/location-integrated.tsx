import React from 'react';
import { Container, Label } from 'reactstrap';
import { AvField, AvGroup, AvInput } from 'availity-reactstrap-validation';
import { ICountry } from "app/shared/model/country.model";

export interface ILocationIntegratedProps {
  isNew: boolean;
  rootNodeName: string;
  countries: ReadonlyArray<ICountry>;
}

export interface ILocationIntegratedState {
  countryId: string;
}

export class LocationIntegrated extends React.Component<ILocationIntegratedProps, ILocationIntegratedState> {
  constructor(props) {
    super(props);
    this.state = {
      countryId: '0'
    };
  }

  render() {
    const { rootNodeName, countries } = this.props;

    return (
      <Container className="card px-4" style={{"margin": "20px 0"}}>
        <AvGroup>
          <Label id="addressLabel" for="location-address">
            Адрес
          </Label>
          <AvField
            id="location-address"
            type="text"
            name={rootNodeName + ".address"}
            validate={{
              required: { value: true, errorMessage: 'Это поле не может быть пустым.' }
            }}
          />
        </AvGroup>
        <AvGroup>
          <Label id="postalCodeLabel" for="location-postalCode">
            Индекс
          </Label>
          <AvField id="location-postalCode" type="text" name={rootNodeName + ".postalCode"}/>
        </AvGroup>
        <AvGroup>
          <Label id="cityLabel" for="location-city">
            Город
          </Label>
          <AvField
            id="location-city"
            type="text"
            name={rootNodeName + ".city"}
            validate={{
              required: { value: true, errorMessage: 'Это поле не может быть пустым.' }
            }}
          />
        </AvGroup>
        <AvGroup>
          <Label id="stateProvinceLabel" for="location-stateProvince">
            Область
          </Label>
          <AvField id="location-stateProvince" type="text" name={rootNodeName + ".stateProvince"}/>
        </AvGroup>
        <AvGroup>
          <Label for="location-country">Страна</Label>
          <AvInput id="location-country" type="select" className="form-control" name={rootNodeName + ".countryCode"}>
            <option value="" key="0"/>
            {countries
              ? countries.map(otherEntity => (
                <option value={otherEntity.code} key={otherEntity.code}>
                  {otherEntity.name}
                </option>
              ))
              : null}
          </AvInput>
        </AvGroup>
      </Container>
    );
  }
}
