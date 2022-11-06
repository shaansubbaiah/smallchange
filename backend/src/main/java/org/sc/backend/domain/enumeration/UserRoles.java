package org.sc.backend.domain.enumeration;

/**
 * The UserRoles enumeration.
 */
public enum UserRoles {
    USER("ROLE_USER"),
    ADMIN("ROLE_ADMIN");

    private final String value;

    UserRoles(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
