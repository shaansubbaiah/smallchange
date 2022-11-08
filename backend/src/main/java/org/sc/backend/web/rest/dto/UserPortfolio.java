package org.sc.backend.web.rest.dto;

import java.util.ArrayList;
import java.util.List;

public class UserPortfolio {

    private List<UserPosition> stocks = new ArrayList<>(), bonds = new ArrayList<>(), mfs = new ArrayList<>();

    public List<UserPosition> getStocks() {
        return stocks;
    }

    public void setStocks(List<UserPosition> stocks) {
        this.stocks = stocks;
    }

    public List<UserPosition> getBonds() {
        return bonds;
    }

    public void setBonds(List<UserPosition> bonds) {
        this.bonds = bonds;
    }

    public List<UserPosition> getMfs() {
        return mfs;
    }

    public void setMfs(List<UserPosition> mfs) {
        this.mfs = mfs;
    }

    public UserPortfolio(List<UserPosition> stocks, List<UserPosition> bonds, List<UserPosition> mfs) {
        this.stocks = stocks;
        this.bonds = bonds;
        this.mfs = mfs;
    }
}
