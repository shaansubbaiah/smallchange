package org.sc.backend.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.sc.backend.web.rest.TestUtil;

class StocksTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Stocks.class);
        Stocks stocks1 = new Stocks();
        stocks1.setCode("id1");
        Stocks stocks2 = new Stocks();
        stocks2.setCode(stocks1.getCode());
        assertThat(stocks1).isEqualTo(stocks2);
        stocks2.setCode("id2");
        assertThat(stocks1).isNotEqualTo(stocks2);
        stocks1.setCode(null);
        assertThat(stocks1).isNotEqualTo(stocks2);
    }
}
