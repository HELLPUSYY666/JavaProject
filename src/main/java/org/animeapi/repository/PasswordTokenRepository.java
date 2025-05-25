package org.animeapi.repository;

import org.animeapi.token.PasswordResetToken;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PasswordTokenRepository extends CrudRepository<PasswordResetToken, Long> {

    PasswordResetToken findByToken(String token);
}
