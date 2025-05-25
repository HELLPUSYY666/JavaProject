package org.animeapi.repository;

import org.springframework.transaction.annotation.Transactional;
import org.animeapi.model.MyUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional(readOnly=true)
public interface UserRepository extends JpaRepository<MyUser, Integer> {

    List<MyUser> findAll();

    Optional<MyUser> findUserByLogin(String login);

    Optional<MyUser> getMyUserByUserId(Integer id);

    Optional<MyUser> findUserByEmail(String email);

}
