// Favorite Model
package org.animeapi.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Favorites")
@Data
@NoArgsConstructor
@AllArgsConstructor
@IdClass(FavoriteId.class)
public class Favorite {
    @Id
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Id
    @ManyToOne
    @JoinColumn(name = "anime_id", nullable = false)
    private Anime anime;
}