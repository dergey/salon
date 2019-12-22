import React from 'react';
import MenuItem from 'app/shared/layout/menus/menu-item';
import { DropdownItem } from 'reactstrap';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { NavLink as Link } from 'react-router-dom';
import { NavDropdown } from './menu-components';

export const MaterialsMenu = props => (
  <NavDropdown icon="cubes" name="Материалы" id="entity-menu">
    <MenuItem icon="asterisk" to="/material">
      Материалы
    </MenuItem>
    <MenuItem icon="asterisk" to="/material-in-salon">
      Материалы в салонах
    </MenuItem>
    <MenuItem icon="asterisk" to="/used-material">
      Использованные материалы
    </MenuItem>
  </NavDropdown>
);
