import React from 'react';
import MenuItem from 'app/shared/layout/menus/menu-item';
import { DropdownItem } from 'reactstrap';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { NavLink as Link } from 'react-router-dom';
import { NavDropdown } from './menu-components';

export const EntitiesMenu = props => (
  <NavDropdown icon="th-list" name="Entities" id="entity-menu">
    <MenuItem icon="asterisk" to="/region">
      Region
    </MenuItem>
    <MenuItem icon="asterisk" to="/country">
      Country
    </MenuItem>
    <MenuItem icon="asterisk" to="/location">
      Location
    </MenuItem>
    <MenuItem icon="asterisk" to="/salon">
      Salon
    </MenuItem>
    <MenuItem icon="asterisk" to="/employee">
      Employee
    </MenuItem>
    <MenuItem icon="asterisk" to="/service">
      Service
    </MenuItem>
    <MenuItem icon="asterisk" to="/order">
      Order
    </MenuItem>
    <MenuItem icon="asterisk" to="/service-provided">
      Service Provided
    </MenuItem>
    <MenuItem icon="asterisk" to="/material">
      Material
    </MenuItem>
    <MenuItem icon="asterisk" to="/material-in-salon">
      Material In Salon
    </MenuItem>
    <MenuItem icon="asterisk" to="/used-material">
      Used Material
    </MenuItem>
    <MenuItem icon="asterisk" to="/specialization">
      Specialization
    </MenuItem>
    <MenuItem icon="asterisk" to="/client">
      Client
    </MenuItem>
    {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
  </NavDropdown>
);
