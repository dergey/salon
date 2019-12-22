import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './used-material.reducer';
import { IUsedMaterial } from 'app/shared/model/used-material.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IUsedMaterialDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class UsedMaterialDetail extends React.Component<IUsedMaterialDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { usedMaterialEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            Использованный материал <b>№{usedMaterialEntity.id}</b>
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="count">Количество</span>
            </dt>
            <dd>{usedMaterialEntity.count}</dd>
            <dt>
              <span id="decommission">Списание</span>
            </dt>
            <dd>{usedMaterialEntity.decommission ? 'да' : 'нет'}</dd>
            {usedMaterialEntity.order ? (
              <>
                <dt>Заказ</dt>
                <dd>{usedMaterialEntity.order.id}</dd>
              </>) : ''}
            <dt>Материал</dt>
            <dd>{usedMaterialEntity.material ? usedMaterialEntity.material.title : ''}</dd>
            <dt>Сотрудник</dt>
            <dd>{usedMaterialEntity.employee ? usedMaterialEntity.employee.lastName + ' '  + usedMaterialEntity.employee.firstName : ''}</dd>
          </dl>
          <Button tag={Link} to="/used-material" replace color="info">
            <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Назад</span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/used-material/${usedMaterialEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Редактировать</span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ usedMaterial }: IRootState) => ({
  usedMaterialEntity: usedMaterial.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(UsedMaterialDetail);
