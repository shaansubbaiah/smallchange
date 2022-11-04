package org.sc.backend.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import org.sc.backend.domain.enumeration.IncomeCategory;
import org.sc.backend.domain.enumeration.InvestmentLength;
import org.sc.backend.domain.enumeration.RiskTolerance;
import org.springframework.data.domain.Persistable;

/**
 * A Preferences.
 */
@JsonIgnoreProperties(value = { "new" })
@Entity
@Table(name = "sc_preferences")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Preferences implements Serializable, Persistable<String> {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "sc_user_id")
    private String scUserId;

    @Column(name = "investment_purpose")
    private String investmentPurpose;

    @Enumerated(EnumType.STRING)
    @Column(name = "risk_tolerance")
    private RiskTolerance riskTolerance;

    @Enumerated(EnumType.STRING)
    @Column(name = "income_category")
    private IncomeCategory incomeCategory;

    @Enumerated(EnumType.STRING)
    @Column(name = "investment_length")
    private InvestmentLength investmentLength;

    @Transient
    private boolean isPersisted;

    @JsonIgnoreProperties(value = { "preferences", "positions", "sCAccounts", "tradeHistories" }, allowSetters = true)
    @OneToOne(mappedBy = "preferences")
    private SCUser sCUser;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getScUserId() {
        return this.scUserId;
    }

    public Preferences scUserId(String scUserId) {
        this.setScUserId(scUserId);
        return this;
    }

    public void setScUserId(String scUserId) {
        this.scUserId = scUserId;
    }

    public String getInvestmentPurpose() {
        return this.investmentPurpose;
    }

    public Preferences investmentPurpose(String investmentPurpose) {
        this.setInvestmentPurpose(investmentPurpose);
        return this;
    }

    public void setInvestmentPurpose(String investmentPurpose) {
        this.investmentPurpose = investmentPurpose;
    }

    public RiskTolerance getRiskTolerance() {
        return this.riskTolerance;
    }

    public Preferences riskTolerance(RiskTolerance riskTolerance) {
        this.setRiskTolerance(riskTolerance);
        return this;
    }

    public void setRiskTolerance(RiskTolerance riskTolerance) {
        this.riskTolerance = riskTolerance;
    }

    public IncomeCategory getIncomeCategory() {
        return this.incomeCategory;
    }

    public Preferences incomeCategory(IncomeCategory incomeCategory) {
        this.setIncomeCategory(incomeCategory);
        return this;
    }

    public void setIncomeCategory(IncomeCategory incomeCategory) {
        this.incomeCategory = incomeCategory;
    }

    public InvestmentLength getInvestmentLength() {
        return this.investmentLength;
    }

    public Preferences investmentLength(InvestmentLength investmentLength) {
        this.setInvestmentLength(investmentLength);
        return this;
    }

    public void setInvestmentLength(InvestmentLength investmentLength) {
        this.investmentLength = investmentLength;
    }

    @Override
    public String getId() {
        return this.scUserId;
    }

    @Transient
    @Override
    public boolean isNew() {
        return !this.isPersisted;
    }

    public Preferences setIsPersisted() {
        this.isPersisted = true;
        return this;
    }

    @PostLoad
    @PostPersist
    public void updateEntityState() {
        this.setIsPersisted();
    }

    public SCUser getSCUser() {
        return this.sCUser;
    }

    public void setSCUser(SCUser sCUser) {
        if (this.sCUser != null) {
            this.sCUser.setPreferences(null);
        }
        if (sCUser != null) {
            sCUser.setPreferences(this);
        }
        this.sCUser = sCUser;
    }

    public Preferences sCUser(SCUser sCUser) {
        this.setSCUser(sCUser);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Preferences)) {
            return false;
        }
        return scUserId != null && scUserId.equals(((Preferences) o).scUserId);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Preferences{" +
            "scUserId=" + getScUserId() +
            ", investmentPurpose='" + getInvestmentPurpose() + "'" +
            ", riskTolerance='" + getRiskTolerance() + "'" +
            ", incomeCategory='" + getIncomeCategory() + "'" +
            ", investmentLength='" + getInvestmentLength() + "'" +
            "}";
    }
}
