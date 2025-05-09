package org.animeapi.service;

import org.animeapi.model.Anime;
import org.animeapi.model.Comment;
import org.animeapi.model.MyUser;
import org.animeapi.repository.AnimeRepository;
import org.animeapi.repository.CommentRepository;
import org.animeapi.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final AnimeRepository animeRepository;

    public CommentService(CommentRepository commentRepository, UserRepository userRepository, AnimeRepository animeRepository) {
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.animeRepository = animeRepository;
    }

    public Comment createComment(Integer userId, Integer animeId, String content, Integer rate) {
        MyUser user = userRepository.findById(userId).orElseThrow();
        Anime anime = animeRepository.findById(animeId).orElseThrow();

        Comment comment = new Comment(null, user, anime, content, rate);
        return commentRepository.save(comment);
    }

    public List<Comment> getCommentsByAnime(Integer animeId) {
        return commentRepository.findByAnime_AnimeId(animeId);
    }

    public List<Comment> getAllComments() {
        return commentRepository.findAll();
    }

    public void deleteComment(Integer id) {
        commentRepository.deleteById(id);
    }
}