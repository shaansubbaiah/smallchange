package org.sc.backend.domain.enumeration;

/**
 * The AssetType enumeration.
 */
public enum AssetType {
    STOCK("STOCK"),
    MUTUALFUND("MUTUALFUND"),
    BOND("BOND");

    private final String description;

    AssetType(String description) {
        this.description = description;
    }

    public String getValue() {
        return description;
    }
}
