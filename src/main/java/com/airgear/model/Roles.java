package com.airgear.model;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Roles {
    USER,
    MODERATOR,
    ADMIN;

    @JsonValue
    public String getValue() {
        return this.toString();
    }
}
