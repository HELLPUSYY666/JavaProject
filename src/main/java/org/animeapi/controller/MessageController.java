package org.animeapi.controller;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class MessageController {

    @MessageMapping("/anime")
    @SendTo("/topic/animes")
    public String send(String message) {
        System.out.println("📨 Получено сообщение: " + message);
        return "Эхо: " + message;
    }
}
