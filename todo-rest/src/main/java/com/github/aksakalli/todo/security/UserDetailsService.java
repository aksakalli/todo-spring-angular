package com.github.aksakalli.todo.security;

import com.github.aksakalli.todo.domain.User;
import com.github.aksakalli.todo.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.Collections;
import java.util.Optional;

/**
 * Authenticate a user from the database.
 */
@Component("userDetailsService")
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    private final Logger log = LoggerFactory.getLogger(UserDetailsService.class);

    @Inject
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        log.debug("Authenticating {}", username);
        String lowercaseUsername = username.toLowerCase();
        Optional<User> userFromDatabase = userRepository.findOneByUsername(lowercaseUsername);
        return userFromDatabase.map(user -> {
            return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), Collections.<GrantedAuthority>emptyList());

        }).orElseThrow(() -> new UsernameNotFoundException("User " + username + " was not found in the database"));

    }

}

