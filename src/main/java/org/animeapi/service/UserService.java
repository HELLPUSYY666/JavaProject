package org.animeapi.service;

import lombok.AllArgsConstructor;
import org.animeapi.model.MyUser;
import org.animeapi.password.PasswordEncoder;
import org.animeapi.repository.PasswordTokenRepository;
import org.animeapi.repository.UserRepository;
import org.animeapi.token.PasswordResetToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {
    public static final String USER_NOT_FOUND_MSG = "User with email %s not found";

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder;

    private final PasswordTokenRepository passwordTokenRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findUserByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG, email)));
    }

    public void createPasswordResetTokenForUser(MyUser user, String token) {
        if (token == null || token.isEmpty()) {
            token = UUID.randomUUID().toString();
        }
        PasswordResetToken passwordResetToken = new PasswordResetToken(token, user);
        passwordTokenRepository.save(passwordResetToken);
    }

    public List<MyUser> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<MyUser> getUserById(Integer id) {
        return userRepository.getMyUserByUserId(id);
    }

    public void deleteUser(Integer id) {
        userRepository.deleteById(id);
    }

    public MyUser saveUser(MyUser user) {
        return userRepository.save(user);
    }
    public Optional<MyUser> findUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    public void changePassword(MyUser user, String newPassword) {
        String encodedPassword = passwordEncoder.encode(newPassword);
        user.setPassword(encodedPassword);
        userRepository.save(user);
    }

    public Optional<MyUser> getUserByPasswordResetToken(String token) {
        PasswordResetToken resetToken = passwordTokenRepository.findByToken(token);
        if (resetToken != null) {
            return Optional.of(resetToken.getUser());
        } else {
            return Optional.empty();
        }
    }
}
