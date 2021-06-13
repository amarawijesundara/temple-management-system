package com.temple.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MonkDetailsMapperTest {

    private MonkDetailsMapper monkDetailsMapper;

    @BeforeEach
    public void setUp() {
        monkDetailsMapper = new MonkDetailsMapperImpl();
    }
}
