package com.sergey.zhuravlev.salon.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.sergey.zhuravlev.salon.web.rest.TestUtil;

public class UsedMaterialTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UsedMaterial.class);
        UsedMaterial usedMaterial1 = new UsedMaterial();
        usedMaterial1.setId(1L);
        UsedMaterial usedMaterial2 = new UsedMaterial();
        usedMaterial2.setId(usedMaterial1.getId());
        assertThat(usedMaterial1).isEqualTo(usedMaterial2);
        usedMaterial2.setId(2L);
        assertThat(usedMaterial1).isNotEqualTo(usedMaterial2);
        usedMaterial1.setId(null);
        assertThat(usedMaterial1).isNotEqualTo(usedMaterial2);
    }
}
