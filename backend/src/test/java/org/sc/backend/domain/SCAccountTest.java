package org.sc.backend.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.sc.backend.web.rest.TestUtil;

class SCAccountTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SCAccount.class);
        SCAccount sCAccount1 = new SCAccount();
        sCAccount1.setAccNo(1L);
        SCAccount sCAccount2 = new SCAccount();
        sCAccount2.setAccNo(sCAccount1.getAccNo());
        assertThat(sCAccount1).isEqualTo(sCAccount2);
        sCAccount2.setAccNo(2L);
        assertThat(sCAccount1).isNotEqualTo(sCAccount2);
        sCAccount1.setAccNo(null);
        assertThat(sCAccount1).isNotEqualTo(sCAccount2);
    }
}
