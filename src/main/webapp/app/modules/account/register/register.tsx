import React, { useState, useEffect } from 'react';

import { connect } from 'react-redux';
import { AvForm, AvField } from 'availity-reactstrap-validation';
import { Row, Col, Alert, Button } from 'reactstrap';

import PasswordStrengthBar from 'app/shared/layout/password/password-strength-bar';
import { IRootState } from 'app/shared/reducers';
import { handleRegister, reset } from './register.reducer';

export type IRegisterProps = DispatchProps;

export const RegisterPage = (props: IRegisterProps) => {
  const [password, setPassword] = useState('');

  useEffect(() => () => props.reset(), []);

  const handleValidSubmit = (event, values) => {
    props.handleRegister(values.username, values.email, values.firstPassword);
    event.preventDefault();
  };

  const updatePassword = event => setPassword(event.target.value);

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h1 id="register-title">Регистрация</h1>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          <AvForm id="register-form" onValidSubmit={handleValidSubmit}>
            <AvField
              name="username"
              label="Имя пользователя"
              placeholder={'Ваше имя пользователя'}
              validate={{
                required: { value: true, errorMessage: 'Имя пользователя необходимое поле.' },
                pattern: { value: '^[_.@A-Za-z0-9-]*$', errorMessage: 'Имя пользователя может содержать только буквы и цифры.' },
                minLength: { value: 1, errorMessage: 'Имя пользователя должно состоять менее чем из 1 символа.' },
                maxLength: { value: 50, errorMessage: 'Имя пользователя не должно превышать 50 символов.' }
              }}
            />
            <AvField
              name="email"
              label="Email"
              placeholder={'Ваш email'}
              type="email"
              validate={{
                required: { value: true, errorMessage: 'Email необходимое поле.' },
                minLength: { value: 5, errorMessage: 'Ваш адрес электронной почты должен содержать не менее 5 символов.' },
                maxLength: { value: 254, errorMessage: 'Длина сообщения электронной почты не должна превышать 50 символов.' }
              }}
            />
            <AvField
              name="firstPassword"
              label="Пароль"
              placeholder={'Новый пароль'}
              type="password"
              onChange={updatePassword}
              validate={{
                required: { value: true, errorMessage: 'Пароль необходимое поле.' },
                minLength: { value: 4, errorMessage: 'Ваш пароль должен состоять не менее чем из 4 символов.' },
                maxLength: { value: 50, errorMessage: 'Ваш пароль не должен превышать 50 символов.' }
              }}
            />
            <PasswordStrengthBar password={password} />
            <AvField
              name="secondPassword"
              label="Подтверждение пароля"
              placeholder="Подтверждение пароля"
              type="password"
              validate={{
                required: { value: true, errorMessage: 'Повторение пароля необходимое поле.' },
                minLength: { value: 4, errorMessage: 'Ваш подтверждающий пароль должен состоять не менее чем из 4 символов.' },
                maxLength: { value: 50, errorMessage: 'Ваш подтверждающий пароль не должен превышать 50 символов.' },
                match: { value: 'firstPassword', errorMessage: 'Пароль и его подтверждение не совпадают!' }
              }}
            />
            <Button id="register-submit" color="primary" type="submit">
              Зарегистрироваться
            </Button>
          </AvForm>
          <p>&nbsp;</p>
          <Alert color="warning">
            <span>Если вы хотите</span>
            <a className="alert-link"> войти</a>
            <span>
              , вы можете попробовать учетные записи по умолчанию:
              <br />- Администратор (логин=&quot;admin&quot; и пароль=&quot;admin&quot;)
              <br />- Пользователь (логин=&quot;user&quot; и пароль=&quot;user&quot;).
            </span>
          </Alert>
        </Col>
      </Row>
    </div>
  );
};

const mapDispatchToProps = { handleRegister, reset };
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  null,
  mapDispatchToProps
)(RegisterPage);
