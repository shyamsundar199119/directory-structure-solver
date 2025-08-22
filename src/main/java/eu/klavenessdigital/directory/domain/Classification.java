package eu.klavenessdigital.directory.domain;

import static eu.klavenessdigital.directory.util.ValidationUtil.isNullOrEmpty;

public enum Classification {
    PUBLIC("Public"),
    SECRET("Secret"),
    TOP_SECRET("Top secret");

    private final String label;

    Classification(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    // Convert string to enum safely
    public static Classification fromString(String value) {
        if (isNullOrEmpty(value)) {
            return null; // To handle folder
        }

        for (Classification c : Classification.values()) {
            if (c.label.equalsIgnoreCase(value)) {
                return c;
            }
        }
        throw new IllegalArgumentException("Unknown classification: " + value);
    }
}
