import React from 'react';
import { connect } from 'react-redux';
import { RouteComponentProps } from 'react-router-dom';
import { Modal, ModalHeader, ModalBody, ModalFooter, Button } from 'reactstrap';
import { ICrudGetAction, ICrudDeleteAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IUsedMaterial } from 'app/shared/model/used-material.model';
import { IRootState } from 'app/shared/reducers';
import { getEntity, deleteEntity } from './used-material.reducer';

export interface IUsedMaterialDeleteDialogProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class UsedMaterialDeleteDialog extends React.Component<IUsedMaterialDeleteDialogProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  confirmDelete = event => {
    this.props.deleteEntity(this.props.usedMaterialEntity.id);
    this.handleClose(event);
  };

  handleClose = event => {
    event.stopPropagation();
    this.props.history.goBack();
  };

  render() {
    const { usedMaterialEntity } = this.props;
    return (
      <Modal isOpen toggle={this.handleClose}>
        <ModalHeader toggle={this.handleClose}>Подтвердите удаление</ModalHeader>
        <ModalBody id="salonApp.usedMaterial.delete.question">Вы уверены, что хотите удалить использованный материал?</ModalBody>
        <ModalFooter>
          <Button color="secondary" onClick={this.handleClose}>
            <FontAwesomeIcon icon="ban" />
            &nbsp; Отмена
          </Button>
          <Button id="jhi-confirm-delete-usedMaterial" color="danger" onClick={this.confirmDelete}>
            <FontAwesomeIcon icon="trash" />
            &nbsp; Удалить
          </Button>
        </ModalFooter>
      </Modal>
    );
  }
}

const mapStateToProps = ({ usedMaterial }: IRootState) => ({
  usedMaterialEntity: usedMaterial.entity
});

const mapDispatchToProps = { getEntity, deleteEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(UsedMaterialDeleteDialog);
