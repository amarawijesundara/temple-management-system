package com.temple.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EmergencyContactDetailsMapperTest {

    private EmergencyContactDetailsMapper emergencyContactDetailsMapper;

    @BeforeEach
    public void setUp() {
        emergencyContactDetailsMapper = new EmergencyContactDetailsMapperImpl();
    }
}
