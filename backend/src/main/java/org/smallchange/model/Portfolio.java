package org.smallchange.model;

import org.smallchange.model.Asset;

public class Portfolio{

  private float totalInvestment,currentValue;
  Asset[] asset;


  public Portfolio(float totalInvestment, float currentValue, Asset[] asset) {
    this.totalInvestment = totalInvestment;
    this.currentValue = currentValue;
    this.asset = asset;
  }

  public float getTotalInvestment() {
    return totalInvestment;
  }

  public void setTotalInvestment(float totalInvestment) {
    this.totalInvestment = totalInvestment;
  }

  public float getCurrentValue() {
    return currentValue;
  }

  public void setCurrentValue(float currentValue) {
    this.currentValue = currentValue;
  }

  public Asset[] getAsset() {
    return asset;
  }

  public void setAsset(Asset[] asset) {
    this.asset = asset;
  }
}
