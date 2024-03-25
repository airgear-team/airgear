package com.airgear.mapper;

import com.airgear.dto.RoleDto;
import com.airgear.model.Role;
import org.mapstruct.Mapper;

import java.util.Set;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    RoleDto toDto(Role role);

    Role toModel(RoleDto dto);

    Set<RoleDto> toDtoSet(Set<Role> roles);

    Set<Role> toModelSet(Set<RoleDto> dtos);
}
