package org.sc.backend.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.sc.backend.web.rest.TestUtil;

class MutualFundsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MutualFunds.class);
        MutualFunds mutualFunds1 = new MutualFunds();
        mutualFunds1.setCode("id1");
        MutualFunds mutualFunds2 = new MutualFunds();
        mutualFunds2.setCode(mutualFunds1.getCode());
        assertThat(mutualFunds1).isEqualTo(mutualFunds2);
        mutualFunds2.setCode("id2");
        assertThat(mutualFunds1).isNotEqualTo(mutualFunds2);
        mutualFunds1.setCode(null);
        assertThat(mutualFunds1).isNotEqualTo(mutualFunds2);
    }
}
