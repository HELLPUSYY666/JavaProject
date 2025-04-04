package org.animeapi.controller;

import org.animeapi.model.Comment;
import org.animeapi.service.CommentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/comments")
public class CommentController {
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping
    public Comment addComment(@RequestBody Map<String, Object> payload) {
        Integer userId = (Integer) payload.get("userId");
        Integer animeId = (Integer) payload.get("animeId");
        String content = (String) payload.get("commentContent");
        Integer rate = (Integer) payload.get("commentRate");

        return commentService.createComment(userId, animeId, content, rate);
    }

    @GetMapping
    public List<Comment> getAllComments() {
        return commentService.getAllComments();
    }

    @GetMapping("/anime/{animeId}")
    public List<Comment> getCommentsByAnime(@PathVariable Integer animeId) {
        return commentService.getCommentsByAnime(animeId);
    }

    @DeleteMapping("/{id}")
    public void deleteComment(@PathVariable Integer id) {
        commentService.deleteComment(id);
    }
}
