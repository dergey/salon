import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Card, CardBody, CardText, CardTitle, Col, Row, Table } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntityScheduler } from './employee.reducer';
import {APP_LOCAL_DATE_FORMAT, APP_LOCAL_TIME_FORMAT} from 'app/config/constants';
import { DayOfWeek } from "app/shared/model/enumerations/dayofweek.model";
import {ServiceProvided} from "app/entities/service-provided/service-provided";
import {IServiceProvided} from "app/shared/model/service-provided.model";

export interface IEmployeeScheduleProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class EmployeeSchedule extends React.Component<IEmployeeScheduleProps> {

  componentDidMount() {
    this.props.getEntityScheduler(this.props.match.params.id);
  }

  render() {
    const { scheduleEntity } = this.props;

    const scheduleTable = scheduleEntity.schedule ? Object.keys(DayOfWeek).map((dayOfWeek) => {

      if (Object.keys(scheduleEntity.schedule).find((element, index, array) => element === dayOfWeek)) {
        const dailySchedule = scheduleEntity.schedule[dayOfWeek];
        const dailyScheduleItems = dailySchedule.map((serviceProvided : IServiceProvided) => {
          return <Card key={serviceProvided.id}>
              <CardBody>
                <CardText>
                  <dl>
                    <dt>
                      Время:
                    </dt>
                    <dd>
                      <TextFormat value={serviceProvided.startDate} type="date" format={APP_LOCAL_TIME_FORMAT}/>
                      -
                      <TextFormat value={serviceProvided.endDate} type="date" format={APP_LOCAL_TIME_FORMAT}/>
                    </dd>
                    <dt>
                      Клиент:
                    </dt>
                    <dd>
                      <Link
                        to={`../../client/${serviceProvided.order.client.id}`}>{serviceProvided.order.client.firstName} {serviceProvided.order.client.lastName}</Link>

                    </dd>
                    <dt>
                      Услуга:
                    </dt>
                    <dd>
                      <Link to={`../../service/${serviceProvided.service.id}`}>{serviceProvided.service.title}</Link>
                    </dd>
                    <dt>
                      Заметка:
                    </dt>
                    <dd>
                      {serviceProvided.note}
                    </dd>
                  </dl>
                  <Button tag={Link} to={`/service-provided/${serviceProvided.id}/edit`} replace color="primary">
                    <FontAwesomeIcon icon="pencil-alt"/>
                  </Button>
                  &nbsp;
                  <Button tag={Link} to={`/service-provided/${serviceProvided.id}/delete`} color="danger">
                    <FontAwesomeIcon icon="trash"/>
                  </Button>
                </CardText>
              </CardBody>
            </Card>
        });

        return (
          <td>
            {dailyScheduleItems}
            <Card className="placeholder">
              <Button tag={Link} to={`/service-provided/new`} replace color="success">
                <FontAwesomeIcon icon="plus" />
              </Button>
            </Card>
          </td>)
      } else {
        return (
          <td>
            <Card className="placeholder">
              <Button tag={Link} to={`/service-provided/new`} replace color="success">
                <FontAwesomeIcon icon="plus" />
              </Button>
            </Card>
          </td>)
      }
    }) : '';

    return (
      <Row>
        <Col>
          <h2>
            Расписание&nbsp;
            {scheduleEntity ? <TextFormat value={scheduleEntity.startDate} type="date" format={APP_LOCAL_DATE_FORMAT} /> : ''}
            &nbsp;-&nbsp;
            {scheduleEntity ? <TextFormat value={scheduleEntity.endDate} type="date" format={APP_LOCAL_DATE_FORMAT} /> : ''}
          </h2>
          <Table className="schedule">
            <thead>
            <tr><th>Понедельник</th><th>Вторник</th><th>Среда</th><th>Четверг</th><th>Пятница</th><th>Суббота</th><th>Воскресенье</th></tr>
            </thead>
            <tbody>
            <tr>{scheduleTable}</tr>
            </tbody>
          </Table>

          <Button tag={Link} to={`/employee/${this.props.match.params.id}`} replace color="info">
            <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Назад</span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ employee }: IRootState) => ({
  scheduleEntity: employee.schedule
});

const mapDispatchToProps = { getEntityScheduler };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(EmployeeSchedule);

