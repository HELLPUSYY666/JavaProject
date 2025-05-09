package org.animeapi.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Getter
@Setter
@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MyUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;

    @Column(nullable = false, length = 50)
    private String login; // <-- поле login, а не username

    @Column(nullable = false, unique = true, length = 50)
    private String email;

    @Column(nullable = false)
    private String password;

    public MyUser(String login, String email, String password) {
        this.login = login;
        this.email = email;
        this.password = password;
    }

    public UserDetails toUserDetails() {
        return new org.springframework.security.core.userdetails.User(
                this.login,                     // username (ваше поле login)
                this.password,                  // пароль
                true,                           // enabled
                true,                           // accountNonExpired
                true,                           // credentialsNonExpired
                true,                           // accountNonLocked
                getAuthorities()                // роли/права
        );
    }

    private Collection<? extends GrantedAuthority> getAuthorities() {
        // Пример: роль USER по умолчанию
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
    }
}
