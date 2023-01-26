package ru.vinogradov.mst.market.auth.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.vinogradov.mst.market.auth.entities.Role;
import ru.vinogradov.mst.market.auth.entities.User;
import ru.vinogradov.mst.market.auth.exceptions.ResourceNotFoundException;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

    Optional<User> findByUsername(String username);

    @Transactional
    default User deleteAndEdit(Long id, Role role) {
        User user = findById(id).orElseThrow(() -> new ResourceNotFoundException(String.format("Пользователь %d не найден ", id)));
        delete(user);
        List<Role> roles = user.getRoles();
        roles.clear();
        roles.add(role);
        user.setRoles(roles);
        user.setId(id);
        return user;
    };
}