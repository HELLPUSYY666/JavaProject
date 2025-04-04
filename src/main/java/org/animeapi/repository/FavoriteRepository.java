package org.animeapi.repository;

import org.animeapi.model.Favorite;
import org.animeapi.model.FavoriteId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FavoriteRepository extends JpaRepository<Favorite, FavoriteId> {

    List<Favorite> findByUser_UserId(Integer userId);

    void deleteByUser_UserIdAndAnime_AnimeId(Integer userId, Integer animeId);

    boolean existsByUser_UserIdAndAnime_AnimeId(Integer userId, Integer animeId);
}
