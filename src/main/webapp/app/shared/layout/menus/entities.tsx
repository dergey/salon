import React from 'react';
import MenuItem from 'app/shared/layout/menus/menu-item';
import { DropdownItem } from 'reactstrap';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { NavLink as Link } from 'react-router-dom';
import { NavDropdown } from './menu-components';

export const EntitiesMenu = props => (
  <NavDropdown icon="store-alt" name="Салоны" id="entity-menu">
    <MenuItem icon="asterisk" to="/region">
      Регионы
    </MenuItem>
    <MenuItem icon="asterisk" to="/country">
      Страны
    </MenuItem>
    <MenuItem icon="asterisk" to="/location">
      Адреса
    </MenuItem>
    <MenuItem icon="asterisk" to="/salon">
      Салоны
    </MenuItem>
    {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
  </NavDropdown>
);
