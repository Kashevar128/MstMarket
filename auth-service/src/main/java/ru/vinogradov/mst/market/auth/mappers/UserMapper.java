package ru.vinogradov.mst.market.auth.mappers;

import org.springframework.stereotype.Component;
import ru.vinogradov.mst.market.api.UserDto;
import ru.vinogradov.mst.market.auth.entities.Role;
import ru.vinogradov.mst.market.auth.entities.User;
import ru.vinogradov.mst.market.auth.repositories.UserRepository;

import java.util.List;

@Component
public class UserMapper {
    private final UserRepository userRepository;

    public UserMapper(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDto mapUserToUserDto(User user) {
        UserDto userdto = UserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .role(user.getRoles().get(0).getName())
                .build();
        return userdto;
    }
}
