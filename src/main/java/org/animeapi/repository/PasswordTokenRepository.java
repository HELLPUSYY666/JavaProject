package org.animeapi.repository;

import org.animeapi.model.MyUser;
import org.animeapi.token.PasswordResetToken;
import org.springframework.data.repository.CrudRepository;

public interface PasswordTokenRepository extends CrudRepository<PasswordResetToken, Long> {

    PasswordResetToken findByToken(String token);
}
