package org.smallchange.model;

public class Preference {
    private String investmentPurpose,riskTolerance,incomeCategory,lengthOfInvestment;

    public Preference(String investmentPurpose, String riskTolerance, String incomeCategory, String lengthOfInvestment) {
        this.investmentPurpose = investmentPurpose;
        this.riskTolerance = riskTolerance;
        this.incomeCategory = incomeCategory;
        this.lengthOfInvestment = lengthOfInvestment;
    }

    public String getInvestmentPurpose() {
        return investmentPurpose;
    }

    public void setInvestmentPurpose(String investmentPurpose) {
        this.investmentPurpose = investmentPurpose;
    }

    public String getRiskTolerance() {
        return riskTolerance;
    }

    public void setRiskTolerance(String riskTolerance) {
        this.riskTolerance = riskTolerance;
    }

    public String getIncomeCategory() {
        return incomeCategory;
    }

    public void setIncomeCategory(String incomeCategory) {
        this.incomeCategory = incomeCategory;
    }

    public String getLengthOfInvestment() {
        return lengthOfInvestment;
    }

    public void setLengthOfInvestment(String lengthOfInvestment) {
        this.lengthOfInvestment = lengthOfInvestment;
    }
}
