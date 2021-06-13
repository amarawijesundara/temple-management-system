package com.temple.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.temple.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class GuardianDetailsDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(GuardianDetailsDTO.class);
        GuardianDetailsDTO guardianDetailsDTO1 = new GuardianDetailsDTO();
        guardianDetailsDTO1.setId(1L);
        GuardianDetailsDTO guardianDetailsDTO2 = new GuardianDetailsDTO();
        assertThat(guardianDetailsDTO1).isNotEqualTo(guardianDetailsDTO2);
        guardianDetailsDTO2.setId(guardianDetailsDTO1.getId());
        assertThat(guardianDetailsDTO1).isEqualTo(guardianDetailsDTO2);
        guardianDetailsDTO2.setId(2L);
        assertThat(guardianDetailsDTO1).isNotEqualTo(guardianDetailsDTO2);
        guardianDetailsDTO1.setId(null);
        assertThat(guardianDetailsDTO1).isNotEqualTo(guardianDetailsDTO2);
    }
}
