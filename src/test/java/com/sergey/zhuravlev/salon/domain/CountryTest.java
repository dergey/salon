package com.sergey.zhuravlev.salon.domain;

import com.sergey.zhuravlev.salon.controller.TestUtil;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CountryTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Country.class);
        Country country1 = new Country();
        country1.setCode("US");
        Country country2 = new Country();
        country2.setCode("GB");
        assertThat(country1).isEqualTo(country2);
        country2.setCode("BY");
        assertThat(country1).isNotEqualTo(country2);
        country1.setCode(null);
        assertThat(country1).isNotEqualTo(country2);
    }
}
