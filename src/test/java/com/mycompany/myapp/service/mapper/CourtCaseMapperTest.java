package com.mycompany.myapp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CourtCaseMapperTest {

    private CourtCaseMapper courtCaseMapper;

    @BeforeEach
    public void setUp() {
        courtCaseMapper = new CourtCaseMapperImpl();
    }
}
