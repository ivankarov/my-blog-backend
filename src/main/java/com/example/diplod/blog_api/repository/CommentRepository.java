package com.example.diplod.blog_api.repository;

import com.example.diplod.blog_api.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment,Long> {
    List<Comment> findByPostId(Long postId);
    List<Comment> findByAuthorId(Long userId);
    List<Comment> findByPostIdOrderByCreatedAtDesc(Long postId);

}
