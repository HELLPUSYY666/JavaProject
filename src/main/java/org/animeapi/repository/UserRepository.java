package org.animeapi.repository;

// User Repository

import org.animeapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    @Query("SELECT u FROM User u")
    List<User> findAllUsers();

    @Query("SELECT u FROM User u WHERE u.userId = :id")
    Optional<User> findUserById(@Param("id") Integer id);
}