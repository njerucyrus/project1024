package com.apps.project1024.models;

public enum UserType {
    STUDENT("Student"),
    PROFESSIONAL("Professional");
    public final String value;
    UserType(String value) {
        this.value = value;
    }
}
