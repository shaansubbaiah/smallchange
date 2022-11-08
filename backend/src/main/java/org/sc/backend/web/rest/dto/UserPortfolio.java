package org.sc.backend.web.rest.dto;

import org.sc.backend.domain.Bonds;
import org.sc.backend.domain.MutualFunds;
import org.sc.backend.domain.Stocks;

import java.util.List;

public class UserPortfolio {

    List<UserPosition> stocks, bonds, mfs;

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

    static class UserPosition {
        String name, code, assetType;
        Integer quantity;
        Float buyPrice, currentPrice;

        public UserPosition(String name, String code, String assetType, Integer quantity, Float buyPrice, Float currentPrice) {
            this.name = name;
            this.code = code;
            this.assetType = assetType;
            this.quantity = quantity;
            this.buyPrice = buyPrice;
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

        public String getAssetType() {
            return assetType;
        }

        public void setAssetType(String assetType) {
            this.assetType = assetType;
        }

        public Integer getQuantity() {
            return quantity;
        }

        public void setQuantity(Integer quantity) {
            this.quantity = quantity;
        }

        public Float getBuyPrice() {
            return buyPrice;
        }

        public void setBuyPrice(Float buyPrice) {
            this.buyPrice = buyPrice;
        }

        public Float getCurrentPrice() {
            return currentPrice;
        }

        public void setCurrentPrice(Float currentPrice) {
            this.currentPrice = currentPrice;
        }
    }
}
