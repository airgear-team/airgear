package com.airgear.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ComplaintCategory {
    FRAUD ("Fraud"),
    VIOLATION_OF_COPYRIGHT ("Violation of copyright"),
    UNACCEPTABLE_CONTENT ("Unacceptable content");

    private final String value;

}

