// User Model
package org.animeapi.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;

    @Column(nullable = false, length = 50)
    private String login;

    @Column(nullable = false, unique = true, length = 50)
    private String email;

    @Column(nullable = false, length = 50)
    private String password;
}