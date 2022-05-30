package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CourtCaseTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CourtCase.class);
        CourtCase courtCase1 = new CourtCase();
        courtCase1.setId(1L);
        CourtCase courtCase2 = new CourtCase();
        courtCase2.setId(courtCase1.getId());
        assertThat(courtCase1).isEqualTo(courtCase2);
        courtCase2.setId(2L);
        assertThat(courtCase1).isNotEqualTo(courtCase2);
        courtCase1.setId(null);
        assertThat(courtCase1).isNotEqualTo(courtCase2);
    }
}
