package org.smallchange.model;

public class Asset {
    private String name,code,assetClass;
    private int quantity;
    private float currentPrice;

    public Asset(String name, String code, String assetClass, int quantity, float currentPrice) {
        this.name = name;
        this.code = code;
        this.assetClass = assetClass;
        this.quantity = quantity;
        this.currentPrice = currentPrice;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getAssetClass() {
        return assetClass;
    }

    public void setAssetClass(String assetClass) {
        this.assetClass = assetClass;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public float getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(float currentPrice) {
        this.currentPrice = currentPrice;
    }
}
