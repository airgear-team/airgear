package com.airgear.mapper;

import com.airgear.model.ComplaintCategory;
import org.mapstruct.Mapper;
import org.mapstruct.ValueMapping;
import org.mapstruct.ValueMappings;

@Mapper(componentModel = "spring")
public interface ComplaintCategoryMapper {

    @ValueMappings({
            @ValueMapping(source = "Fraud", target = "FRAUD"),
            @ValueMapping(source = "Violation of copyright", target = "VIOLATION_OF_COPYRIGHT"),
            @ValueMapping(source = "Unacceptable content", target = "UNACCEPTABLE_CONTENT")
    })
    ComplaintCategory stringToEnum(String value);

    @ValueMappings({
            @ValueMapping(source = "FRAUD", target = "Fraud"),
            @ValueMapping(source = "VIOLATION_OF_COPYRIGHT", target = "Violation of copyright"),
            @ValueMapping(source = "UNACCEPTABLE_CONTENT", target = "Unacceptable content")
    })
    String enumToString(ComplaintCategory complaintCategory);
}
