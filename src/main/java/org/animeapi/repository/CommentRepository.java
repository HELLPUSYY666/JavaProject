package org.animeapi.repository;

import org.animeapi.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
    List<Comment> findByAnime_AnimeId(Integer animeId);
}