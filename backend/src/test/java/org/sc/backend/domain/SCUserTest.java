package org.sc.backend.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.sc.backend.web.rest.TestUtil;

class SCUserTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SCUser.class);
        SCUser sCUser1 = new SCUser();
        sCUser1.setScUserId("id1");
        SCUser sCUser2 = new SCUser();
        sCUser2.setScUserId(sCUser1.getScUserId());
        assertThat(sCUser1).isEqualTo(sCUser2);
        sCUser2.setScUserId("id2");
        assertThat(sCUser1).isNotEqualTo(sCUser2);
        sCUser1.setScUserId(null);
        assertThat(sCUser1).isNotEqualTo(sCUser2);
    }
}
