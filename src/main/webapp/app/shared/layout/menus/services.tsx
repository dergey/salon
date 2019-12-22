import React from 'react';
import MenuItem from 'app/shared/layout/menus/menu-item';
import { DropdownItem } from 'reactstrap';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { NavLink as Link } from 'react-router-dom';
import { NavDropdown } from './menu-components';

export const ServicesMenu = props => (
  <NavDropdown icon="concierge-bell" name="Услуги" id="entity-menu">
    <MenuItem icon="asterisk" to="/service">
      Услуги
    </MenuItem>
    <MenuItem icon="asterisk" to="/service-provided">
      Предоставленные услуги
    </MenuItem>
    <MenuItem icon="asterisk" to="/order">
      Заказы
    </MenuItem>
    <MenuItem icon="asterisk" to="/client">
      Клиенты
    </MenuItem>
    {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
  </NavDropdown>
);
