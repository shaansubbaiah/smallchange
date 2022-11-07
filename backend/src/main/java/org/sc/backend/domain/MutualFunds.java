package org.sc.backend.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.springframework.data.domain.Persistable;

/**
 * A MutualFunds.
 */
@JsonIgnoreProperties(value = { "new" })
@Entity
@Table(name = "sc_mutual_funds")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class MutualFunds implements Serializable, Persistable<String> {

    private static final long serialVersionUID = 1L;

    @Size(min = 1)
    @Id
    @Column(name = "code")
    private String code;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Min(value = 0)
    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @NotNull
    @DecimalMin(value = "0")
    @Column(name = "current_price", nullable = false)
    private Float currentPrice;

    @DecimalMin(value = "0")
    @Column(name = "interest_rate")
    private Float interestRate;

    @Column(name = "mf_type")
    private String mfType;

    @Transient
    private boolean isPersisted;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getCode() {
        return this.code;
    }

    public MutualFunds code(String code) {
        this.setCode(code);
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return this.name;
    }

    public MutualFunds name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getQuantity() {
        return this.quantity;
    }

    public MutualFunds quantity(Integer quantity) {
        this.setQuantity(quantity);
        return this;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Float getCurrentPrice() {
        return this.currentPrice;
    }

    public MutualFunds currentPrice(Float currentPrice) {
        this.setCurrentPrice(currentPrice);
        return this;
    }

    public void setCurrentPrice(Float currentPrice) {
        this.currentPrice = currentPrice;
    }

    public Float getInterestRate() {
        return this.interestRate;
    }

    public MutualFunds interestRate(Float interestRate) {
        this.setInterestRate(interestRate);
        return this;
    }

    public void setInterestRate(Float interestRate) {
        this.interestRate = interestRate;
    }

    public String getMfType() {
        return this.mfType;
    }

    public MutualFunds mfType(String mfType) {
        this.setMfType(mfType);
        return this;
    }

    public void setMfType(String mfType) {
        this.mfType = mfType;
    }

    @Override
    public String getId() {
        return this.code;
    }

    @Transient
    @Override
    public boolean isNew() {
        return !this.isPersisted;
    }

    public MutualFunds setIsPersisted() {
        this.isPersisted = true;
        return this;
    }

    @PostLoad
    @PostPersist
    public void updateEntityState() {
        this.setIsPersisted();
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MutualFunds)) {
            return false;
        }
        return code != null && code.equals(((MutualFunds) o).code);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MutualFunds{" +
            "code=" + getCode() +
            ", name='" + getName() + "'" +
            ", quantity=" + getQuantity() +
            ", currentPrice=" + getCurrentPrice() +
            ", interestRate=" + getInterestRate() +
            ", mfType='" + getMfType() + "'" +
            "}";
    }
}
