package org.animeapi.repository;

// User Repository

import org.animeapi.model.MyUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<MyUser, Integer> {
    @Query("SELECT u FROM MyUser u")
    List<MyUser> findAllUsers();

    @Query("SELECT u FROM MyUser u WHERE u.userId = :id")
    Optional<MyUser> findUserById(@Param("id") Integer id);

    Optional<MyUser> findUserByLogin(String login);

    Optional<MyUser> getMyUserByUserId(Integer id);

    MyUser findUserByEmail(String email);
}