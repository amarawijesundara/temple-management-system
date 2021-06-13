package com.temple.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GuardianDetailsMapperTest {

    private GuardianDetailsMapper guardianDetailsMapper;

    @BeforeEach
    public void setUp() {
        guardianDetailsMapper = new GuardianDetailsMapperImpl();
    }
}
