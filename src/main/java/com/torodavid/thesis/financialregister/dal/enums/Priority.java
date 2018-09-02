package com.torodavid.thesis.financialregister.dal.enums;

public enum Priority {
    ONE ("Egy"),
    TWO ("Ketto"),
    THREE ("Harom");

    private final String name;

    Priority(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
