package com.sergey.zhuravlev.salon.domain;

import com.sergey.zhuravlev.salon.controller.TestUtil;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ServiceProvidedTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ServiceProvided.class);
        ServiceProvided serviceProvided1 = new ServiceProvided();
        serviceProvided1.setId(1L);
        ServiceProvided serviceProvided2 = new ServiceProvided();
        serviceProvided2.setId(serviceProvided1.getId());
        assertThat(serviceProvided1).isEqualTo(serviceProvided2);
        serviceProvided2.setId(2L);
        assertThat(serviceProvided1).isNotEqualTo(serviceProvided2);
        serviceProvided1.setId(null);
        assertThat(serviceProvided1).isNotEqualTo(serviceProvided2);
    }
}
