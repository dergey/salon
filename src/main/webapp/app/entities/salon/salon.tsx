import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Table } from 'reactstrap';
import { getSortState, IPaginationBaseState, JhiItemCount, JhiPagination } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './salon.reducer';
import { ITEMS_PER_PAGE } from 'app/shared/util/pagination.constants';

export interface ISalonProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export type ISalonState = IPaginationBaseState;

export class Salon extends React.Component<ISalonProps, ISalonState> {
  state: ISalonState = {
    ...getSortState(this.props.location, ITEMS_PER_PAGE)
  };

  componentDidMount() {
    this.getEntities();
  }

  sort = prop => () => {
    this.setState(
      {
        order: this.state.order === 'asc' ? 'desc' : 'asc',
        sort: prop
      },
      () => this.sortEntities()
    );
  };

  sortEntities() {
    this.getEntities();
    this.props.history.push(`${this.props.location.pathname}?page=${this.state.activePage}&sort=${this.state.sort},${this.state.order}`);
  }

  handlePagination = activePage => this.setState({ activePage }, () => this.sortEntities());

  getEntities = () => {
    const { activePage, itemsPerPage, sort, order } = this.state;
    this.props.getEntities(activePage - 1, itemsPerPage, `${sort},${order}`);
  };

  render() {
    const { salonList, match, totalItems } = this.props;
    return (
      <div>
        <h2 id="salon-heading">
          Салоны
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Создать новый салон
          </Link>
        </h2>
        <div className="table-responsive">
          {salonList && salonList.length > 0 ? (
            <Table responsive aria-describedby="salon-heading">
              <thead>
                <tr>
                  <th className="hand" onClick={this.sort('id')}>
                    Номер <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('title')}>
                    Название <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    Адрес <FontAwesomeIcon icon="sort" />
                  </th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {salonList.map((salon, i) => (
                  <tr key={`entity-${i}`}>
                    <td>
                      <Button tag={Link} to={`${match.url}/${salon.id}`} color="link" size="sm">
                        {salon.id}
                      </Button>
                    </td>
                    <td>{salon.title}</td>
                    <td>{salon.location ?
                      <Link to={`location/${salon.location.id}`}>
                        {salon.location.country.name + ', ' + salon.location.city + ', ' + salon.location.address}
                      </Link> : ''}
                    </td>
                    <td className="text-right">
                      <div className="btn-group flex-btn-group-container">
                        <Button tag={Link} to={`${match.url}/${salon.id}`} color="info" size="sm">
                          <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">Просмотр</span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${salon.id}/edit`} color="primary" size="sm">
                          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Редактировать</span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${salon.id}/delete`} color="danger" size="sm">
                          <FontAwesomeIcon icon="trash" /> <span className="d-none d-md-inline">Удалить</span>
                        </Button>
                      </div>
                    </td>
                  </tr>
                ))}
              </tbody>
            </Table>
          ) : (
            <div className="alert alert-warning">Не найдено ни одного салона</div>
          )}
        </div>
        <div className={salonList && salonList.length > 0 ? '' : 'd-none'}>
          <Row className="justify-content-center">
            <JhiItemCount page={this.state.activePage} total={totalItems} itemsPerPage={this.state.itemsPerPage} />
          </Row>
          <Row className="justify-content-center">
            <JhiPagination
              activePage={this.state.activePage}
              onSelect={this.handlePagination}
              maxButtons={5}
              itemsPerPage={this.state.itemsPerPage}
              totalItems={this.props.totalItems}
            />
          </Row>
        </div>
      </div>
    );
  }
}

const mapStateToProps = ({ salon }: IRootState) => ({
  salonList: salon.entities,
  totalItems: salon.totalItems
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(Salon);
