package com.temple.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.temple.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MonkDetailsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MonkDetails.class);
        MonkDetails monkDetails1 = new MonkDetails();
        monkDetails1.setId(1L);
        MonkDetails monkDetails2 = new MonkDetails();
        monkDetails2.setId(monkDetails1.getId());
        assertThat(monkDetails1).isEqualTo(monkDetails2);
        monkDetails2.setId(2L);
        assertThat(monkDetails1).isNotEqualTo(monkDetails2);
        monkDetails1.setId(null);
        assertThat(monkDetails1).isNotEqualTo(monkDetails2);
    }
}
