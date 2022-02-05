package com.sergey.zhuravlev.salon.domain;

import com.sergey.zhuravlev.salon.controller.TestUtil;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class MaterialInSalonTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MaterialInSalon.class);
        MaterialInSalon materialInSalon1 = new MaterialInSalon();
        materialInSalon1.setId(1L);
        MaterialInSalon materialInSalon2 = new MaterialInSalon();
        materialInSalon2.setId(materialInSalon1.getId());
        assertThat(materialInSalon1).isEqualTo(materialInSalon2);
        materialInSalon2.setId(2L);
        assertThat(materialInSalon1).isNotEqualTo(materialInSalon2);
        materialInSalon1.setId(null);
        assertThat(materialInSalon1).isNotEqualTo(materialInSalon2);
    }
}
