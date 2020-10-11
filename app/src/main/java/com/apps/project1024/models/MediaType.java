package com.apps.project1024.models;

public enum MediaType {
    TEXT("text"),
    IMAGE("image"),
    FILE("file");

    public final String value;
    MediaType(String value) {
        this.value = value;
    }
}
