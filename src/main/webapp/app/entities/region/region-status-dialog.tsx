import React from 'react';
import { connect } from 'react-redux';
import { RouteComponentProps } from 'react-router-dom';
import { Button, Modal, ModalBody, ModalFooter, ModalHeader } from 'reactstrap';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';
import { changeStatusEntity, getEntity } from './region.reducer';
import { RegionStatus } from "app/shared/model/enumerations/region-status.model";

export interface IRegionStatusDialogProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class RegionStatusDialog extends React.Component<IRegionStatusDialogProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  confirmChangeStatus = event => {
    this.props.changeStatusEntity(this.props.regionEntity);
    this.handleClose(event);
  };

  handleClose = event => {
    event.stopPropagation();
    this.props.history.goBack();
  };

  render() {
    const { regionEntity } = this.props;
    const action = regionEntity.status === RegionStatus.ACTIVATED ? 'Деактивировать' : 'Активировать';
    const actionHeader = regionEntity.status === RegionStatus.ACTIVATED ? 'деактивацию' : 'активацию';
    return (
      <Modal isOpen toggle={this.handleClose}>
        <ModalHeader toggle={this.handleClose}>Подтвердите {actionHeader}</ModalHeader>
        <ModalBody id="salonApp.region.changeStatus.question">Вы уверены, что хотите {action.toLowerCase()} этот регион {regionEntity.name}?</ModalBody>
        <ModalFooter>
          <Button color="secondary" onClick={this.handleClose}>
            <FontAwesomeIcon icon="ban" />
            &nbsp; Отменить
          </Button>
          <Button id="jhi-confirm-change-status-region" color="primary" onClick={this.confirmChangeStatus}>
            <FontAwesomeIcon icon="sync" />
            &nbsp; {action}
          </Button>
        </ModalFooter>
      </Modal>
    );
  }
}

const mapStateToProps = ({ region }: IRootState) => ({
  regionEntity: region.entity
});

const mapDispatchToProps = { getEntity, changeStatusEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(RegionStatusDialog);
