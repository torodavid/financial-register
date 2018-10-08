package com.torodavid.thesis.financialregister.dal.enums;

public enum Priority {
    ONE ("Egy"),
    TWO ("Kettő"),
    THREE ("Három");

    private final String name;

    Priority(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
