package com.khanakirana.backend.utils;

/**
 * Created by vavasthi on 5/10/15.
 */
public enum AccountType {
    CUSTOMER(1, "CustomerAccount"),
    BUSINESS(2, "BusinessAccount"),
    SYSADMIN(99, "SystemAdministrator");

    public final int type;
    public final String name;

    private AccountType(int type, String name) {
        this.type = type;
        this.name = name;
    }
    public int getType() {
        return type;
    }
    public String getName() {
        return name;
    }
    public static AccountType createFromValues(int type, String name) {
        for (AccountType at : AccountType.values()) {
            if (at.type == type && at.name.equalsIgnoreCase(name)) {
                return at;
            }
        }
        throw new IllegalArgumentException();
    }
    public static AccountType createFromValues(int type) {
        for (AccountType at : AccountType.values()) {
            if (at.type == type) {
                return at;
            }
        }
        throw new IllegalArgumentException();
    }
}
