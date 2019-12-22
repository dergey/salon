import React from 'react';
import { connect } from 'react-redux';
import { RouteComponentProps } from 'react-router-dom';
import { Modal, ModalHeader, ModalBody, ModalFooter, Button } from 'reactstrap';
import { ICrudGetAction, ICrudDeleteAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { ISalon } from 'app/shared/model/salon.model';
import { IRootState } from 'app/shared/reducers';
import { getEntity, deleteEntity } from './salon.reducer';

export interface ISalonDeleteDialogProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class SalonDeleteDialog extends React.Component<ISalonDeleteDialogProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  confirmDelete = event => {
    this.props.deleteEntity(this.props.salonEntity.id);
    this.handleClose(event);
  };

  handleClose = event => {
    event.stopPropagation();
    this.props.history.goBack();
  };

  render() {
    const { salonEntity } = this.props;
    return (
      <Modal isOpen toggle={this.handleClose}>
        <ModalHeader toggle={this.handleClose}>Подтвердите удаление</ModalHeader>
        <ModalBody id="salonApp.salon.delete.question">Вы уверены, что хотите удалить {salonEntity.title}?</ModalBody>
        <ModalFooter>
          <Button color="secondary" onClick={this.handleClose}>
            <FontAwesomeIcon icon="ban" />
            &nbsp; Отмена
          </Button>
          <Button id="jhi-confirm-delete-salon" color="danger" onClick={this.confirmDelete}>
            <FontAwesomeIcon icon="trash" />
            &nbsp; Удалить
          </Button>
        </ModalFooter>
      </Modal>
    );
  }
}

const mapStateToProps = ({ salon }: IRootState) => ({
  salonEntity: salon.entity
});

const mapDispatchToProps = { getEntity, deleteEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(SalonDeleteDialog);
