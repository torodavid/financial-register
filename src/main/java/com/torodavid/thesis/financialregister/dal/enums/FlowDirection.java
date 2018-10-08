package com.torodavid.thesis.financialregister.dal.enums;

public enum FlowDirection {
    IN ("Bevétel"),
    OUT ("Kiadás");

    private final String name;

    FlowDirection(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
