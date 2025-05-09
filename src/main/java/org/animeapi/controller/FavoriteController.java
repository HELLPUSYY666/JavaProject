package org.animeapi.controller;

import org.animeapi.model.Anime;
import org.animeapi.model.Favorite;
import org.animeapi.service.FavoriteService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/favorites")
public class FavoriteController {

    private final FavoriteService favoriteService;

    public FavoriteController(FavoriteService favoriteService) {
        this.favoriteService = favoriteService;
    }

    @PostMapping
    public Favorite addFavorite(@RequestBody Map<String, Integer> payload) {
        return favoriteService.addFavorite(payload.get("userId"), payload.get("animeId"));
    }

    @DeleteMapping
    public void removeFavorite(@RequestBody Map<String, Integer> payload) {
        favoriteService.removeFavorite(payload.get("userId"), payload.get("animeId"));
    }

    @GetMapping("/user/{userId}")
    public List<Anime> getUserFavorites(@PathVariable Integer userId) {
        return favoriteService.getFavoriteAnimeByUser(userId);
    }

    @GetMapping("/check")
    public boolean isFavorite(@RequestParam Integer userId, @RequestParam Integer animeId) {
        return favoriteService.isFavorite(userId, animeId);
    }
}