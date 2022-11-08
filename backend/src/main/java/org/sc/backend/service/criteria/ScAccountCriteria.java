package org.sc.backend.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link org.sc.backend.domain.ScAccount} entity. This class is used
 * in {@link org.sc.backend.web.rest.admin.ScAccountResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /sc-accounts?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ScAccountCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter accNo;

    private StringFilter scUserId;

    private StringFilter bankName;

    private StringFilter accType;

    private FloatFilter accBalance;

    private Boolean distinct;

    public ScAccountCriteria() {}

    public ScAccountCriteria(ScAccountCriteria other) {
        this.accNo = other.accNo == null ? null : other.accNo.copy();
        this.scUserId = other.scUserId == null ? null : other.scUserId.copy();
        this.bankName = other.bankName == null ? null : other.bankName.copy();
        this.accType = other.accType == null ? null : other.accType.copy();
        this.accBalance = other.accBalance == null ? null : other.accBalance.copy();
        this.scUserId = other.scUserId == null ? null : other.scUserId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public ScAccountCriteria copy() {
        return new ScAccountCriteria(this);
    }

    public LongFilter getAccNo() {
        return accNo;
    }

    public LongFilter accNo() {
        if (accNo == null) {
            accNo = new LongFilter();
        }
        return accNo;
    }

    public void setAccNo(LongFilter accNo) {
        this.accNo = accNo;
    }
    public StringFilter getBankName() {
        return bankName;
    }

    public StringFilter bankName() {
        if (bankName == null) {
            bankName = new StringFilter();
        }
        return bankName;
    }

    public void setBankName(StringFilter bankName) {
        this.bankName = bankName;
    }

    public StringFilter getAccType() {
        return accType;
    }

    public StringFilter accType() {
        if (accType == null) {
            accType = new StringFilter();
        }
        return accType;
    }

    public void setAccType(StringFilter accType) {
        this.accType = accType;
    }

    public FloatFilter getAccBalance() {
        return accBalance;
    }

    public FloatFilter accBalance() {
        if (accBalance == null) {
            accBalance = new FloatFilter();
        }
        return accBalance;
    }

    public void setAccBalance(FloatFilter accBalance) {
        this.accBalance = accBalance;
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
        final ScAccountCriteria that = (ScAccountCriteria) o;
        return (
            Objects.equals(accNo, that.accNo) &&
            Objects.equals(scUserId, that.scUserId) &&
            Objects.equals(bankName, that.bankName) &&
            Objects.equals(accType, that.accType) &&
            Objects.equals(accBalance, that.accBalance) &&
            Objects.equals(scUserId, that.scUserId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(accNo, scUserId, bankName, accType, accBalance, scUserId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ScAccountCriteria{" +
            (accNo != null ? "accNo=" + accNo + ", " : "") +
            (scUserId != null ? "scUserId=" + scUserId + ", " : "") +
            (bankName != null ? "bankName=" + bankName + ", " : "") +
            (accType != null ? "accType=" + accType + ", " : "") +
            (accBalance != null ? "accBalance=" + accBalance + ", " : "") +
            (scUserId != null ? "scUserId=" + scUserId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
