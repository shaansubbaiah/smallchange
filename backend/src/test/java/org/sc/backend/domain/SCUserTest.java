package org.sc.backend.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.sc.backend.web.rest.TestUtil;

class ScUserTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ScUser.class);
        ScUser scUser1 = new ScUser();
        scUser1.setScUserId("id1");
        ScUser scUser2 = new ScUser();
        scUser2.setScUserId(scUser1.getScUserId());
        assertThat(scUser1).isEqualTo(scUser2);
        scUser2.setScUserId("id2");
        assertThat(scUser1).isNotEqualTo(scUser2);
        scUser1.setScUserId(null);
        assertThat(scUser1).isNotEqualTo(scUser2);
    }
}
