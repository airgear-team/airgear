package com.airgear.dto;

import com.airgear.model.Role;
import lombok.Builder;
import lombok.Data;

import java.util.*;

/**
 * RoleDto class. Fields are similar to Role entity.
 * Contains methods for translating entity into DTO and vice versa.
 *
 * @author Oleksandr Panchenko
 * @version 1.0
 */
@Data
@Builder
public class RoleDto {
    private Long id;
    private String name;
    private String description;

    public Role toRole() {
        return Role.builder()
                .id(id)
                .name(name)
                .description(description)
                .build();
    }

    public static Set<Role> toRoles(Set<RoleDto> roles) {
        Set<Role> result = new HashSet<>();
        roles.forEach(roleDto -> result.add(roleDto.toRole()));
        return result;
    }

    public static RoleDto fromRole(Role role) {
        return RoleDto.builder()
                .id(role.getId())
                .name(role.getName())
                .description(role.getDescription())
                .build();
    }

    public static Set<RoleDto> fromRoles(Set<Role> roles) {
        Set<RoleDto> result = new HashSet<>();
        roles.forEach(role -> result.add(RoleDto.fromRole(role)));
        return result;
    }
}
