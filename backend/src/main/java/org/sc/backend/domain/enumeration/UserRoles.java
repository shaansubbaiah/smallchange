package org.sc.backend.domain.enumeration;

/**
 * The UserRoles enumeration.
 */
public enum UserRoles {
    USER("USER"),
    ADMIN("ADMIN");

    private final String value;

    UserRoles(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
