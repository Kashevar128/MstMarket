package ru.vinogradov.mst.market.auth.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.vinogradov.mst.market.auth.entities.Role;
import ru.vinogradov.mst.market.auth.repositories.RoleRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;

    public Role getUserRole() {
        return roleRepository.findByName("ROLE_USER").get();
    }

    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }
}