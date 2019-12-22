import './home.scss';

import React from 'react';
import { Link } from 'react-router-dom';

import { connect } from 'react-redux';
import { Row, Col, Alert } from 'reactstrap';

export type IHomeProp = StateProps;

export const Home = (props: IHomeProp) => {
  const { account } = props;

  return (
    <Row>
      <Col>
        {account && account.login ? (
          <div>
            <Alert color="success">Вы вошли в систему как {account.login}.</Alert>
          </div>
        ) : (
          <div>
            <Alert color="warning">
              Если вы хотите
              <Link to="/login" className="alert-link">
                {' '}
                войти
              </Link>
              , вы можете попробовать учетные записи по умолчанию:
              <br />- Администратора (логин=&quot;admin&quot; и пароль=&quot;admin&quot;)
              <br />- Пользователя (логин=&quot;user&quot; и пароль=&quot;user&quot;).
            </Alert>

            <Alert color="warning">
              У вас еще нет аккаунта?&nbsp;
              <Link to="/account/register" className="alert-link">
                Зарегистрируйте новую учетную запись
              </Link>
            </Alert>
          </div>
        )}
      </Col>
    </Row>
  );
};

const mapStateToProps = storeState => ({
  account: storeState.authentication.account,
  isAuthenticated: storeState.authentication.isAuthenticated
});

type StateProps = ReturnType<typeof mapStateToProps>;

export default connect(mapStateToProps)(Home);
