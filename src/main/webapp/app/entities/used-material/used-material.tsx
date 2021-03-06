import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { ICrudGetAllAction, getSortState, IPaginationBaseState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './used-material.reducer';
import { IUsedMaterial } from 'app/shared/model/used-material.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ITEMS_PER_PAGE } from 'app/shared/util/pagination.constants';

export interface IUsedMaterialProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export type IUsedMaterialState = IPaginationBaseState;

export class UsedMaterial extends React.Component<IUsedMaterialProps, IUsedMaterialState> {
  state: IUsedMaterialState = {
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
    const { usedMaterialList, match, totalItems } = this.props;
    return (
      <div>
        <h2 id="used-material-heading">
          Использованные материалы
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Создать использованный материал
          </Link>
        </h2>
        <div className="table-responsive">
          {usedMaterialList && usedMaterialList.length > 0 ? (
            <Table responsive aria-describedby="used-material-heading">
              <thead>
                <tr>
                  <th className="hand" onClick={this.sort('id')}>
                    Номер <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('count')}>
                    Количество <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('decommission')}>
                    Списание <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    Заказ <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    Материал <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    Сотрудник <FontAwesomeIcon icon="sort" />
                  </th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {usedMaterialList.map((usedMaterial, i) => (
                  <tr key={`entity-${i}`}>
                    <td>
                      <Button tag={Link} to={`${match.url}/${usedMaterial.id}`} color="link" size="sm">
                        {usedMaterial.id}
                      </Button>
                    </td>
                    <td>{usedMaterial.count}</td>
                    <td>{usedMaterial.decommission ? 'да' : 'нет'}</td>
                    <td>{usedMaterial.order ? <Link to={`order/${usedMaterial.order.id}`}>{usedMaterial.order.id}</Link> : ''}</td>
                    <td>
                      {usedMaterial.material ? <Link to={`material/${usedMaterial.material.id}`}>{usedMaterial.material.title}</Link> : ''}
                    </td>
                    <td>
                      {usedMaterial.employee ? (
                        <Link to={`employee/${usedMaterial.employee.id}`}>{usedMaterial.employee.lastName}</Link>
                      ) : (
                        ''
                      )}
                    </td>
                    <td className="text-right">
                      <div className="btn-group flex-btn-group-container">
                        <Button tag={Link} to={`${match.url}/${usedMaterial.id}`} color="info" size="sm">
                          <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">Просмотр</span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${usedMaterial.id}/edit`} color="primary" size="sm">
                          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Редактировать</span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${usedMaterial.id}/delete`} color="danger" size="sm">
                          <FontAwesomeIcon icon="trash" /> <span className="d-none d-md-inline">Удалить</span>
                        </Button>
                      </div>
                    </td>
                  </tr>
                ))}
              </tbody>
            </Table>
          ) : (
            <div className="alert alert-warning">Не найдено ни одного использованного материала</div>
          )}
        </div>
        <div className={usedMaterialList && usedMaterialList.length > 0 ? '' : 'd-none'}>
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

const mapStateToProps = ({ usedMaterial }: IRootState) => ({
  usedMaterialList: usedMaterial.entities,
  totalItems: usedMaterial.totalItems
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(UsedMaterial);
