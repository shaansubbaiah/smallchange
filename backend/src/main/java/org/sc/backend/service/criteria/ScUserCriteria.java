package org.sc.backend.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.sc.backend.domain.enumeration.UserRoles;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link org.sc.backend.domain.ScUser} entity. This class is used
 * in {@link org.sc.backend.web.rest.admin.ScUserResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /sc-users?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ScUserCriteria implements Serializable, Criteria {

    /**
     * Class for filtering UserRoles
     */
    public static class UserRolesFilter extends Filter<UserRoles> {

        public UserRolesFilter() {}

        public UserRolesFilter(UserRolesFilter filter) {
            super(filter);
        }

        @Override
        public UserRolesFilter copy() {
            return new UserRolesFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private StringFilter scUserId;

    private StringFilter name;

    private StringFilter email;

    private StringFilter passwordHash;

    private UserRolesFilter scUserRole;

    private BooleanFilter scUserEnabled;

    private StringFilter preferencesId;

    private LongFilter positionsId;

    private LongFilter scAccountId;

    private LongFilter tradeHistoryId;

    private Boolean distinct;

    public ScUserCriteria() {}

    public ScUserCriteria(ScUserCriteria other) {
        this.scUserId = other.scUserId == null ? null : other.scUserId.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.email = other.email == null ? null : other.email.copy();
        this.passwordHash = other.passwordHash == null ? null : other.passwordHash.copy();
        this.scUserRole = other.scUserRole == null ? null : other.scUserRole.copy();
        this.scUserEnabled = other.scUserEnabled == null ? null : other.scUserEnabled.copy();
        this.preferencesId = other.preferencesId == null ? null : other.preferencesId.copy();
        this.positionsId = other.positionsId == null ? null : other.positionsId.copy();
        this.scAccountId = other.scAccountId == null ? null : other.scAccountId.copy();
        this.tradeHistoryId = other.tradeHistoryId == null ? null : other.tradeHistoryId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public ScUserCriteria copy() {
        return new ScUserCriteria(this);
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

    public StringFilter getEmail() {
        return email;
    }

    public StringFilter email() {
        if (email == null) {
            email = new StringFilter();
        }
        return email;
    }

    public void setEmail(StringFilter email) {
        this.email = email;
    }

    public StringFilter getPasswordHash() {
        return passwordHash;
    }

    public StringFilter passwordHash() {
        if (passwordHash == null) {
            passwordHash = new StringFilter();
        }
        return passwordHash;
    }

    public void setPasswordHash(StringFilter passwordHash) {
        this.passwordHash = passwordHash;
    }

    public UserRolesFilter getScUserRole() {
        return scUserRole;
    }

    public UserRolesFilter scUserRole() {
        if (scUserRole == null) {
            scUserRole = new UserRolesFilter();
        }
        return scUserRole;
    }

    public void setScUserRole(UserRolesFilter scUserRole) {
        this.scUserRole = scUserRole;
    }

    public BooleanFilter getScUserEnabled() {
        return scUserEnabled;
    }

    public BooleanFilter scUserEnabled() {
        if (scUserEnabled == null) {
            scUserEnabled = new BooleanFilter();
        }
        return scUserEnabled;
    }

    public void setScUserEnabled(BooleanFilter scUserEnabled) {
        this.scUserEnabled = scUserEnabled;
    }

    public StringFilter getPreferencesId() {
        return preferencesId;
    }

    public StringFilter preferencesId() {
        if (preferencesId == null) {
            preferencesId = new StringFilter();
        }
        return preferencesId;
    }

    public void setPreferencesId(StringFilter preferencesId) {
        this.preferencesId = preferencesId;
    }

    public LongFilter getPositionsId() {
        return positionsId;
    }

    public LongFilter positionsId() {
        if (positionsId == null) {
            positionsId = new LongFilter();
        }
        return positionsId;
    }

    public void setPositionsId(LongFilter positionsId) {
        this.positionsId = positionsId;
    }

    public LongFilter getScAccountId() {
        return scAccountId;
    }

    public LongFilter scAccountId() {
        if (scAccountId == null) {
            scAccountId = new LongFilter();
        }
        return scAccountId;
    }

    public void setScAccountId(LongFilter scAccountId) {
        this.scAccountId = scAccountId;
    }

    public LongFilter getTradeHistoryId() {
        return tradeHistoryId;
    }

    public LongFilter tradeHistoryId() {
        if (tradeHistoryId == null) {
            tradeHistoryId = new LongFilter();
        }
        return tradeHistoryId;
    }

    public void setTradeHistoryId(LongFilter tradeHistoryId) {
        this.tradeHistoryId = tradeHistoryId;
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
        final ScUserCriteria that = (ScUserCriteria) o;
        return (
            Objects.equals(scUserId, that.scUserId) &&
            Objects.equals(name, that.name) &&
            Objects.equals(email, that.email) &&
            Objects.equals(passwordHash, that.passwordHash) &&
            Objects.equals(scUserRole, that.scUserRole) &&
            Objects.equals(scUserEnabled, that.scUserEnabled) &&
            Objects.equals(preferencesId, that.preferencesId) &&
            Objects.equals(positionsId, that.positionsId) &&
            Objects.equals(scAccountId, that.scAccountId) &&
            Objects.equals(tradeHistoryId, that.tradeHistoryId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            scUserId,
            name,
            email,
            passwordHash,
            scUserRole,
            scUserEnabled,
            preferencesId,
            positionsId,
            scAccountId,
            tradeHistoryId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ScUserCriteria{" +
            (scUserId != null ? "scUserId=" + scUserId + ", " : "") +
            (name != null ? "name=" + name + ", " : "") +
            (email != null ? "email=" + email + ", " : "") +
            (passwordHash != null ? "passwordHash=" + passwordHash + ", " : "") +
            (scUserRole != null ? "scUserRole=" + scUserRole + ", " : "") +
            (scUserEnabled != null ? "scUserEnabled=" + scUserEnabled + ", " : "") +
            (preferencesId != null ? "preferencesId=" + preferencesId + ", " : "") +
            (positionsId != null ? "positionsId=" + positionsId + ", " : "") +
            (scAccountId != null ? "scAccountId=" + scAccountId + ", " : "") +
            (tradeHistoryId != null ? "tradeHistoryId=" + tradeHistoryId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
