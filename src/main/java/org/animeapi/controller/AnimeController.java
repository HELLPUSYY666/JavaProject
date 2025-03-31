package org.animeapi.controller;

import org.animeapi.model.Anime;
import org.animeapi.repository.AnimeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/anime")
public class AnimeController {

    @Autowired
    private AnimeRepository animeRepository;

    @GetMapping
    public List<Anime> getAllAnime() {
        return animeRepository.findAllAnime();
    }

    @GetMapping("/{id}")
    public Optional<Anime> getAnimeById(@PathVariable Integer id) {
        return animeRepository.findAnimeById(id);
    }

    @PostMapping
    public Anime createAnime(@RequestBody Anime anime) {
        return animeRepository.save(anime);
    }

    @PutMapping("/{id}")
    public Anime updateAnime(@PathVariable Integer id, @RequestBody Anime anime) {
        anime.setAnimeId(id);
        return animeRepository.save(anime);
    }

    @DeleteMapping("/{id}")
    public void deleteAnime(@PathVariable Integer id) {
        animeRepository.deleteById(id);
    }
}
