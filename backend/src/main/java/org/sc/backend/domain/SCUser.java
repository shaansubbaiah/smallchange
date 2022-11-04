package org.sc.backend.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.springframework.data.domain.Persistable;

/**
 * A SCUser.
 */
@JsonIgnoreProperties(value = { "new" })
@Entity
@Table(name = "sc_user")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SCUser implements Serializable, Persistable<String> {

    private static final long serialVersionUID = 1L;

    @Size(min = 3, max = 10)
    @Id
    @Column(name = "sc_user_id", length = 10)
    private String scUserId;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Pattern(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @NotNull
    @Size(min = 64, max = 64)
    @Column(name = "password_hash", length = 64, nullable = false)
    private String passwordHash;

    @Lob
    @Column(name = "image")
    private byte[] image;

    @Column(name = "image_content_type")
    private String imageContentType;

    @Transient
    private boolean isPersisted;

    @JsonIgnoreProperties(value = { "sCUser" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private Preferences preferences;

    @OneToMany(mappedBy = "sCUser")
    @JsonIgnoreProperties(value = { "sCUser" }, allowSetters = true)
    private Set<Positions> positions = new HashSet<>();

    @OneToMany(mappedBy = "sCUser")
    @JsonIgnoreProperties(value = { "sCUser" }, allowSetters = true)
    private Set<SCAccount> sCAccounts = new HashSet<>();

    @OneToMany(mappedBy = "sCUser")
    @JsonIgnoreProperties(value = { "sCUser" }, allowSetters = true)
    private Set<TradeHistory> tradeHistories = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getScUserId() {
        return this.scUserId;
    }

    public SCUser scUserId(String scUserId) {
        this.setScUserId(scUserId);
        return this;
    }

    public void setScUserId(String scUserId) {
        this.scUserId = scUserId;
    }

    public String getName() {
        return this.name;
    }

    public SCUser name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return this.email;
    }

    public SCUser email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswordHash() {
        return this.passwordHash;
    }

    public SCUser passwordHash(String passwordHash) {
        this.setPasswordHash(passwordHash);
        return this;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public byte[] getImage() {
        return this.image;
    }

    public SCUser image(byte[] image) {
        this.setImage(image);
        return this;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getImageContentType() {
        return this.imageContentType;
    }

    public SCUser imageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
        return this;
    }

    public void setImageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
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

    public SCUser setIsPersisted() {
        this.isPersisted = true;
        return this;
    }

    @PostLoad
    @PostPersist
    public void updateEntityState() {
        this.setIsPersisted();
    }

    public Preferences getPreferences() {
        return this.preferences;
    }

    public void setPreferences(Preferences preferences) {
        this.preferences = preferences;
    }

    public SCUser preferences(Preferences preferences) {
        this.setPreferences(preferences);
        return this;
    }

    public Set<Positions> getPositions() {
        return this.positions;
    }

    public void setPositions(Set<Positions> positions) {
        if (this.positions != null) {
            this.positions.forEach(i -> i.setSCUser(null));
        }
        if (positions != null) {
            positions.forEach(i -> i.setSCUser(this));
        }
        this.positions = positions;
    }

    public SCUser positions(Set<Positions> positions) {
        this.setPositions(positions);
        return this;
    }

    public SCUser addPositions(Positions positions) {
        this.positions.add(positions);
        positions.setSCUser(this);
        return this;
    }

    public SCUser removePositions(Positions positions) {
        this.positions.remove(positions);
        positions.setSCUser(null);
        return this;
    }

    public Set<SCAccount> getSCAccounts() {
        return this.sCAccounts;
    }

    public void setSCAccounts(Set<SCAccount> sCAccounts) {
        if (this.sCAccounts != null) {
            this.sCAccounts.forEach(i -> i.setSCUser(null));
        }
        if (sCAccounts != null) {
            sCAccounts.forEach(i -> i.setSCUser(this));
        }
        this.sCAccounts = sCAccounts;
    }

    public SCUser sCAccounts(Set<SCAccount> sCAccounts) {
        this.setSCAccounts(sCAccounts);
        return this;
    }

    public SCUser addSCAccount(SCAccount sCAccount) {
        this.sCAccounts.add(sCAccount);
        sCAccount.setSCUser(this);
        return this;
    }

    public SCUser removeSCAccount(SCAccount sCAccount) {
        this.sCAccounts.remove(sCAccount);
        sCAccount.setSCUser(null);
        return this;
    }

    public Set<TradeHistory> getTradeHistories() {
        return this.tradeHistories;
    }

    public void setTradeHistories(Set<TradeHistory> tradeHistories) {
        if (this.tradeHistories != null) {
            this.tradeHistories.forEach(i -> i.setSCUser(null));
        }
        if (tradeHistories != null) {
            tradeHistories.forEach(i -> i.setSCUser(this));
        }
        this.tradeHistories = tradeHistories;
    }

    public SCUser tradeHistories(Set<TradeHistory> tradeHistories) {
        this.setTradeHistories(tradeHistories);
        return this;
    }

    public SCUser addTradeHistory(TradeHistory tradeHistory) {
        this.tradeHistories.add(tradeHistory);
        tradeHistory.setSCUser(this);
        return this;
    }

    public SCUser removeTradeHistory(TradeHistory tradeHistory) {
        this.tradeHistories.remove(tradeHistory);
        tradeHistory.setSCUser(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SCUser)) {
            return false;
        }
        return scUserId != null && scUserId.equals(((SCUser) o).scUserId);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SCUser{" +
            "scUserId=" + getScUserId() +
            ", name='" + getName() + "'" +
            ", email='" + getEmail() + "'" +
            ", passwordHash='" + getPasswordHash() + "'" +
            ", image='" + getImage() + "'" +
            ", imageContentType='" + getImageContentType() + "'" +
            "}";
    }
}
