package org.smallchange.dao;
import org.smallchange.model.Trade;

import java.util.List;

public interface TradeHistoryDao {
    public List<Trade> getTradeHistory(String userId);

}
