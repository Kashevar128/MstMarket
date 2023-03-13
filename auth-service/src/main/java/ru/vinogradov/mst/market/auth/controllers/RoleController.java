package ru.vinogradov.mst.market.auth.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.vinogradov.mst.market.auth.services.RoleService;

import java.util.List;

@RestController
@RequestMapping("/forAdmin/roles")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    @GetMapping
    public List<String> getAllCategory() {
        return roleService.getAllRolesStr();
    }
}
