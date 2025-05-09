package org.animeapi.controller;

import org.animeapi.model.Anime;
import org.animeapi.service.AnimeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/anime")
public class AnimeController {
    private final AnimeService animeService;

    public AnimeController(AnimeService animeService) {
        this.animeService = animeService;
    }

    @GetMapping
    public List<Anime> getAllAnime() {
        return animeService.getAllAnime();
    }

    @GetMapping("/{id}")
    public Optional<Anime> getAnimeById(@PathVariable Integer id) {
        return animeService.getAnimeById(id);
    }

    @PostMapping
    public Anime createAnime(@RequestBody Anime anime) {
        return animeService.saveAnime(anime);
    }

    @DeleteMapping("/{id}")
    public void deleteAnime(@PathVariable Integer id) {
        animeService.deleteAnime(id);
    }
}