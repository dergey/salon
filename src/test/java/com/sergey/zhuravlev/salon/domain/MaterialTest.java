package com.sergey.zhuravlev.salon.domain;

import com.sergey.zhuravlev.salon.controller.TestUtil;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class MaterialTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Material.class);
        Material material1 = new Material();
        material1.setId(1L);
        Material material2 = new Material();
        material2.setId(material1.getId());
        assertThat(material1).isEqualTo(material2);
        material2.setId(2L);
        assertThat(material1).isNotEqualTo(material2);
        material1.setId(null);
        assertThat(material1).isNotEqualTo(material2);
    }
}
