import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './material-in-salon.reducer';
import { IMaterialInSalon } from 'app/shared/model/material-in-salon.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IMaterialInSalonDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class MaterialInSalonDetail extends React.Component<IMaterialInSalonDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { materialInSalonEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            Материал в салоне <b>№{materialInSalonEntity.id}</b>
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="count">Количество</span>
            </dt>
            <dd>{materialInSalonEntity.count}</dd>
            <dt>Материал</dt>
            <dd>{materialInSalonEntity.material ? materialInSalonEntity.material.title : ''}</dd>
            <dt>Салон</dt>
            <dd>{materialInSalonEntity.salon ? materialInSalonEntity.salon.title : ''}</dd>
          </dl>
          <Button tag={Link} to="/material-in-salon" replace color="info">
            <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Назад</span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/material-in-salon/${materialInSalonEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Редактировать</span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ materialInSalon }: IRootState) => ({
  materialInSalonEntity: materialInSalon.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(MaterialInSalonDetail);
