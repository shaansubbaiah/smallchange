package org.sc.backend.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.sc.backend.web.rest.TestUtil;

class ScAccountTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ScAccount.class);
        ScAccount scAccount1 = new ScAccount();
        scAccount1.setAccNo(1L);
        ScAccount scAccount2 = new ScAccount();
        scAccount2.setAccNo(scAccount1.getAccNo());
        assertThat(scAccount1).isEqualTo(scAccount2);
        scAccount2.setAccNo(2L);
        assertThat(scAccount1).isNotEqualTo(scAccount2);
        scAccount1.setAccNo(null);
        assertThat(scAccount1).isNotEqualTo(scAccount2);
    }
}
