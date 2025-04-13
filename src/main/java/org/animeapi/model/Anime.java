// Anime Model
package org.animeapi.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Anime")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Anime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer animeId;

    @Column(nullable = false, length = 255)
    private String photoUrl;

    @Column(nullable = false, length = 255)
    private String animeName;

    @Column(length = 255)
    private String animeGenre;

    private Integer animeRating;

    @Column(length = 1000)
    private String animeDescription;
}