package com.airgear.mapper;

import com.airgear.dto.RoleDto;
import com.airgear.model.Role;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    RoleDto toDto(Role role);

    Role toRole(RoleDto dto);
}
