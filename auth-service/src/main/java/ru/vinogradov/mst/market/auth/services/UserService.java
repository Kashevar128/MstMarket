package ru.vinogradov.mst.market.auth.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vinogradov.mst.market.api.JwtRequest;
import ru.vinogradov.mst.market.api.RegistrationUserDto;
import ru.vinogradov.mst.market.api.UserDto;
import ru.vinogradov.mst.market.auth.entities.Role;
import ru.vinogradov.mst.market.auth.entities.User;
import ru.vinogradov.mst.market.auth.exceptions.DontMatchPasswordsException;
import ru.vinogradov.mst.market.auth.exceptions.IncorrectLoginOrPasswordException;
import ru.vinogradov.mst.market.auth.exceptions.IncorrectRoleUserException;
import ru.vinogradov.mst.market.auth.exceptions.TheUserAlreadyExistsException;
import ru.vinogradov.mst.market.auth.repositories.UserRepository;
import ru.vinogradov.mst.market.auth.utils.JwtTokenUtil;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final RoleService roleService;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final PasswordEncoder passwordEncoder;

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(String.format("User '%s' not found", username)));
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), mapRolesToAuthorities(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }

    public void createUser(User user) {
        user.setRoles(List.of(roleService.getUserRole()));
        userRepository.save(user);
    }

    public void auth(JwtRequest authRequest) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        } catch (BadCredentialsException e) {
            throw new IncorrectLoginOrPasswordException("Некорректный логин или пароль");
        }
    }

    public void reg(RegistrationUserDto registrationUserDto) {
        if (!registrationUserDto.getPassword().equals(registrationUserDto.getConfirmPassword())) {
            throw new DontMatchPasswordsException("Пароли не совпадают");
        }
        if (findByUsername(registrationUserDto.getUsername()).isPresent()) {
            throw new TheUserAlreadyExistsException("Пользователь с таким именем уже существует");
        }
        User user = new User();
        user.setEmail(registrationUserDto.getEmail());
        user.setUsername(registrationUserDto.getUsername());
        user.setPassword(passwordEncoder.encode(registrationUserDto.getPassword()));
        createUser(user);
    }

    public String getToken(UserDetails userDetails) {
        return jwtTokenUtil.generateToken(userDetails);
    }

    public Collection<Role> getRolesUser(String username) {
        return userRepository.findByUsername(username).get().getRoles();
    }

    public boolean getAccessAdmin(String username) {
        Collection<Role> rolesUser = userRepository.findByUsername(username).get().getRoles();
        for (Role role : rolesUser) {
            if (role.getName().equals("ROLE_ADMIN")) return true;
        }
        return false;
    }

    public Page<User> findAll(int page, int pageSize, Specification<User> specification) {
        return userRepository.findAll(specification, PageRequest.of(page, pageSize));
    }

    public void roleEdit(UserDto userDto) {
        for (Role role : roleService.getAllRoles()) {
            if (role.getName().equals(userDto.getRole())) {
                User editUser = userRepository.deleteAndEdit(userDto.getId(), role);
                userRepository.save(editUser);
                return;
            }
        }
        throw new IncorrectRoleUserException("Такой роли не существует!");
    }
}