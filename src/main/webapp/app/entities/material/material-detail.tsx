import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './material.reducer';
import { IMaterial } from 'app/shared/model/material.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IMaterialDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class MaterialDetail extends React.Component<IMaterialDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { materialEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            Материал <b>№{materialEntity.id}</b>
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="title">Название</span>
            </dt>
            <dd>{materialEntity.title}</dd>
            <dt>
              <span id="unit">Единица</span>
            </dt>
            <dd>{materialEntity.unit}</dd>
            <dt>
              <span id="price">Цена</span>
            </dt>
            <dd>{materialEntity.price}</dd>
          </dl>
          <Button tag={Link} to="/material" replace color="info">
            <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Назад</span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/material/${materialEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Редактировать</span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ material }: IRootState) => ({
  materialEntity: material.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(MaterialDetail);
