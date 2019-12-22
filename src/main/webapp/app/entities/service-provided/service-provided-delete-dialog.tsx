import React from 'react';
import { connect } from 'react-redux';
import { RouteComponentProps } from 'react-router-dom';
import { Modal, ModalHeader, ModalBody, ModalFooter, Button } from 'reactstrap';
import { ICrudGetAction, ICrudDeleteAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IServiceProvided } from 'app/shared/model/service-provided.model';
import { IRootState } from 'app/shared/reducers';
import { getEntity, deleteEntity } from './service-provided.reducer';

export interface IServiceProvidedDeleteDialogProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class ServiceProvidedDeleteDialog extends React.Component<IServiceProvidedDeleteDialogProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  confirmDelete = event => {
    this.props.deleteEntity(this.props.serviceProvidedEntity.id);
    this.handleClose(event);
  };

  handleClose = event => {
    event.stopPropagation();
    this.props.history.goBack();
  };

  render() {
    const { serviceProvidedEntity } = this.props;
    return (
      <Modal isOpen toggle={this.handleClose}>
        <ModalHeader toggle={this.handleClose}>Подтвердите удаление</ModalHeader>
        <ModalBody id="salonApp.serviceProvided.delete.question">Вы уверены, что хотите удалить предоставленную услугу?</ModalBody>
        <ModalFooter>
          <Button color="secondary" onClick={this.handleClose}>
            <FontAwesomeIcon icon="ban" />
            &nbsp; Отмена
          </Button>
          <Button id="jhi-confirm-delete-serviceProvided" color="danger" onClick={this.confirmDelete}>
            <FontAwesomeIcon icon="trash" />
            &nbsp; Удалить
          </Button>
        </ModalFooter>
      </Modal>
    );
  }
}

const mapStateToProps = ({ serviceProvided }: IRootState) => ({
  serviceProvidedEntity: serviceProvided.entity
});

const mapDispatchToProps = { getEntity, deleteEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(ServiceProvidedDeleteDialog);
