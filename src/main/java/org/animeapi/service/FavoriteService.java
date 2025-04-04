package org.animeapi.service;

import org.animeapi.model.Anime;
import org.animeapi.model.Favorite;
import org.animeapi.model.User;
import org.animeapi.repository.AnimeRepository;
import org.animeapi.repository.FavoriteRepository;
import org.animeapi.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FavoriteService {

    private final FavoriteRepository favoriteRepository;
    private final UserRepository userRepository;
    private final AnimeRepository animeRepository;

    public FavoriteService(FavoriteRepository favoriteRepository, UserRepository userRepository, AnimeRepository animeRepository) {
        this.favoriteRepository = favoriteRepository;
        this.userRepository = userRepository;
        this.animeRepository = animeRepository;
    }

    public Favorite addFavorite(Integer userId, Integer animeId) {
        User user = userRepository.findById(userId).orElseThrow();
        Anime anime = animeRepository.findById(animeId).orElseThrow();

        Favorite favorite = new Favorite(user, anime);
        return favoriteRepository.save(favorite);
    }

    public void removeFavorite(Integer userId, Integer animeId) {
        favoriteRepository.deleteByUser_UserIdAndAnime_AnimeId(userId, animeId);
    }

    public boolean isFavorite(Integer userId, Integer animeId) {
        return favoriteRepository.existsByUser_UserIdAndAnime_AnimeId(userId, animeId);
    }

    public List<Anime> getFavoriteAnimeByUser(Integer userId) {
        return favoriteRepository.findByUser_UserId(userId)
                .stream()
                .map(Favorite::getAnime)
                .collect(Collectors.toList());
    }
}

