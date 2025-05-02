package org.animeapi.repository;
// Anime Repository

import org.animeapi.model.Anime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AnimeRepository extends JpaRepository<Anime, Integer> {
    @Query("SELECT a FROM Anime a")
    List<Anime> findAllAnime();

    @Query("SELECT a FROM Anime a WHERE a.animeId = :id")
    Optional<Anime> findAnimeById(@Param("id") Integer id);
}