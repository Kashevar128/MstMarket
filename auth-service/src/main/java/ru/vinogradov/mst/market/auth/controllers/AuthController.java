package ru.vinogradov.mst.market.auth.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import ru.vinogradov.mst.market.api.*;
import ru.vinogradov.mst.market.auth.entities.User;
import ru.vinogradov.mst.market.auth.mappers.UserMapper;
import ru.vinogradov.mst.market.auth.repositories.Specifications.UsersSpecifications;
import ru.vinogradov.mst.market.auth.services.UserService;

@RestController
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;
    private final UserMapper userMapper;

    @PostMapping("/authenticate")
    public ResponseEntity<?> createAuthToken(@RequestBody JwtRequest authRequest) {
        userService.auth(authRequest);
        UserDetails userDetails = userService.loadUserByUsername(authRequest.getUsername());
        JwtResponse jwtResponse = JwtResponse.builder()
                .token(userService.getToken(userDetails))
                .visibleAdminButton(userService.getAccessAdmin(authRequest.getUsername()))
                .build();
        userService.userFilter(authRequest.getUsername());
        return ResponseEntity.ok(jwtResponse);
    }

    @PostMapping("/registration")
    public ResponseEntity<?> createAuthToken(@RequestBody RegistrationUserDto registrationUserDto) {
        userService.reg(registrationUserDto);
        UserDetails userDetails = userService.loadUserByUsername(registrationUserDto.getUsername());
        JwtResponse jwtResponse = JwtResponse.builder()
                .token(userService.getToken(userDetails))
                .build();
        return ResponseEntity.ok(jwtResponse);
    }


}
