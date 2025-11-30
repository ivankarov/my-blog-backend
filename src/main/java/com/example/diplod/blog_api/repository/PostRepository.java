package com.example.diplod.blog_api.repository;

import com.example.diplod.blog_api.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post,Long> {
    List<Post> findByAuthorId(Long userId);
    List<Post> findByOrderByCreatedAtDesc();
    List<Post> findByTitleContainingIgnoreCase(String title);
}
