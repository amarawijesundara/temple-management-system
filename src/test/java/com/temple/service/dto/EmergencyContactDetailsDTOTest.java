package com.temple.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.temple.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EmergencyContactDetailsDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EmergencyContactDetailsDTO.class);
        EmergencyContactDetailsDTO emergencyContactDetailsDTO1 = new EmergencyContactDetailsDTO();
        emergencyContactDetailsDTO1.setId(1L);
        EmergencyContactDetailsDTO emergencyContactDetailsDTO2 = new EmergencyContactDetailsDTO();
        assertThat(emergencyContactDetailsDTO1).isNotEqualTo(emergencyContactDetailsDTO2);
        emergencyContactDetailsDTO2.setId(emergencyContactDetailsDTO1.getId());
        assertThat(emergencyContactDetailsDTO1).isEqualTo(emergencyContactDetailsDTO2);
        emergencyContactDetailsDTO2.setId(2L);
        assertThat(emergencyContactDetailsDTO1).isNotEqualTo(emergencyContactDetailsDTO2);
        emergencyContactDetailsDTO1.setId(null);
        assertThat(emergencyContactDetailsDTO1).isNotEqualTo(emergencyContactDetailsDTO2);
    }
}
