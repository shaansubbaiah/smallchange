package org.smallchange.model;

import org.smallchange.model.Asset;

public class MutualFunds extends Asset {
    private float interestRate;


    public MutualFunds(String name, String code, String assetClass, int quantity, float currentPrice, float interestRate) {
        super(name, code, assetClass, quantity, currentPrice);
        this.interestRate = interestRate;
    }

    public float getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(float interestRate) {
        this.interestRate = interestRate;
    }
}
