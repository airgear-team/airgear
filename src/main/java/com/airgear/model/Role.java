package com.airgear.model;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Role {
    USER,
    MODERATOR,
    ADMIN;

    @JsonValue
    public String getValue() {
        return this.toString();
    }
}
