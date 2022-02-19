import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './country.reducer';

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
                  <th>Код</th>
                  <th>Название страны</th>
                  <th>Регион</th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {countryList.map((country, i) => (
                  <tr key={`entity-${i}`}>
                    <td><Link to={`${match.url}/${country.code}`}>{country.code}</Link></td>
                    <td>{country.name}</td>
                    <td>{country.region ? <Link to={`region/${country.region.id}`}>{country.region.name}</Link> : ''}</td>
                    <td className="text-right">
                      <div className="btn-group flex-btn-group-container">
                        <Button tag={Link} to={`${match.url}/${country.code}`} color="info" size="sm">
                          <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">Просмотр</span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${country.code}/edit`} color="primary" size="sm">
                          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Редактировать</span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${country.code}/delete`} color="danger" size="sm">
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
