package com.tsa.dbclient.domain.util;

public enum SqlMethod {
    SHOW("SHOW"),
    SELECT("SELECT"),
    INSERT("INSERT"),
    UPDATE("UPDATE"),
    DELETE("DELETE");

    private final String description;

    SqlMethod(String description) {
        this.description = description;
    }
    public String getDescription() {
        return description;
    }
}
