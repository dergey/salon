import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './specialization.reducer';
import { ISpecialization } from 'app/shared/model/specialization.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ISpecializationDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class SpecializationDetail extends React.Component<ISpecializationDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { specializationEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            Specialization [<b>{specializationEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="note">Note</span>
            </dt>
            <dd>{specializationEntity.note}</dd>
            <dt>Employee</dt>
            <dd>{specializationEntity.employee ? specializationEntity.employee.lastName : ''}</dd>
            <dt>Service</dt>
            <dd>{specializationEntity.service ? specializationEntity.service.title : ''}</dd>
          </dl>
          <Button tag={Link} to="/specialization" replace color="info">
            <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/specialization/${specializationEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ specialization }: IRootState) => ({
  specializationEntity: specialization.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(SpecializationDetail);
