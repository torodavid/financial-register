package com.torodavid.thesis.financialregister.dal.enums;

public enum FlowDirection {
    IN ("Bevetel"),
    OUT ("Kiadas");

    private final String name;

    FlowDirection(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
