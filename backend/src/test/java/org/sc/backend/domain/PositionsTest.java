package org.sc.backend.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.sc.backend.web.rest.TestUtil;

class PositionsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Positions.class);
        Positions positions1 = new Positions();
        positions1.setPositionId(1L);
        Positions positions2 = new Positions();
        positions2.setPositionId(positions1.getPositionId());
        assertThat(positions1).isEqualTo(positions2);
        positions2.setPositionId(2L);
        assertThat(positions1).isNotEqualTo(positions2);
        positions1.setPositionId(null);
        assertThat(positions1).isNotEqualTo(positions2);
    }
}
