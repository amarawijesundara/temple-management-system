package com.temple.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.temple.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EmergencyContactDetailsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EmergencyContactDetails.class);
        EmergencyContactDetails emergencyContactDetails1 = new EmergencyContactDetails();
        emergencyContactDetails1.setId(1L);
        EmergencyContactDetails emergencyContactDetails2 = new EmergencyContactDetails();
        emergencyContactDetails2.setId(emergencyContactDetails1.getId());
        assertThat(emergencyContactDetails1).isEqualTo(emergencyContactDetails2);
        emergencyContactDetails2.setId(2L);
        assertThat(emergencyContactDetails1).isNotEqualTo(emergencyContactDetails2);
        emergencyContactDetails1.setId(null);
        assertThat(emergencyContactDetails1).isNotEqualTo(emergencyContactDetails2);
    }
}
