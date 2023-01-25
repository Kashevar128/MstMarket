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

//    @GetMapping
//    public Page<ProductDto> getAllUsers(
//            @RequestParam(name = "p", defaultValue = "1") Integer page,
//            @RequestParam(name = "page_size", defaultValue = "10") Integer pageSize,
//            @RequestParam(name = "title_part", required = false) String titlePart
//    ) {
//        if (page < 1) {
//            page = 1;
//        }
//        Specification<Product> spec = Specification.where(null);
//        if (titlePart != null) {
//            spec = spec.and(ProductsSpecifications.titleLike(titlePart));
//        }
//        if (minPrice != null) {
//            spec = spec.and(ProductsSpecifications.priceGreaterOrEqualsThan(BigDecimal.valueOf(minPrice)));
//        }
//        if (maxPrice != null) {
//            spec = spec.and(ProductsSpecifications.priceLessThanOrEqualsThan(BigDecimal.valueOf(maxPrice)));
//        }
//        return productService.findAll(page - 1, pageSize, spec).map(productMapper::mapProductToProductDto);
//    }
//
    @GetMapping("/listUsers")
    public Page<UserDto> getAllUsers(
            @RequestParam(name = "p", defaultValue = "1") Integer page,
            @RequestParam(name = "page_size", defaultValue = "5") Integer pageSize,
            @RequestParam(name = "title_part", required = false) String titlePart
    ) {
        if (page < 1) {
            page = 1;
        }
        Specification<User> spec = Specification.where(null);
        if (titlePart != null) {
            spec = spec.and(UsersSpecifications.titleLike(titlePart));
        }
        return userService.findAll(page - 1, pageSize, spec).map(userMapper::mapUserToUserDto);
    }
}
