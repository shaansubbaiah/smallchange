package org.sc.backend.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.sc.backend.domain.enumeration.IncomeCategory;
import org.sc.backend.domain.enumeration.InvestmentLength;
import org.sc.backend.domain.enumeration.RiskTolerance;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link org.sc.backend.domain.Preferences} entity. This class is used
 * in {@link org.sc.backend.web.rest.admin.PreferencesResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /preferences?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PreferencesCriteria implements Serializable, Criteria {

    /**
     * Class for filtering RiskTolerance
     */
    public static class RiskToleranceFilter extends Filter<RiskTolerance> {

        public RiskToleranceFilter() {}

        public RiskToleranceFilter(RiskToleranceFilter filter) {
            super(filter);
        }

        @Override
        public RiskToleranceFilter copy() {
            return new RiskToleranceFilter(this);
        }
    }

    /**
     * Class for filtering IncomeCategory
     */
    public static class IncomeCategoryFilter extends Filter<IncomeCategory> {

        public IncomeCategoryFilter() {}

        public IncomeCategoryFilter(IncomeCategoryFilter filter) {
            super(filter);
        }

        @Override
        public IncomeCategoryFilter copy() {
            return new IncomeCategoryFilter(this);
        }
    }

    /**
     * Class for filtering InvestmentLength
     */
    public static class InvestmentLengthFilter extends Filter<InvestmentLength> {

        public InvestmentLengthFilter() {}

        public InvestmentLengthFilter(InvestmentLengthFilter filter) {
            super(filter);
        }

        @Override
        public InvestmentLengthFilter copy() {
            return new InvestmentLengthFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private StringFilter scUserId;

    private StringFilter investmentPurpose;

    private RiskToleranceFilter riskTolerance;

    private IncomeCategoryFilter incomeCategory;

    private InvestmentLengthFilter investmentLength;

    private Boolean distinct;

    public PreferencesCriteria() {}

    public PreferencesCriteria(PreferencesCriteria other) {
        this.scUserId = other.scUserId == null ? null : other.scUserId.copy();
        this.investmentPurpose = other.investmentPurpose == null ? null : other.investmentPurpose.copy();
        this.riskTolerance = other.riskTolerance == null ? null : other.riskTolerance.copy();
        this.incomeCategory = other.incomeCategory == null ? null : other.incomeCategory.copy();
        this.investmentLength = other.investmentLength == null ? null : other.investmentLength.copy();
        this.scUserId = other.scUserId == null ? null : other.scUserId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public PreferencesCriteria copy() {
        return new PreferencesCriteria(this);
    }

    public StringFilter getInvestmentPurpose() {
        return investmentPurpose;
    }

    public StringFilter investmentPurpose() {
        if (investmentPurpose == null) {
            investmentPurpose = new StringFilter();
        }
        return investmentPurpose;
    }

    public void setInvestmentPurpose(StringFilter investmentPurpose) {
        this.investmentPurpose = investmentPurpose;
    }

    public RiskToleranceFilter getRiskTolerance() {
        return riskTolerance;
    }

    public RiskToleranceFilter riskTolerance() {
        if (riskTolerance == null) {
            riskTolerance = new RiskToleranceFilter();
        }
        return riskTolerance;
    }

    public void setRiskTolerance(RiskToleranceFilter riskTolerance) {
        this.riskTolerance = riskTolerance;
    }

    public IncomeCategoryFilter getIncomeCategory() {
        return incomeCategory;
    }

    public IncomeCategoryFilter incomeCategory() {
        if (incomeCategory == null) {
            incomeCategory = new IncomeCategoryFilter();
        }
        return incomeCategory;
    }

    public void setIncomeCategory(IncomeCategoryFilter incomeCategory) {
        this.incomeCategory = incomeCategory;
    }

    public InvestmentLengthFilter getInvestmentLength() {
        return investmentLength;
    }

    public InvestmentLengthFilter investmentLength() {
        if (investmentLength == null) {
            investmentLength = new InvestmentLengthFilter();
        }
        return investmentLength;
    }

    public void setInvestmentLength(InvestmentLengthFilter investmentLength) {
        this.investmentLength = investmentLength;
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
        final PreferencesCriteria that = (PreferencesCriteria) o;
        return (
            Objects.equals(scUserId, that.scUserId) &&
            Objects.equals(investmentPurpose, that.investmentPurpose) &&
            Objects.equals(riskTolerance, that.riskTolerance) &&
            Objects.equals(incomeCategory, that.incomeCategory) &&
            Objects.equals(investmentLength, that.investmentLength) &&
            Objects.equals(scUserId, that.scUserId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(scUserId, investmentPurpose, riskTolerance, incomeCategory, investmentLength, scUserId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PreferencesCriteria{" +
            (scUserId != null ? "scUserId=" + scUserId + ", " : "") +
            (investmentPurpose != null ? "investmentPurpose=" + investmentPurpose + ", " : "") +
            (riskTolerance != null ? "riskTolerance=" + riskTolerance + ", " : "") +
            (incomeCategory != null ? "incomeCategory=" + incomeCategory + ", " : "") +
            (investmentLength != null ? "investmentLength=" + investmentLength + ", " : "") +
            (scUserId != null ? "scUserId=" + scUserId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
