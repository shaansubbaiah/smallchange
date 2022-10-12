package org.smallchange.model;

import java.time.LocalDate;
import java.util.Objects;

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

    @Override
    public String toString() {
        return "Trade{" +
                "tradeType='" + tradeType + '\'' +
                ", date=" + date +
                ", tradeQuantity=" + tradeQuantity +
                ", asset=" + asset +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Trade)) return false;
        Trade trade = (Trade) o;
        return getTradeQuantity() == trade.getTradeQuantity() && Objects.equals(getTradeType(), trade.getTradeType()) && Objects.equals(getDate(), trade.getDate()) && Objects.equals(getAsset(), trade.getAsset());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTradeType(), getDate(), getTradeQuantity(), getAsset());
    }
}
