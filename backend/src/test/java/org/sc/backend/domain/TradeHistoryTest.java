package org.sc.backend.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.sc.backend.web.rest.TestUtil;

class TradeHistoryTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TradeHistory.class);
        TradeHistory tradeHistory1 = new TradeHistory();
        tradeHistory1.setTradeId(1L);
        TradeHistory tradeHistory2 = new TradeHistory();
        tradeHistory2.setTradeId(tradeHistory1.getTradeId());
        assertThat(tradeHistory1).isEqualTo(tradeHistory2);
        tradeHistory2.setTradeId(2L);
        assertThat(tradeHistory1).isNotEqualTo(tradeHistory2);
        tradeHistory1.setTradeId(null);
        assertThat(tradeHistory1).isNotEqualTo(tradeHistory2);
    }
}
