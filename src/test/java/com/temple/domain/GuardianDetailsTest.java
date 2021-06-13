package com.temple.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.temple.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class GuardianDetailsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(GuardianDetails.class);
        GuardianDetails guardianDetails1 = new GuardianDetails();
        guardianDetails1.setId(1L);
        GuardianDetails guardianDetails2 = new GuardianDetails();
        guardianDetails2.setId(guardianDetails1.getId());
        assertThat(guardianDetails1).isEqualTo(guardianDetails2);
        guardianDetails2.setId(2L);
        assertThat(guardianDetails1).isNotEqualTo(guardianDetails2);
        guardianDetails1.setId(null);
        assertThat(guardianDetails1).isNotEqualTo(guardianDetails2);
    }
}
