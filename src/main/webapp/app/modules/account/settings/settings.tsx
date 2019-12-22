import React, { useEffect } from 'react';
import { Button, Col, Alert, Row } from 'reactstrap';
import { connect } from 'react-redux';

import { AvForm, AvField } from 'availity-reactstrap-validation';

import { IRootState } from 'app/shared/reducers';
import { getSession } from 'app/shared/reducers/authentication';
import { saveAccountSettings, reset } from './settings.reducer';

export interface IUserSettingsProps extends StateProps, DispatchProps {}

export const SettingsPage = (props: IUserSettingsProps) => {
  useEffect(() => {
    props.getSession();
    return () => {
      props.reset();
    };
  }, []);

  const handleValidSubmit = (event, values) => {
    const account = {
      ...props.account,
      ...values
    };

    props.saveAccountSettings(account);
    event.persist();
  };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="settings-title">Настройки профиля для {props.account.login}</h2>
          <AvForm id="settings-form" onValidSubmit={handleValidSubmit}>
            {/* Email */}
            <AvField
              name="email"
              label="Email"
              placeholder={'Ваш email'}
              type="email"
              validate={{
                required: { value: true, errorMessage: 'Ваш email является обязательным.' },
                minLength: { value: 5, errorMessage: 'Ваш email должен быть не менее 5 символов.' },
                maxLength: { value: 50, errorMessage: 'Ваш email не может быть длиннее 50 символов.' }
              }}
              value={props.account.email}
            />
            <Button color="primary" type="submit">
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

const mapDispatchToProps = { getSession, saveAccountSettings, reset };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(SettingsPage);
