package com.example.jwtlogin.configBeans;

import com.example.jwtlogin.models.ERole;
import com.example.jwtlogin.models.Role;
import com.example.jwtlogin.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

public class ConfigureRoles {
    @Autowired
    private RoleRepository roleRepository;

    @PostConstruct
    public void createRoles(){
        if(!roleRepository.existsById(1L)){
            roleRepository.save(Role.builder().role(ERole.ROLE_USER).build());
            roleRepository.save(Role.builder().role(ERole.ROLE_ADMIN).build());
        }
    }
}
