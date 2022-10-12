package org.smallchange.model;

import org.smallchange.model.Asset;

public class Stocks extends Asset {
    private String exchange;

    public Stocks(String name, String code, String assetClass, int quantity, float currentPrice, String exchange) {
        super(name, code, assetClass, quantity, currentPrice);
        this.exchange = exchange;
    }

    public String getExchange() {
        return exchange;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
    }
}
