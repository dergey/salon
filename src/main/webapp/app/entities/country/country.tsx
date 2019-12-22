import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { ICrudGetAllAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './country.reducer';
import { ICountry } from 'app/shared/model/country.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ICountryProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export class Country extends React.Component<ICountryProps> {
  componentDidMount() {
    this.props.getEntities();
  }

  render() {
    const { countryList, match } = this.props;
    return (
      <div>
        <h2 id="country-heading">
          Страны
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Создать новую страну
          </Link>
        </h2>
        <div className="table-responsive">
          {countryList && countryList.length > 0 ? (
            <Table responsive aria-describedby="country-heading">
              <thead>
                <tr>
                  <th>Номер</th>
                  <th>Название страны</th>
                  <th>Регион</th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {countryList.map((country, i) => (
                  <tr key={`entity-${i}`}>
                    <td>
                      <Button tag={Link} to={`${match.url}/${country.id}`} color="link" size="sm">
                        {country.id}
                      </Button>
                    </td>
                    <td>{country.countryName}</td>
                    <td>{country.region ? <Link to={`region/${country.region.id}`}>{country.region.regionName}</Link> : ''}</td>
                    <td className="text-right">
                      <div className="btn-group flex-btn-group-container">
                        <Button tag={Link} to={`${match.url}/${country.id}`} color="info" size="sm">
                          <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">Просмотр</span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${country.id}/edit`} color="primary" size="sm">
                          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Редактировать</span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${country.id}/delete`} color="danger" size="sm">
                          <FontAwesomeIcon icon="trash" /> <span className="d-none d-md-inline">Удалить</span>
                        </Button>
                      </div>
                    </td>
                  </tr>
                ))}
              </tbody>
            </Table>
          ) : (
            <div className="alert alert-warning">No Countries found</div>
          )}
        </div>
      </div>
    );
  }
}

const mapStateToProps = ({ country }: IRootState) => ({
  countryList: country.entities
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(Country);
