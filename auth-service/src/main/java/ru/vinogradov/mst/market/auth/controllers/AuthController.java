package ru.vinogradov.mst.market.auth.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import ru.vinogradov.mst.market.api.JwtRequest;
import ru.vinogradov.mst.market.api.JwtResponse;
import ru.vinogradov.mst.market.api.RegistrationUserDto;
import ru.vinogradov.mst.market.auth.services.UserService;

@RestController
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;

    @PostMapping("/authenticate")
    public ResponseEntity<?> createAuthToken(@RequestBody JwtRequest authRequest) {
        userService.auth(authRequest);
        UserDetails userDetails = userService.loadUserByUsername(authRequest.getUsername());
        return ResponseEntity.ok(new JwtResponse(userService.getToken(userDetails)));
    }

    @PostMapping("/registration")
    public ResponseEntity<?> createAuthToken(@RequestBody RegistrationUserDto registrationUserDto) {
        userService.reg(registrationUserDto);
        UserDetails userDetails = userService.loadUserByUsername(registrationUserDto.getUsername());
        return ResponseEntity.ok(new JwtResponse(userService.getToken(userDetails)));
    }
}
