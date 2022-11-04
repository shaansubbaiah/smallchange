package org.sc.backend.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.sc.backend.web.rest.TestUtil;

class BondsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Bonds.class);
        Bonds bonds1 = new Bonds();
        bonds1.setCode("id1");
        Bonds bonds2 = new Bonds();
        bonds2.setCode(bonds1.getCode());
        assertThat(bonds1).isEqualTo(bonds2);
        bonds2.setCode("id2");
        assertThat(bonds1).isNotEqualTo(bonds2);
        bonds1.setCode(null);
        assertThat(bonds1).isNotEqualTo(bonds2);
    }
}
