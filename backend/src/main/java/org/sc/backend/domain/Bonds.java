package org.sc.backend.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.springframework.data.domain.Persistable;

/**
 * A Bonds.
 */
@JsonIgnoreProperties(value = { "new" })
@Entity
@Table(name = "sc_bonds")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Bonds implements Serializable, Persistable<String> {

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

    @NotNull
    @DecimalMin(value = "0")
    @Column(name = "interest_rate", nullable = false)
    private Float interestRate;

    @NotNull
    @Min(value = 0)
    @Column(name = "duration_months", nullable = false)
    private Integer durationMonths;

    @Column(name = "bond_type")
    private String bondType;

    @Column(name = "exchange_name")
    private String exchangeName;

    @Transient
    private boolean isPersisted;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getCode() {
        return this.code;
    }

    public Bonds code(String code) {
        this.setCode(code);
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return this.name;
    }

    public Bonds name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getQuantity() {
        return this.quantity;
    }

    public Bonds quantity(Integer quantity) {
        this.setQuantity(quantity);
        return this;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Float getCurrentPrice() {
        return this.currentPrice;
    }

    public Bonds currentPrice(Float currentPrice) {
        this.setCurrentPrice(currentPrice);
        return this;
    }

    public void setCurrentPrice(Float currentPrice) {
        this.currentPrice = currentPrice;
    }

    public Float getInterestRate() {
        return this.interestRate;
    }

    public Bonds interestRate(Float interestRate) {
        this.setInterestRate(interestRate);
        return this;
    }

    public void setInterestRate(Float interestRate) {
        this.interestRate = interestRate;
    }

    public Integer getDurationMonths() {
        return this.durationMonths;
    }

    public Bonds durationMonths(Integer durationMonths) {
        this.setDurationMonths(durationMonths);
        return this;
    }

    public void setDurationMonths(Integer durationMonths) {
        this.durationMonths = durationMonths;
    }

    public String getBondType() {
        return this.bondType;
    }

    public Bonds bondType(String bondType) {
        this.setBondType(bondType);
        return this;
    }

    public void setBondType(String bondType) {
        this.bondType = bondType;
    }

    public String getExchangeName() {
        return this.exchangeName;
    }

    public Bonds exchangeName(String exchangeName) {
        this.setExchangeName(exchangeName);
        return this;
    }

    public void setExchangeName(String exchangeName) {
        this.exchangeName = exchangeName;
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

    public Bonds setIsPersisted() {
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
        if (!(o instanceof Bonds)) {
            return false;
        }
        return code != null && code.equals(((Bonds) o).code);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Bonds{" +
            "code=" + getCode() +
            ", name='" + getName() + "'" +
            ", quantity=" + getQuantity() +
            ", currentPrice=" + getCurrentPrice() +
            ", interestRate=" + getInterestRate() +
            ", durationMonths=" + getDurationMonths() +
            ", bondType='" + getBondType() + "'" +
            ", exchangeName='" + getExchangeName() + "'" +
            "}";
    }
}
