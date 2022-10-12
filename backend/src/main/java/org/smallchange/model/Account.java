package org.smallchange.model;

public class Account {

    private String acctName,bankName;
    private int acctNo;


    private float acctBalance;

    public Account(String acctName,String bankName, int acctNo, float acctBalance) {
        this.acctName = acctName;
        this.bankName=bankName;
        this.acctNo= acctNo;
        this.acctBalance=acctBalance;
    }



    public int getAcctNo() {
        return acctNo;
    }

    public void setAcctNo(int acctNo) {
        this.acctNo = acctNo;
    }


    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public float getAcctBalance() {
        return acctBalance;
    }

    public void setAcctBalance(float acctBalance) {
        this.acctBalance = acctBalance;
    }
    public String getAcctName() {
        return acctName;
    }

    public void setAcctName(String acctName) {
        this.acctName = acctName;
    }

}



