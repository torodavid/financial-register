package com.torodavid.thesis.financialregister.dal.enums;

public enum Category {
    UNCATEGORIZED ("Nincs besorolva"),
    BILL ("Számla"),
    SALARY ("Fizetés"),
    ENTERTAINMENT ("Szórakozás"),
    TECH ("Tech");

    private final String name;

    Category(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
