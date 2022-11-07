package org.sc.backend.service.criteria;

import java.io.Serializable;
import java.util.Objects;

import org.sc.backend.web.rest.admin.BondsResource;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link org.sc.backend.domain.Bonds} entity. This class is used
 * in {@link BondsResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /bonds?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class BondsCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private StringFilter code;

    private StringFilter name;

    private IntegerFilter quantity;

    private FloatFilter currentPrice;

    private FloatFilter interestRate;

    private IntegerFilter durationMonths;

    private StringFilter bondType;

    private StringFilter exchangeName;

    private Boolean distinct;

    public BondsCriteria() {}

    public BondsCriteria(BondsCriteria other) {
        this.code = other.code == null ? null : other.code.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.quantity = other.quantity == null ? null : other.quantity.copy();
        this.currentPrice = other.currentPrice == null ? null : other.currentPrice.copy();
        this.interestRate = other.interestRate == null ? null : other.interestRate.copy();
        this.durationMonths = other.durationMonths == null ? null : other.durationMonths.copy();
        this.bondType = other.bondType == null ? null : other.bondType.copy();
        this.exchangeName = other.exchangeName == null ? null : other.exchangeName.copy();
        this.distinct = other.distinct;
    }

    @Override
    public BondsCriteria copy() {
        return new BondsCriteria(this);
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

    public IntegerFilter getDurationMonths() {
        return durationMonths;
    }

    public IntegerFilter durationMonths() {
        if (durationMonths == null) {
            durationMonths = new IntegerFilter();
        }
        return durationMonths;
    }

    public void setDurationMonths(IntegerFilter durationMonths) {
        this.durationMonths = durationMonths;
    }

    public StringFilter getBondType() {
        return bondType;
    }

    public StringFilter bondType() {
        if (bondType == null) {
            bondType = new StringFilter();
        }
        return bondType;
    }

    public void setBondType(StringFilter bondType) {
        this.bondType = bondType;
    }

    public StringFilter getExchangeName() {
        return exchangeName;
    }

    public StringFilter exchangeName() {
        if (exchangeName == null) {
            exchangeName = new StringFilter();
        }
        return exchangeName;
    }

    public void setExchangeName(StringFilter exchangeName) {
        this.exchangeName = exchangeName;
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
        final BondsCriteria that = (BondsCriteria) o;
        return (
            Objects.equals(code, that.code) &&
            Objects.equals(name, that.name) &&
            Objects.equals(quantity, that.quantity) &&
            Objects.equals(currentPrice, that.currentPrice) &&
            Objects.equals(interestRate, that.interestRate) &&
            Objects.equals(durationMonths, that.durationMonths) &&
            Objects.equals(bondType, that.bondType) &&
            Objects.equals(exchangeName, that.exchangeName) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, name, quantity, currentPrice, interestRate, durationMonths, bondType, exchangeName, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BondsCriteria{" +
            (code != null ? "code=" + code + ", " : "") +
            (name != null ? "name=" + name + ", " : "") +
            (quantity != null ? "quantity=" + quantity + ", " : "") +
            (currentPrice != null ? "currentPrice=" + currentPrice + ", " : "") +
            (interestRate != null ? "interestRate=" + interestRate + ", " : "") +
            (durationMonths != null ? "durationMonths=" + durationMonths + ", " : "") +
            (bondType != null ? "bondType=" + bondType + ", " : "") +
            (exchangeName != null ? "exchangeName=" + exchangeName + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
