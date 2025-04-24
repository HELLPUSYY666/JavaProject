package org.animeapi.service;

import java.util.Optional;

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

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository repository;
    @Autowired
    private PasswordTokenRepository passwordTokenRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Optional<MyUser> user = repository.findUserByLogin(login);
        if (user.isPresent()) {
            var userObj = user.get();
            return User.builder()
                    .username(userObj.getLogin())
                    .password(userObj.getPassword())
                    .build();
        }
        else{
            throw new UsernameNotFoundException(login);
        }
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
        userRepository.save(user);
    }

}