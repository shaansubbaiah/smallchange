package org.sc.backend.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A ScAccount.
 */
@Entity
@Table(name = "sc_account")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ScAccount implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "acc_no")
    private Long accNo;

    @NotNull
    @Column(name = "sc_user_id", nullable = false)
    private String scUserId;

    @NotNull
    @Column(name = "bank_name", nullable = false)
    private String bankName;

    @NotNull
    @Column(name = "acc_type", nullable = false)
    private String accType;

    @DecimalMin(value = "0")
    @Column(name = "acc_balance")
    private Float accBalance;

    @ManyToOne
    @JsonIgnoreProperties(value = { "preferences", "positions", "scAccounts", "tradeHistories" }, allowSetters = true)
    private ScUser scUser;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getAccNo() {
        return this.accNo;
    }

    public ScAccount accNo(Long accNo) {
        this.setAccNo(accNo);
        return this;
    }

    public void setAccNo(Long accNo) {
        this.accNo = accNo;
    }

    public String getScUserId() {
        return this.scUserId;
    }

    public ScAccount scUserId(String scUserId) {
        this.setScUserId(scUserId);
        return this;
    }

    public void setScUserId(String scUserId) {
        this.scUserId = scUserId;
    }

    public String getBankName() {
        return this.bankName;
    }

    public ScAccount bankName(String bankName) {
        this.setBankName(bankName);
        return this;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getAccType() {
        return this.accType;
    }

    public ScAccount accType(String accType) {
        this.setAccType(accType);
        return this;
    }

    public void setAccType(String accType) {
        this.accType = accType;
    }

    public Float getAccBalance() {
        return this.accBalance;
    }

    public ScAccount accBalance(Float accBalance) {
        this.setAccBalance(accBalance);
        return this;
    }

    public void setAccBalance(Float accBalance) {
        this.accBalance = accBalance;
    }

    public ScUser getScUser() {
        return this.scUser;
    }

    public void setScUser(ScUser scUser) {
        this.scUser = scUser;
    }

    public ScAccount scUser(ScUser scUser) {
        this.setScUser(scUser);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ScAccount)) {
            return false;
        }
        return accNo != null && accNo.equals(((ScAccount) o).accNo);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ScAccount{" +
            "accNo=" + getAccNo() +
            ", scUserId='" + getScUserId() + "'" +
            ", bankName='" + getBankName() + "'" +
            ", accType='" + getAccType() + "'" +
            ", accBalance=" + getAccBalance() +
            "}";
    }
}
