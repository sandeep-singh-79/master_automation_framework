package com.sandeep.base.api;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public enum EndPoints {
    USERS("users");

    private String value;

    EndPoints(String value) {
        this.value = value;
    }

    public String toString() {
        return this.value;
    }
}
