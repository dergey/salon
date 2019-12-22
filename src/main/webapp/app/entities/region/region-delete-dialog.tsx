import React from 'react';
import { connect } from 'react-redux';
import { RouteComponentProps } from 'react-router-dom';
import { Modal, ModalHeader, ModalBody, ModalFooter, Button } from 'reactstrap';
import { ICrudGetAction, ICrudDeleteAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRegion } from 'app/shared/model/region.model';
import { IRootState } from 'app/shared/reducers';
import { getEntity, deleteEntity } from './region.reducer';

export interface IRegionDeleteDialogProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class RegionDeleteDialog extends React.Component<IRegionDeleteDialogProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  confirmDelete = event => {
    this.props.deleteEntity(this.props.regionEntity.id);
    this.handleClose(event);
  };

  handleClose = event => {
    event.stopPropagation();
    this.props.history.goBack();
  };

  render() {
    const { regionEntity } = this.props;
    return (
      <Modal isOpen toggle={this.handleClose}>
        <ModalHeader toggle={this.handleClose}>Подтвердите удаление</ModalHeader>
        <ModalBody id="salonApp.region.delete.question">Вы уверены, что хотите удалить этот регион {regionEntity.regionName}?</ModalBody>
        <ModalFooter>
          <Button color="secondary" onClick={this.handleClose}>
            <FontAwesomeIcon icon="ban" />
            &nbsp; Отменить
          </Button>
          <Button id="jhi-confirm-delete-region" color="danger" onClick={this.confirmDelete}>
            <FontAwesomeIcon icon="trash" />
            &nbsp; Удалить
          </Button>
        </ModalFooter>
      </Modal>
    );
  }
}

const mapStateToProps = ({ region }: IRootState) => ({
  regionEntity: region.entity
});

const mapDispatchToProps = { getEntity, deleteEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(RegionDeleteDialog);
