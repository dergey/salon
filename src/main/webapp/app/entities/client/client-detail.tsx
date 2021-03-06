import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './client.reducer';
import { IClient } from 'app/shared/model/client.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IClientDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class ClientDetail extends React.Component<IClientDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { clientEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            Клиент <b>№{clientEntity.id}</b>
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="firstName">Имя</span>
            </dt>
            <dd>{clientEntity.firstName}</dd>
            <dt>
              <span id="lastName">Фамилия</span>
            </dt>
            <dd>{clientEntity.lastName}</dd>
            <dt>
              <span id="email">Email</span>
            </dt>
            <dd>{clientEntity.email}</dd>
            <dt>
              <span id="phoneNumber">Телефон</span>
            </dt>
            <dd>{clientEntity.phoneNumber}</dd>
            <dt>
              <span id="sex">Пол</span>
            </dt>
            <dd>{clientEntity.sex === "MAN" ? "муж" : clientEntity.sex === "WOMAN" ? "жен" : ""}</dd>
          </dl>
          <Button tag={Link} to="/client" replace color="info">
            <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Назад</span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/client/${clientEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Редактировать</span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ client }: IRootState) => ({
  clientEntity: client.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(ClientDetail);
