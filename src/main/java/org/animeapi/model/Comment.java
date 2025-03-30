// Comment Model
package org.animeapi.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Comments")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer commentId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "anime_id", nullable = false)
    private Anime anime;

    @Column(nullable = false, length = 1000)
    private String commentContent;

    @Column(nullable = false)
    private Integer commentRate;
}