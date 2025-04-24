package org.animeapi.service;

import org.animeapi.model.MyUser;
import org.animeapi.repository.UserRepository;
import org.animeapi.repository.PasswordTokenRepository;
import org.animeapi.token.PasswordResetToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository repository;
    private final PasswordTokenRepository passwordTokenRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository repository, PasswordTokenRepository passwordTokenRepository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordTokenRepository = passwordTokenRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public MyUser findUserByEmail(String email) {
        return repository.findUserByEmail(email);
    }

    public void createPasswordResetTokenForUser(MyUser user, String token) {
        PasswordResetToken myToken = new PasswordResetToken(token, user);
        passwordTokenRepository.save(myToken);
    }

    public Optional<MyUser> getUserByPasswordResetToken(String token) {
        PasswordResetToken resetToken = passwordTokenRepository.findByToken(token);
        return Optional.ofNullable(resetToken != null ? resetToken.getUser() : null);
    }

    public void changeUserPassword(MyUser user, String newPassword) {
        user.setPassword(passwordEncoder.encode(newPassword));
        repository.save(user);
    }
}
