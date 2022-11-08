package org.sc.backend.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.sc.backend.domain.enumeration.AssetType;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link org.sc.backend.domain.Positions} entity. This class is used
 * in {@link org.sc.backend.web.rest.admin.PositionsResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /positions?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PositionsCriteria implements Serializable, Criteria {

    /**
     * Class for filtering AssetType
     */
    public static class AssetTypeFilter extends Filter<AssetType> {

        public AssetTypeFilter() {}

        public AssetTypeFilter(AssetTypeFilter filter) {
            super(filter);
        }

        @Override
        public AssetTypeFilter copy() {
            return new AssetTypeFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter positionId;

    private StringFilter scUserId;

    private StringFilter assetCode;

    private AssetTypeFilter assetType;

    private FloatFilter buyPrice;

    private IntegerFilter quantity;


    public PositionsCriteria() {}

    public PositionsCriteria(PositionsCriteria other) {
        this.positionId = other.positionId == null ? null : other.positionId.copy();
        this.scUserId = other.scUserId == null ? null : other.scUserId.copy();
        this.assetCode = other.assetCode == null ? null : other.assetCode.copy();
        this.assetType = other.assetType == null ? null : other.assetType.copy();
        this.buyPrice = other.buyPrice == null ? null : other.buyPrice.copy();
        this.quantity = other.quantity == null ? null : other.quantity.copy();
        this.scUserId = other.scUserId == null ? null : other.scUserId.copy();
    }

    @Override
    public PositionsCriteria copy() {
        return new PositionsCriteria(this);
    }

    public LongFilter getPositionId() {
        return positionId;
    }

    public LongFilter positionId() {
        if (positionId == null) {
            positionId = new LongFilter();
        }
        return positionId;
    }

    public void setPositionId(LongFilter positionId) {
        this.positionId = positionId;
    }

    public StringFilter getScUserId() {
        return scUserId;
    }

    public StringFilter scUserId() {
        if (scUserId == null) {
            scUserId = new StringFilter();
        }
        return scUserId;
    }

    public void setScUserId(StringFilter scUserId) {
        this.scUserId = scUserId;
    }

    public StringFilter getAssetCode() {
        return assetCode;
    }

    public StringFilter assetCode() {
        if (assetCode == null) {
            assetCode = new StringFilter();
        }
        return assetCode;
    }

    public void setAssetCode(StringFilter assetCode) {
        this.assetCode = assetCode;
    }

    public AssetTypeFilter getAssetType() {
        return assetType;
    }

    public AssetTypeFilter assetType() {
        if (assetType == null) {
            assetType = new AssetTypeFilter();
        }
        return assetType;
    }

    public void setAssetType(AssetTypeFilter assetType) {
        this.assetType = assetType;
    }

    public FloatFilter getBuyPrice() {
        return buyPrice;
    }

    public FloatFilter buyPrice() {
        if (buyPrice == null) {
            buyPrice = new FloatFilter();
        }
        return buyPrice;
    }

    public void setBuyPrice(FloatFilter buyPrice) {
        this.buyPrice = buyPrice;
    }

    public IntegerFilter getQuantity() {
        return quantity;
    }

    public IntegerFilter quantity() {
        if (quantity == null) {
            quantity = new IntegerFilter();
        }
        return quantity;
    }

    public void setQuantity(IntegerFilter quantity) {
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final PositionsCriteria that = (PositionsCriteria) o;
        return (
            Objects.equals(positionId, that.positionId) &&
            Objects.equals(scUserId, that.scUserId) &&
            Objects.equals(assetCode, that.assetCode) &&
            Objects.equals(assetType, that.assetType) &&
            Objects.equals(buyPrice, that.buyPrice) &&
            Objects.equals(quantity, that.quantity) &&
            Objects.equals(scUserId, that.scUserId)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(positionId, scUserId, assetCode, assetType, buyPrice, quantity, scUserId);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PositionsCriteria{" +
            (positionId != null ? "positionId=" + positionId + ", " : "") +
            (scUserId != null ? "scUserId=" + scUserId + ", " : "") +
            (assetCode != null ? "assetCode=" + assetCode + ", " : "") +
            (assetType != null ? "assetType=" + assetType + ", " : "") +
            (buyPrice != null ? "buyPrice=" + buyPrice + ", " : "") +
            (quantity != null ? "quantity=" + quantity + ", " : "") +
            (scUserId != null ? "scUserId=" + scUserId + ", " : "") +
            "}";
    }
}
