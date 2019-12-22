import React from 'react';
import MenuItem from 'app/shared/layout/menus/menu-item';
import {DropdownItem} from 'reactstrap';
import {FontAwesomeIcon} from '@fortawesome/react-fontawesome';

import {NavLink as Link} from 'react-router-dom';
import {NavDropdown} from './menu-components';

export const EmployeesMenu = props => (
  <NavDropdown icon="user-tie" name="Сотрудники" id="entity-menu">
    <MenuItem icon="asterisk" to="/employee">
      Сотрудники
    </MenuItem>
    <MenuItem icon="asterisk" to="/specialization">
      Специализация
    </MenuItem>
  </NavDropdown>
);
