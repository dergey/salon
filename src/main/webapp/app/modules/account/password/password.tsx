import React, { useState, useEffect } from 'react';

import { connect } from 'react-redux';
import { AvForm, AvField } from 'availity-reactstrap-validation';
import { Row, Col, Button } from 'reactstrap';

import { IRootState } from 'app/shared/reducers';
import { getSession } from 'app/shared/reducers/authentication';
import PasswordStrengthBar from 'app/shared/layout/password/password-strength-bar';
import { savePassword, reset } from './password.reducer';

export interface IUserPasswordProps extends StateProps, DispatchProps {}

export const PasswordPage = (props: IUserPasswordProps) => {
  const [password, setPassword] = useState('');

  useEffect(() => {
    props.reset();
    props.getSession();
    return () => props.reset();
  }, []);

  const handleValidSubmit = (event, values) => {
    props.savePassword(values.currentPassword, values.newPassword);
  };

  const updatePassword = event => setPassword(event.target.value);

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="password-title">Пароль для {props.account.login}</h2>
          <AvForm id="password-form" onValidSubmit={handleValidSubmit}>
            <AvField
              name="currentPassword"
              label="Текущий пароль"
              placeholder={'Текущий пароль'}
              type="password"
              validate={{
                required: { value: true, errorMessage: 'Текущий пароль является обязательным.' }
              }}
            />
            <AvField
              name="newPassword"
              label="Новый пароль"
              placeholder={'Новый пароль'}
              type="password"
              validate={{
                required: { value: true, errorMessage: 'Новый пароль является обязательным.' },
                minLength: { value: 4, errorMessage: 'Ваш пароль должен состоять не менее чем из 4-х символов.' },
                maxLength: { value: 50, errorMessage: 'Ваш пароль не может быть длиннее 50 символов.' }
              }}
              onChange={updatePassword}
            />
            <PasswordStrengthBar password={password} />
            <AvField
              name="confirmPassword"
              label="Подтверждение нового пароля"
              placeholder="Подтвердите новый пароль"
              type="password"
              validate={{
                required: {
                  value: true,
                  errorMessage: 'Подтверждение пароля обязательно.'
                },
                minLength: {
                  value: 4,
                  errorMessage: 'Подтверждение пароля должено состоять не менее чем из 4-х символов.'
                },
                maxLength: {
                  value: 50,
                  errorMessage: 'Подтверждение пароля не может быть длиннее 50 символов.'
                },
                match: {
                  value: 'newPassword',
                  errorMessage: 'Пароль и его подтверждение не совпадают!'
                }
              }}
            />
            <Button color="success" type="submit">
              Сохранить
            </Button>
          </AvForm>
        </Col>
      </Row>
    </div>
  );
};

const mapStateToProps = ({ authentication }: IRootState) => ({
  account: authentication.account,
  isAuthenticated: authentication.isAuthenticated
});

const mapDispatchToProps = { getSession, savePassword, reset };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(PasswordPage);
