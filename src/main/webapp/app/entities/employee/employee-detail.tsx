import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './employee.reducer';
import { APP_DATE_FORMAT } from 'app/config/constants';

export interface IEmployeeDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class EmployeeDetail extends React.Component<IEmployeeDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { employeeEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            Сотрудник <b>№{employeeEntity.id}</b>
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="firstName">Имя</span>
            </dt>
            <dd>{employeeEntity.firstName}</dd>
            <dt>
              <span id="lastName">Фамилия</span>
            </dt>
            <dd>{employeeEntity.lastName}</dd>
            <dt>
              <span id="email">Email</span>
            </dt>
            <dd>{employeeEntity.email}</dd>
            <dt>
              <span id="phoneNumber">Телефон</span>
            </dt>
            <dd>{employeeEntity.phoneNumber}</dd>
            <dt>
              <span id="hireDate">Дата найма</span>
            </dt>
            <dd>
              <TextFormat value={employeeEntity.hireDate} type="date" format={APP_DATE_FORMAT} />
            </dd>
            <dt>
              <span id="salary">Зарплата</span>
            </dt>
            <dd>{employeeEntity.salary}</dd>
            <dt>
              <span id="commissionPct">Коммисионные</span>
            </dt>
            <dd>{employeeEntity.commissionPct}</dd>
            <dt>Менеджер</dt>
            <dd>{employeeEntity.manager ? employeeEntity.manager.lastName + ' ' + employeeEntity.manager.firstName : ''}</dd>
            <dt>Парикмахерская</dt>
            <dd>{employeeEntity.salon ? employeeEntity.salon.title : ''}</dd>
          </dl>
          <Button tag={Link} to="/employee" replace color="info">
            <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Назад</span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/employee/${employeeEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Редактировать</span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/employee/${employeeEntity.id}/schedule`} replace color="primary">
            <FontAwesomeIcon icon="calendar-alt" /> <span className="d-none d-md-inline">Расписание</span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ employee }: IRootState) => ({
  employeeEntity: employee.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(EmployeeDetail);
