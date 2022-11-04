package org.sc.backend.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link org.sc.backend.domain.MutualFunds} entity. This class is used
 * in {@link org.sc.backend.web.rest.MutualFundsResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /mutual-funds?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class MutualFundsCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private StringFilter code;

    private StringFilter name;

    private IntegerFilter quantity;

    private FloatFilter currentPrice;

    private FloatFilter interestRate;

    private StringFilter mfType;

    private Boolean distinct;

    public MutualFundsCriteria() {}

    public MutualFundsCriteria(MutualFundsCriteria other) {
        this.code = other.code == null ? null : other.code.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.quantity = other.quantity == null ? null : other.quantity.copy();
        this.currentPrice = other.currentPrice == null ? null : other.currentPrice.copy();
        this.interestRate = other.interestRate == null ? null : other.interestRate.copy();
        this.mfType = other.mfType == null ? null : other.mfType.copy();
        this.distinct = other.distinct;
    }

    @Override
    public MutualFundsCriteria copy() {
        return new MutualFundsCriteria(this);
    }

    public StringFilter getCode() {
        return code;
    }

    public StringFilter code() {
        if (code == null) {
            code = new StringFilter();
        }
        return code;
    }

    public void setCode(StringFilter code) {
        this.code = code;
    }

    public StringFilter getName() {
        return name;
    }

    public StringFilter name() {
        if (name == null) {
            name = new StringFilter();
        }
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
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

    public FloatFilter getCurrentPrice() {
        return currentPrice;
    }

    public FloatFilter currentPrice() {
        if (currentPrice == null) {
            currentPrice = new FloatFilter();
        }
        return currentPrice;
    }

    public void setCurrentPrice(FloatFilter currentPrice) {
        this.currentPrice = currentPrice;
    }

    public FloatFilter getInterestRate() {
        return interestRate;
    }

    public FloatFilter interestRate() {
        if (interestRate == null) {
            interestRate = new FloatFilter();
        }
        return interestRate;
    }

    public void setInterestRate(FloatFilter interestRate) {
        this.interestRate = interestRate;
    }

    public StringFilter getMfType() {
        return mfType;
    }

    public StringFilter mfType() {
        if (mfType == null) {
            mfType = new StringFilter();
        }
        return mfType;
    }

    public void setMfType(StringFilter mfType) {
        this.mfType = mfType;
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
        final MutualFundsCriteria that = (MutualFundsCriteria) o;
        return (
            Objects.equals(code, that.code) &&
            Objects.equals(name, that.name) &&
            Objects.equals(quantity, that.quantity) &&
            Objects.equals(currentPrice, that.currentPrice) &&
            Objects.equals(interestRate, that.interestRate) &&
            Objects.equals(mfType, that.mfType) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, name, quantity, currentPrice, interestRate, mfType, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MutualFundsCriteria{" +
            (code != null ? "code=" + code + ", " : "") +
            (name != null ? "name=" + name + ", " : "") +
            (quantity != null ? "quantity=" + quantity + ", " : "") +
            (currentPrice != null ? "currentPrice=" + currentPrice + ", " : "") +
            (interestRate != null ? "interestRate=" + interestRate + ", " : "") +
            (mfType != null ? "mfType=" + mfType + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
