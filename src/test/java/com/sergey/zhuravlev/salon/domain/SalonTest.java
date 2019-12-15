package com.sergey.zhuravlev.salon.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.sergey.zhuravlev.salon.web.rest.TestUtil;

public class SalonTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Salon.class);
        Salon salon1 = new Salon();
        salon1.setId(1L);
        Salon salon2 = new Salon();
        salon2.setId(salon1.getId());
        assertThat(salon1).isEqualTo(salon2);
        salon2.setId(2L);
        assertThat(salon1).isNotEqualTo(salon2);
        salon1.setId(null);
        assertThat(salon1).isNotEqualTo(salon2);
    }
}
