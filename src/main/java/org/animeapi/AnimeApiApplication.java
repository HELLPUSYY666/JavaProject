package org.animeapi;

import jakarta.annotation.PostConstruct;
import org.animeapi.parser.AnimeParserService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AnimeApiApplication {

    private final AnimeParserService animeParserService;

    public AnimeApiApplication(AnimeParserService animeParserService) {
        this.animeParserService = animeParserService;
    }

    public static void main(String[] args) {
        SpringApplication.run(AnimeApiApplication.class, args);
    }

    @PostConstruct
    public void init() {
        animeParserService.parseAndSaveTopAnime(10); // Парсит 10 аниме при старте
    }
}
