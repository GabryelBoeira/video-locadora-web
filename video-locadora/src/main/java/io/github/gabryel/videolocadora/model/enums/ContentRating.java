package io.github.gabryel.videolocadora.model.enums;

import java.util.Arrays;

public  enum ContentRating {

    FREE("FREE", 0),
    PG_10("PG_10", 10),
    PG_13("PG_13", 13),
    AGE_16("AGE_16", 16),
    AGE_18("AGE_18", 18);

    private final String dbValue;
    private final int minAge;

    ContentRating(String dbValue, int minAge) {
        this.dbValue = dbValue;
        this.minAge = minAge;
    }

    public String dbValue() {
        return dbValue;
    }

    public int minAge() {
        return minAge;
    }

    public static ContentRating fromDbValue(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("content_rating vazio/nulo");
        }

        String normalized = value.trim().toUpperCase();

        return Arrays.stream(values())
                .filter(v -> v.dbValue.equalsIgnoreCase(normalized))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("content_rating inválido: " + value));
    }

}