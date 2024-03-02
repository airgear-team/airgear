package com.airgear.model.email;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class EmailsRequestStructure {
    private Set<String> addresses;
    private EmailMessage emailMessage;
}
