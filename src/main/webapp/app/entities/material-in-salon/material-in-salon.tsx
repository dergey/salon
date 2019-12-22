import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { ICrudGetAllAction, getSortState, IPaginationBaseState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './material-in-salon.reducer';
import { IMaterialInSalon } from 'app/shared/model/material-in-salon.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ITEMS_PER_PAGE } from 'app/shared/util/pagination.constants';

export interface IMaterialInSalonProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export type IMaterialInSalonState = IPaginationBaseState;

export class MaterialInSalon extends React.Component<IMaterialInSalonProps, IMaterialInSalonState> {
  state: IMaterialInSalonState = {
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
    const { materialInSalonList, match, totalItems } = this.props;
    return (
      <div>
        <h2 id="material-in-salon-heading">
          Материалы в салонах
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Добавить материал в салон
          </Link>
        </h2>
        <div className="table-responsive">
          {materialInSalonList && materialInSalonList.length > 0 ? (
            <Table responsive aria-describedby="material-in-salon-heading">
              <thead>
                <tr>
                  <th className="hand" onClick={this.sort('id')}>
                    Номер <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('count')}>
                    Количество <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    Материал <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    Салон <FontAwesomeIcon icon="sort" />
                  </th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {materialInSalonList.map((materialInSalon, i) => (
                  <tr key={`entity-${i}`}>
                    <td>
                      <Button tag={Link} to={`${match.url}/${materialInSalon.id}`} color="link" size="sm">
                        {materialInSalon.id}
                      </Button>
                    </td>
                    <td>{materialInSalon.count}</td>
                    <td>
                      {materialInSalon.material ? (
                        <Link to={`material/${materialInSalon.material.id}`}>{materialInSalon.material.title}</Link>
                      ) : (
                        ''
                      )}
                    </td>
                    <td>
                      {materialInSalon.salon ? <Link to={`salon/${materialInSalon.salon.id}`}>{materialInSalon.salon.title}</Link> : ''}
                    </td>
                    <td className="text-right">
                      <div className="btn-group flex-btn-group-container">
                        <Button tag={Link} to={`${match.url}/${materialInSalon.id}`} color="info" size="sm">
                          <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">Просмотр</span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${materialInSalon.id}/edit`} color="primary" size="sm">
                          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Редактировать</span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${materialInSalon.id}/delete`} color="danger" size="sm">
                          <FontAwesomeIcon icon="trash" /> <span className="d-none d-md-inline">Удалить</span>
                        </Button>
                      </div>
                    </td>
                  </tr>
                ))}
              </tbody>
            </Table>
          ) : (
            <div className="alert alert-warning">Не найдено ни одного материала в салоне</div>
          )}
        </div>
        <div className={materialInSalonList && materialInSalonList.length > 0 ? '' : 'd-none'}>
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

const mapStateToProps = ({ materialInSalon }: IRootState) => ({
  materialInSalonList: materialInSalon.entities,
  totalItems: materialInSalon.totalItems
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(MaterialInSalon);
