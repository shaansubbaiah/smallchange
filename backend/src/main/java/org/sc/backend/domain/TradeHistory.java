package org.sc.backend.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.sc.backend.domain.enumeration.AssetType;

/**
 * A TradeHistory.
 */
@Entity
@Table(name = "sc_trade_history")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TradeHistory implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "trade_id")
    private Long tradeId;

    @NotNull
    @Column(name = "sc_user_id", nullable = false)
    private String scUserId;

    @NotNull
    @Column(name = "asset_code", nullable = false)
    private String assetCode;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "asset_type", nullable = false)
    private AssetType assetType;

    @NotNull
    @Column(name = "trade_price", nullable = false)
    private Float tradePrice;

    @NotNull
    @Column(name = "trade_type", nullable = false)
    private String tradeType;

    @NotNull
    @Column(name = "trade_quantity", nullable = false)
    private Integer tradeQuantity;

    @NotNull
    @Column(name = "trade_date", nullable = false)
    private LocalDate tradeDate;

    @ManyToOne
    @JsonIgnoreProperties(value = { "preferences", "positions", "sCAccounts", "tradeHistories" }, allowSetters = true)
    private SCUser sCUser;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getTradeId() {
        return this.tradeId;
    }

    public TradeHistory tradeId(Long tradeId) {
        this.setTradeId(tradeId);
        return this;
    }

    public void setTradeId(Long tradeId) {
        this.tradeId = tradeId;
    }

    public String getScUserId() {
        return this.scUserId;
    }

    public TradeHistory scUserId(String scUserId) {
        this.setScUserId(scUserId);
        return this;
    }

    public void setScUserId(String scUserId) {
        this.scUserId = scUserId;
    }

    public String getAssetCode() {
        return this.assetCode;
    }

    public TradeHistory assetCode(String assetCode) {
        this.setAssetCode(assetCode);
        return this;
    }

    public void setAssetCode(String assetCode) {
        this.assetCode = assetCode;
    }

    public AssetType getAssetType() {
        return this.assetType;
    }

    public TradeHistory assetType(AssetType assetType) {
        this.setAssetType(assetType);
        return this;
    }

    public void setAssetType(AssetType assetType) {
        this.assetType = assetType;
    }

    public Float getTradePrice() {
        return this.tradePrice;
    }

    public TradeHistory tradePrice(Float tradePrice) {
        this.setTradePrice(tradePrice);
        return this;
    }

    public void setTradePrice(Float tradePrice) {
        this.tradePrice = tradePrice;
    }

    public String getTradeType() {
        return this.tradeType;
    }

    public TradeHistory tradeType(String tradeType) {
        this.setTradeType(tradeType);
        return this;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }

    public Integer getTradeQuantity() {
        return this.tradeQuantity;
    }

    public TradeHistory tradeQuantity(Integer tradeQuantity) {
        this.setTradeQuantity(tradeQuantity);
        return this;
    }

    public void setTradeQuantity(Integer tradeQuantity) {
        this.tradeQuantity = tradeQuantity;
    }

    public LocalDate getTradeDate() {
        return this.tradeDate;
    }

    public TradeHistory tradeDate(LocalDate tradeDate) {
        this.setTradeDate(tradeDate);
        return this;
    }

    public void setTradeDate(LocalDate tradeDate) {
        this.tradeDate = tradeDate;
    }

    public SCUser getSCUser() {
        return this.sCUser;
    }

    public void setSCUser(SCUser sCUser) {
        this.sCUser = sCUser;
    }

    public TradeHistory sCUser(SCUser sCUser) {
        this.setSCUser(sCUser);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TradeHistory)) {
            return false;
        }
        return tradeId != null && tradeId.equals(((TradeHistory) o).tradeId);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TradeHistory{" +
            "tradeId=" + getTradeId() +
            ", scUserId='" + getScUserId() + "'" +
            ", assetCode='" + getAssetCode() + "'" +
            ", assetType='" + getAssetType() + "'" +
            ", tradePrice=" + getTradePrice() +
            ", tradeType='" + getTradeType() + "'" +
            ", tradeQuantity=" + getTradeQuantity() +
            ", tradeDate='" + getTradeDate() + "'" +
            "}";
    }
}
