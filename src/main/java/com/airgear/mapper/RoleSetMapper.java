package com.airgear.mapper;

import com.airgear.dto.RoleDto;
import com.airgear.model.Role;
import org.mapstruct.Mapper;

import java.util.Set;

@Mapper(componentModel = "spring", uses = RoleMapper.class)
public interface RoleSetMapper {
    Set<RoleDto> toDtoSet(Set<Role> roles);

    Set<Role> toRoles(Set<RoleDto> dtos);
}
