import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { ICrudGetAllAction, TextFormat, getSortState, IPaginationBaseState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './service-provided.reducer';
import { IServiceProvided } from 'app/shared/model/service-provided.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ITEMS_PER_PAGE } from 'app/shared/util/pagination.constants';

export interface IServiceProvidedProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export type IServiceProvidedState = IPaginationBaseState;

export class ServiceProvided extends React.Component<IServiceProvidedProps, IServiceProvidedState> {
  state: IServiceProvidedState = {
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
    const { serviceProvidedList, match, totalItems } = this.props;
    return (
      <div>
        <h2 id="service-provided-heading">
          Service Provideds
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Service Provided
          </Link>
        </h2>
        <div className="table-responsive">
          {serviceProvidedList && serviceProvidedList.length > 0 ? (
            <Table responsive aria-describedby="service-provided-heading">
              <thead>
                <tr>
                  <th className="hand" onClick={this.sort('id')}>
                    ID <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('startDate')}>
                    Start Date <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('endDate')}>
                    End Date <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('note')}>
                    Note <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    Order <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    Employee <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    Service <FontAwesomeIcon icon="sort" />
                  </th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {serviceProvidedList.map((serviceProvided, i) => (
                  <tr key={`entity-${i}`}>
                    <td>
                      <Button tag={Link} to={`${match.url}/${serviceProvided.id}`} color="link" size="sm">
                        {serviceProvided.id}
                      </Button>
                    </td>
                    <td>
                      <TextFormat type="date" value={serviceProvided.startDate} format={APP_DATE_FORMAT} />
                    </td>
                    <td>
                      <TextFormat type="date" value={serviceProvided.endDate} format={APP_DATE_FORMAT} />
                    </td>
                    <td>{serviceProvided.note}</td>
                    <td>{serviceProvided.order ? <Link to={`order/${serviceProvided.order.id}`}>{serviceProvided.order.id}</Link> : ''}</td>
                    <td>
                      {serviceProvided.employee ? (
                        <Link to={`employee/${serviceProvided.employee.id}`}>{serviceProvided.employee.lastName}</Link>
                      ) : (
                        ''
                      )}
                    </td>
                    <td>
                      {serviceProvided.service ? (
                        <Link to={`service/${serviceProvided.service.id}`}>{serviceProvided.service.title}</Link>
                      ) : (
                        ''
                      )}
                    </td>
                    <td className="text-right">
                      <div className="btn-group flex-btn-group-container">
                        <Button tag={Link} to={`${match.url}/${serviceProvided.id}`} color="info" size="sm">
                          <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${serviceProvided.id}/edit`} color="primary" size="sm">
                          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${serviceProvided.id}/delete`} color="danger" size="sm">
                          <FontAwesomeIcon icon="trash" /> <span className="d-none d-md-inline">Delete</span>
                        </Button>
                      </div>
                    </td>
                  </tr>
                ))}
              </tbody>
            </Table>
          ) : (
            <div className="alert alert-warning">No Service Provideds found</div>
          )}
        </div>
        <div className={serviceProvidedList && serviceProvidedList.length > 0 ? '' : 'd-none'}>
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

const mapStateToProps = ({ serviceProvided }: IRootState) => ({
  serviceProvidedList: serviceProvided.entities,
  totalItems: serviceProvided.totalItems
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(ServiceProvided);
