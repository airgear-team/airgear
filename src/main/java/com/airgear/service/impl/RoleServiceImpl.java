package com.airgear.service.impl;

import com.airgear.repository.RoleRepository;
import com.airgear.model.Role;
import com.airgear.service.RoleService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service(value = "roleService")
@AllArgsConstructor
public class RoleServiceImpl implements RoleService {

    private RoleRepository roleRepository;

    @Override
    public Role findByName(String name) {
        return roleRepository.findRoleByName(name);
    }

}