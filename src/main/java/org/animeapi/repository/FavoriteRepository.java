package org.animeapi.repository;

// Favorite Repository

import org.animeapi.model.Favorite;
import org.animeapi.model.FavoriteId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FavoriteRepository extends JpaRepository<Favorite, FavoriteId> {
    @Query("SELECT f FROM Favorite f")
    List<Favorite> findAllFavorites();

    @Query("SELECT f FROM Favorite f WHERE f.user.userId = :userId AND f.anime.animeId = :animeId")
    Optional<Favorite> findFavoriteById(@Param("userId") Integer userId, @Param("animeId") Integer animeId);
}