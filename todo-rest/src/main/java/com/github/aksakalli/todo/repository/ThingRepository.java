package com.github.aksakalli.todo.repository;

import com.github.aksakalli.todo.domain.Thing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

/**
 * Spring Data JPA repository for the Thing entity.
 */
public interface ThingRepository extends JpaRepository<Thing, Long> {

    Optional<Thing> findOneById(@Param("id") Long id);
}
