package org.sc.backend.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.springframework.data.domain.Persistable;

/**
 * A Stocks.
 */
@JsonIgnoreProperties(value = { "new" })
@Entity
@Table(name = "sc_stocks")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Stocks implements Serializable, Persistable<String> {

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

    @Column(name = "stock_type")
    private String stockType;

    @Column(name = "exchange_name")
    private String exchangeName;

    @Transient
    private boolean isPersisted;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getCode() {
        return this.code;
    }

    public Stocks code(String code) {
        this.setCode(code);
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return this.name;
    }

    public Stocks name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getQuantity() {
        return this.quantity;
    }

    public Stocks quantity(Integer quantity) {
        this.setQuantity(quantity);
        return this;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Float getCurrentPrice() {
        return this.currentPrice;
    }

    public Stocks currentPrice(Float currentPrice) {
        this.setCurrentPrice(currentPrice);
        return this;
    }

    public void setCurrentPrice(Float currentPrice) {
        this.currentPrice = currentPrice;
    }

    public String getStockType() {
        return this.stockType;
    }

    public Stocks stockType(String stockType) {
        this.setStockType(stockType);
        return this;
    }

    public void setStockType(String stockType) {
        this.stockType = stockType;
    }

    public String getExchangeName() {
        return this.exchangeName;
    }

    public Stocks exchangeName(String exchangeName) {
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

    public Stocks setIsPersisted() {
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
        if (!(o instanceof Stocks)) {
            return false;
        }
        return code != null && code.equals(((Stocks) o).code);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Stocks{" +
            "code=" + getCode() +
            ", name='" + getName() + "'" +
            ", quantity=" + getQuantity() +
            ", currentPrice=" + getCurrentPrice() +
            ", stockType='" + getStockType() + "'" +
            ", exchangeName='" + getExchangeName() + "'" +
            "}";
    }
}
