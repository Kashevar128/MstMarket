package ru.vinogradov.mst.market.auth.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.vinogradov.mst.market.auth.entities.Role;
import ru.vinogradov.mst.market.auth.repositories.RoleRepository;

import java.util.List;
import java.util.stream.Collectors;

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

    public List<String> getAllRolesStr() {
        return roleRepository.findAll().stream().map(Role::getName).toList();
    }
}