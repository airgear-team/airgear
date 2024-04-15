package com.airgear.entity;

import com.airgear.entity.EmailMessage;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class EmailsRequestStructure {
    private Set<String> addresses;
    private EmailMessage emailMessage;
}
