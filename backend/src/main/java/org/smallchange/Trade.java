package org.smallchange;

import java.time.LocalDate;

public class Trade {
    private String tradeType;
    private LocalDate date;
    private int tradeQuantity;
    Asset asset;


    public Trade(String tradeType, LocalDate date, int tradeQuantity, Asset asset) {
        this.tradeType = tradeType;
        this.date = date;
        this.tradeQuantity = tradeQuantity;
        this.asset = asset;
    }

    public String getTradeType() {
        return tradeType;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getTradeQuantity() {
        return tradeQuantity;
    }

    public void setTradeQuantity(int tradeQuantity) {
        this.tradeQuantity = tradeQuantity;
    }

    public Asset getAsset() {
        return asset;
    }

    public void setAsset(Asset asset) {
        this.asset = asset;
    }
}
