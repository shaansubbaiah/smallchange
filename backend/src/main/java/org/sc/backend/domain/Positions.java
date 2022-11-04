package org.sc.backend.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.sc.backend.domain.enumeration.AssetType;

/**
 * A Positions.
 */
@Entity
@Table(name = "sc_positions")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Positions implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "position_id")
    private Long positionId;

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
    @DecimalMin(value = "0")
    @Column(name = "buy_price", nullable = false)
    private Float buyPrice;

    @NotNull
    @Min(value = 0)
    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @ManyToOne
    @JsonIgnoreProperties(value = { "preferences", "positions", "sCAccounts", "tradeHistories" }, allowSetters = true)
    private SCUser sCUser;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getPositionId() {
        return this.positionId;
    }

    public Positions positionId(Long positionId) {
        this.setPositionId(positionId);
        return this;
    }

    public void setPositionId(Long positionId) {
        this.positionId = positionId;
    }

    public String getScUserId() {
        return this.scUserId;
    }

    public Positions scUserId(String scUserId) {
        this.setScUserId(scUserId);
        return this;
    }

    public void setScUserId(String scUserId) {
        this.scUserId = scUserId;
    }

    public String getAssetCode() {
        return this.assetCode;
    }

    public Positions assetCode(String assetCode) {
        this.setAssetCode(assetCode);
        return this;
    }

    public void setAssetCode(String assetCode) {
        this.assetCode = assetCode;
    }

    public AssetType getAssetType() {
        return this.assetType;
    }

    public Positions assetType(AssetType assetType) {
        this.setAssetType(assetType);
        return this;
    }

    public void setAssetType(AssetType assetType) {
        this.assetType = assetType;
    }

    public Float getBuyPrice() {
        return this.buyPrice;
    }

    public Positions buyPrice(Float buyPrice) {
        this.setBuyPrice(buyPrice);
        return this;
    }

    public void setBuyPrice(Float buyPrice) {
        this.buyPrice = buyPrice;
    }

    public Integer getQuantity() {
        return this.quantity;
    }

    public Positions quantity(Integer quantity) {
        this.setQuantity(quantity);
        return this;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public SCUser getSCUser() {
        return this.sCUser;
    }

    public void setSCUser(SCUser sCUser) {
        this.sCUser = sCUser;
    }

    public Positions sCUser(SCUser sCUser) {
        this.setSCUser(sCUser);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Positions)) {
            return false;
        }
        return positionId != null && positionId.equals(((Positions) o).positionId);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Positions{" +
            "positionId=" + getPositionId() +
            ", scUserId='" + getScUserId() + "'" +
            ", assetCode='" + getAssetCode() + "'" +
            ", assetType='" + getAssetType() + "'" +
            ", buyPrice=" + getBuyPrice() +
            ", quantity=" + getQuantity() +
            "}";
    }
}
