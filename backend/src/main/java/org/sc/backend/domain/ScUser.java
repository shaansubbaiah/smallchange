package org.sc.backend.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.sc.backend.domain.enumeration.UserRoles;
import org.springframework.data.domain.Persistable;

/**
 * A ScUser.
 */
@JsonIgnoreProperties(value = { "new" })
@Entity
@Table(name = "sc_user")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ScUser implements Serializable, Persistable<String> {

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
    @Size(max = 72)
    @Column(name = "password_hash", length = 72, nullable = false)
    private String passwordHash;

    @Lob
    @Column(name = "image")
    private byte[] image;

    @Column(name = "image_content_type")
    private String imageContentType;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "sc_user_role", nullable = false)
    private UserRoles scUserRole;

    @NotNull
    @Column(name = "sc_user_enabled", nullable = false)
    private Boolean scUserEnabled;

    @Transient
    private boolean isPersisted;

    @JsonIgnoreProperties(value = { "scUser" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private Preferences preferences;

    @OneToMany(mappedBy = "scUser")
    @JsonIgnoreProperties(value = { "scUser" }, allowSetters = true)
    private Set<Positions> positions = new HashSet<>();

    @OneToMany(mappedBy = "scUser")
    @JsonIgnoreProperties(value = { "scUser" }, allowSetters = true)
    private Set<ScAccount> scAccounts = new HashSet<>();

    @OneToMany(mappedBy = "scUser")
    @JsonIgnoreProperties(value = { "scUser" }, allowSetters = true)
    private Set<TradeHistory> tradeHistories = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getScUserId() {
        return this.scUserId;
    }

    public ScUser scUserId(String scUserId) {
        this.setScUserId(scUserId);
        return this;
    }

    public void setScUserId(String scUserId) {
        this.scUserId = scUserId;
    }

    public String getName() {
        return this.name;
    }

    public ScUser name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return this.email;
    }

    public ScUser email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswordHash() {
        return this.passwordHash;
    }

    public ScUser passwordHash(String passwordHash) {
        this.setPasswordHash(passwordHash);
        return this;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public byte[] getImage() {
        return this.image;
    }

    public ScUser image(byte[] image) {
        this.setImage(image);
        return this;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getImageContentType() {
        return this.imageContentType;
    }

    public ScUser imageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
        return this;
    }

    public void setImageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
    }

    public UserRoles getScUserRole() {
        return this.scUserRole;
    }

    public ScUser scUserRole(UserRoles scUserRole) {
        this.setScUserRole(scUserRole);
        return this;
    }

    public void setScUserRole(UserRoles scUserRole) {
        this.scUserRole = scUserRole;
    }

    public Boolean getScUserEnabled() {
        return this.scUserEnabled;
    }

    public ScUser scUserEnabled(Boolean scUserEnabled) {
        this.setScUserEnabled(scUserEnabled);
        return this;
    }

    public void setScUserEnabled(Boolean scUserEnabled) {
        this.scUserEnabled = scUserEnabled;
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

    public ScUser setIsPersisted() {
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

    public ScUser preferences(Preferences preferences) {
        this.setPreferences(preferences);
        return this;
    }

    public Set<Positions> getPositions() {
        return this.positions;
    }

    public void setPositions(Set<Positions> positions) {
        if (this.positions != null) {
            this.positions.forEach(i -> i.setScUser(null));
        }
        if (positions != null) {
            positions.forEach(i -> i.setScUser(this));
        }
        this.positions = positions;
    }

    public ScUser positions(Set<Positions> positions) {
        this.setPositions(positions);
        return this;
    }

    public ScUser addPositions(Positions positions) {
        this.positions.add(positions);
        positions.setScUser(this);
        return this;
    }

    public ScUser removePositions(Positions positions) {
        this.positions.remove(positions);
        positions.setScUser(null);
        return this;
    }

    public Set<ScAccount> getScAccounts() {
        return this.scAccounts;
    }

    public void setScAccounts(Set<ScAccount> scAccounts) {
        if (this.scAccounts != null) {
            this.scAccounts.forEach(i -> i.setScUser(null));
        }
        if (scAccounts != null) {
            scAccounts.forEach(i -> i.setScUser(this));
        }
        this.scAccounts = scAccounts;
    }

    public ScUser scAccounts(Set<ScAccount> scAccounts) {
        this.setScAccounts(scAccounts);
        return this;
    }

    public ScUser addScAccount(ScAccount scAccount) {
        this.scAccounts.add(scAccount);
        scAccount.setScUser(this);
        return this;
    }

    public ScUser removeScAccount(ScAccount scAccount) {
        this.scAccounts.remove(scAccount);
        scAccount.setScUser(null);
        return this;
    }

    public Set<TradeHistory> getTradeHistories() {
        return this.tradeHistories;
    }

    public void setTradeHistories(Set<TradeHistory> tradeHistories) {
        if (this.tradeHistories != null) {
            this.tradeHistories.forEach(i -> i.setScUser(null));
        }
        if (tradeHistories != null) {
            tradeHistories.forEach(i -> i.setScUser(this));
        }
        this.tradeHistories = tradeHistories;
    }

    public ScUser tradeHistories(Set<TradeHistory> tradeHistories) {
        this.setTradeHistories(tradeHistories);
        return this;
    }

    public ScUser addTradeHistory(TradeHistory tradeHistory) {
        this.tradeHistories.add(tradeHistory);
        tradeHistory.setScUser(this);
        return this;
    }

    public ScUser removeTradeHistory(TradeHistory tradeHistory) {
        this.tradeHistories.remove(tradeHistory);
        tradeHistory.setScUser(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ScUser)) {
            return false;
        }
        return scUserId != null && scUserId.equals(((ScUser) o).scUserId);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ScUser{" +
            "scUserId=" + getScUserId() +
            ", name='" + getName() + "'" +
            ", email='" + getEmail() + "'" +
            ", passwordHash='" + getPasswordHash() + "'" +
            ", image='" + getImage() + "'" +
            ", imageContentType='" + getImageContentType() + "'" +
            ", scUserRole='" + getScUserRole() + "'" +
            ", scUserEnabled='" + getScUserEnabled() + "'" +
            "}";
    }
}
