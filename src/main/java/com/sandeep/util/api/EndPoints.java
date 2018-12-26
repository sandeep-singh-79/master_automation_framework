package com.sandeep.util.api;

public enum EndPoints {
    v1("v1");

    private String value;

    EndPoints(String value) {
        this.value = value;
    }

    public String toString() {
        return this.value;
    }
}
