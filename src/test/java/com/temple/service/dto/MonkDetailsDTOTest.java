package com.temple.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.temple.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MonkDetailsDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MonkDetailsDTO.class);
        MonkDetailsDTO monkDetailsDTO1 = new MonkDetailsDTO();
        monkDetailsDTO1.setId(1L);
        MonkDetailsDTO monkDetailsDTO2 = new MonkDetailsDTO();
        assertThat(monkDetailsDTO1).isNotEqualTo(monkDetailsDTO2);
        monkDetailsDTO2.setId(monkDetailsDTO1.getId());
        assertThat(monkDetailsDTO1).isEqualTo(monkDetailsDTO2);
        monkDetailsDTO2.setId(2L);
        assertThat(monkDetailsDTO1).isNotEqualTo(monkDetailsDTO2);
        monkDetailsDTO1.setId(null);
        assertThat(monkDetailsDTO1).isNotEqualTo(monkDetailsDTO2);
    }
}
