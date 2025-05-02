package org.animeapi.service;

import lombok.AllArgsConstructor;
import org.animeapi.model.MyUser;
import org.animeapi.repository.PasswordTokenRepository;
import org.animeapi.repository.UserRepository;
import org.animeapi.token.PasswordResetToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {

    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private PasswordTokenRepository passwordTokenRepository;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Optional<MyUser> user = userRepository.findUserByLogin(login);
        if (user.isPresent()) {
            var userObj = user.get();
            return User.builder()
                    .username(userObj.getLogin())
                    .password(userObj.getPassword())
                    .build();
        } else {
            throw new UsernameNotFoundException(login);
        }
    }

    public void createPasswordResetTokenForUser(MyUser user, String token) {
        // Генерация уникального токена, если не передан
        if (token == null || token.isEmpty()) {
            token = UUID.randomUUID().toString(); // Генерация уникального токена
        }

        // Создание объекта токена
        PasswordResetToken passwordResetToken = new PasswordResetToken(token, user);

        // Сохранение токена в базе данных
        passwordTokenRepository.save(passwordResetToken);
    }

    public List<MyUser> getAllUsers() {
        return userRepository.findAll(); // <-- добавлен метод
    }

    public Optional<MyUser> getUserById(Integer id) {
        return userRepository.getMyUserByUserId(id); // <-- добавлен метод
    }

    public void deleteUser(Integer id) {
        userRepository.deleteById(id); // <-- добавлен метод
    }

    public MyUser saveUser(MyUser user) {
        return userRepository.save(user); // <-- метод сохранения пользователя
    }
    public MyUser findUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    public void changePassword(MyUser user, String newPassword) {
        String encodedPassword = passwordEncoder.encode(newPassword);
        user.setPassword(encodedPassword);
        userRepository.save(user);
    }

    public Optional<MyUser> getUserByPasswordResetToken(String token) {
        // Находим токен сброса пароля по предоставленному значению
        PasswordResetToken resetToken = passwordTokenRepository.findByToken(token);

        // Если токен найден, возвращаем связанного с ним пользователя
        if (resetToken != null) {
            return Optional.of(resetToken.getUser());
        } else {
            // Если токен не найден, возвращаем пустой Optional
            return Optional.empty();
        }
    }
}
