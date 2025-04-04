package org.animeapi.service;

import org.animeapi.model.Anime;
import org.animeapi.repository.AnimeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AnimeService {
    private final AnimeRepository animeRepository;

    public AnimeService(AnimeRepository animeRepository) {
        this.animeRepository = animeRepository;
    }

    public List<Anime> getAllAnime() {
        return animeRepository.findAll();
    }

    public Optional<Anime> getAnimeById(Integer id) {
        return animeRepository.findById(id);
    }

    public Anime saveAnime(Anime anime) {
        return animeRepository.save(anime);
    }

    public void deleteAnime(Integer id) {
        animeRepository.deleteById(id);
    }
}
