package com.mckinsey.util.api;

import lombok.extern.slf4j.Slf4j;

@Slf4j
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
