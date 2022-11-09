package org.sc.backend.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.sc.backend.domain.enumeration.AssetType;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link org.sc.backend.domain.TradeHistory} entity. This class is used
 * in {@link org.sc.backend.web.rest.admin.TradeHistoryResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /trade-histories?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TradeHistoryCriteria implements Serializable, Criteria {

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

    private LongFilter tradeId;

    private StringFilter scUserId;

    private StringFilter assetCode;

    private AssetTypeFilter assetType;

    private FloatFilter tradePrice;

    private StringFilter tradeType;

    private IntegerFilter tradeQuantity;

    private LocalDateFilter tradeDate;

    private Boolean distinct;

    public TradeHistoryCriteria() {}

    public TradeHistoryCriteria(TradeHistoryCriteria other) {
        this.tradeId = other.tradeId == null ? null : other.tradeId.copy();
        this.scUserId = other.scUserId == null ? null : other.scUserId.copy();
        this.assetCode = other.assetCode == null ? null : other.assetCode.copy();
        this.assetType = other.assetType == null ? null : other.assetType.copy();
        this.tradePrice = other.tradePrice == null ? null : other.tradePrice.copy();
        this.tradeType = other.tradeType == null ? null : other.tradeType.copy();
        this.tradeQuantity = other.tradeQuantity == null ? null : other.tradeQuantity.copy();
        this.tradeDate = other.tradeDate == null ? null : other.tradeDate.copy();
        this.scUserId = other.scUserId == null ? null : other.scUserId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public TradeHistoryCriteria copy() {
        return new TradeHistoryCriteria(this);
    }

    public LongFilter getTradeId() {
        return tradeId;
    }

    public LongFilter tradeId() {
        if (tradeId == null) {
            tradeId = new LongFilter();
        }
        return tradeId;
    }

    public void setTradeId(LongFilter tradeId) {
        this.tradeId = tradeId;
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

    public FloatFilter getTradePrice() {
        return tradePrice;
    }

    public FloatFilter tradePrice() {
        if (tradePrice == null) {
            tradePrice = new FloatFilter();
        }
        return tradePrice;
    }

    public void setTradePrice(FloatFilter tradePrice) {
        this.tradePrice = tradePrice;
    }

    public StringFilter getTradeType() {
        return tradeType;
    }

    public StringFilter tradeType() {
        if (tradeType == null) {
            tradeType = new StringFilter();
        }
        return tradeType;
    }

    public void setTradeType(StringFilter tradeType) {
        this.tradeType = tradeType;
    }

    public IntegerFilter getTradeQuantity() {
        return tradeQuantity;
    }

    public IntegerFilter tradeQuantity() {
        if (tradeQuantity == null) {
            tradeQuantity = new IntegerFilter();
        }
        return tradeQuantity;
    }

    public void setTradeQuantity(IntegerFilter tradeQuantity) {
        this.tradeQuantity = tradeQuantity;
    }

    public LocalDateFilter getTradeDate() {
        return tradeDate;
    }

    public LocalDateFilter tradeDate() {
        if (tradeDate == null) {
            tradeDate = new LocalDateFilter();
        }
        return tradeDate;
    }

    public void setTradeDate(LocalDateFilter tradeDate) {
        this.tradeDate = tradeDate;
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

    public Boolean getDistinct() {
        return distinct;
    }

    public void setDistinct(Boolean distinct) {
        this.distinct = distinct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final TradeHistoryCriteria that = (TradeHistoryCriteria) o;
        return (
            Objects.equals(tradeId, that.tradeId) &&
            Objects.equals(scUserId, that.scUserId) &&
            Objects.equals(assetCode, that.assetCode) &&
            Objects.equals(assetType, that.assetType) &&
            Objects.equals(tradePrice, that.tradePrice) &&
            Objects.equals(tradeType, that.tradeType) &&
            Objects.equals(tradeQuantity, that.tradeQuantity) &&
            Objects.equals(tradeDate, that.tradeDate) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(tradeId, scUserId, assetCode, assetType, tradePrice, tradeType, tradeQuantity, tradeDate, scUserId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TradeHistoryCriteria{" +
            (tradeId != null ? "tradeId=" + tradeId + ", " : "") +
            (scUserId != null ? "scUserId=" + scUserId + ", " : "") +
            (assetCode != null ? "assetCode=" + assetCode + ", " : "") +
            (assetType != null ? "assetType=" + assetType + ", " : "") +
            (tradePrice != null ? "tradePrice=" + tradePrice + ", " : "") +
            (tradeType != null ? "tradeType=" + tradeType + ", " : "") +
            (tradeQuantity != null ? "tradeQuantity=" + tradeQuantity + ", " : "") +
            (tradeDate != null ? "tradeDate=" + tradeDate + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
