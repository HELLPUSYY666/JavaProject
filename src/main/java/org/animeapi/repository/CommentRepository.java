package org.animeapi.repository;

// Comment Repository

import org.animeapi.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
    @Query("SELECT c FROM Comment c")
    List<Comment> findAllComments();

    @Query("SELECT c FROM Comment c WHERE c.commentId = :id")
    Optional<Comment> findCommentById(@Param("id") Integer id);
}