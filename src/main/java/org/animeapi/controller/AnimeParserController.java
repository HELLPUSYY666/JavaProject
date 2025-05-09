package org.animeapi.controller;

import lombok.RequiredArgsConstructor;
import org.animeapi.parser.AnimeParserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/parser")
@RequiredArgsConstructor
public class AnimeParserController {

    private final AnimeParserService parserService;

    @PostMapping("/run")
    public String runParser(@RequestParam(defaultValue = "10") int count) {
        parserService.parseAndSaveTopAnime(count);
        return "Парсинг запущен!";
    }
}
