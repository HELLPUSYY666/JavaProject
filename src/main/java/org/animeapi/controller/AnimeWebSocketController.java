package org.animeapi.controller;

import lombok.RequiredArgsConstructor;
import org.animeapi.model.Anime;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class AnimeWebSocketController {

    @MessageMapping("/send")
    @SendTo("/topic/animes")
    public Anime sendAnime(Anime anime) {
        return anime;
    }
}
