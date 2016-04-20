package com.github.aksakalli.todo.repository;

import com.github.aksakalli.todo.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

/**
 * Spring Data JPA repository for the User entity.
 */
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findOneByUsername(@Param("username")
                                     String username);

    Optional<User> findOneByEmail(@Param("email")
                                  String email);

    Optional<User> findOneById(@Param("id")
                               Long id);

}
