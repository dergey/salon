import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Label, Row, Col } from 'reactstrap';
import { AvForm, AvGroup, AvInput, AvField, AvFeedback } from 'availity-reactstrap-validation';

import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getUser, getRoles, updateUser, createUser, reset } from './user-management.reducer';
import { IRootState } from 'app/shared/reducers';

export interface IUserManagementUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ login: string }> {}

export const UserManagementUpdate = (props: IUserManagementUpdateProps) => {
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.login);

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getUser(props.match.params.login);
    }
    props.getRoles();
    return () => props.reset();
  }, []);

  const handleClose = () => {
    props.history.push('/admin/user-management');
  };

  const saveUser = (event, values) => {
    if (isNew) {
      props.createUser(values);
    } else {
      props.updateUser(values);
    }
    handleClose();
  };

  const isInvalid = false;
  const { user, loading, updating, roles } = props;

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h1>Создать или отредактировать пользователя</h1>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Загрузка...</p>
          ) : (
            <AvForm onValidSubmit={saveUser}>
              {user.id ? (
                <AvGroup>
                  <Label for="id">Номер</Label>
                  <AvField type="text" className="form-control" name="id" required readOnly value={user.id} />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label for="login">Имя пользователя</Label>
                <AvField
                  type="text"
                  className="form-control"
                  name="login"
                  validate={{
                    required: {
                      value: true,
                      errorMessage: 'Это поле не может быть пустым.'
                    },
                    pattern: {
                      value: '^[_.@A-Za-z0-9-]*$',
                      errorMessage: 'Это поле может содержать только буквы и цифры.'
                    },
                    minLength: {
                      value: 1,
                      errorMessage: 'Это поле должно быть не менее 1 символа.'
                    },
                    maxLength: {
                      value: 50,
                      errorMessage: 'Это поле не может быть больше 50 символов.'
                    }
                  }}
                  value={user.login}
                />
              </AvGroup>
              <AvGroup>
                <AvField
                  name="email"
                  label="Email"
                  placeholder={'Ваш email'}
                  type="email"
                  validate={{
                    required: {
                      value: true,
                      errorMessage: 'Это поле не может быть пустым.'
                    },
                    email: {
                      errorMessage: 'Это поле должно быть email-ом.'
                    },
                    minLength: {
                      value: 5,
                      errorMessage: 'Это поле должно быть более 5 символов.'
                    },
                    maxLength: {
                      value: 254,
                      errorMessage: 'Это поле не может быть длиннее 50 символов.'
                    }
                  }}
                  value={user.email}
                />
              </AvGroup>
              <AvGroup check>
                <Label>
                  <AvInput type="checkbox" name="activated" value={user.activated} /> Активен
                </Label>
              </AvGroup>
              <AvGroup>
                <Label for="authorities">Профили</Label>
                <AvInput type="select" className="form-control" name="authorities" value={user.authorities} multiple>
                  {roles.map(role => (
                    <option value={role} key={role}>
                      {role}
                    </option>
                  ))}
                </AvInput>
              </AvGroup>
              <Button tag={Link} to="/admin/user-management" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">Назад</span>
              </Button>
              &nbsp;
              <Button color="primary" type="submit" disabled={isInvalid || updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp; Сохранить
              </Button>
            </AvForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

const mapStateToProps = (storeState: IRootState) => ({
  user: storeState.userManagement.user,
  roles: storeState.userManagement.authorities,
  loading: storeState.userManagement.loading,
  updating: storeState.userManagement.updating
});

const mapDispatchToProps = { getUser, getRoles, updateUser, createUser, reset };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(UserManagementUpdate);
