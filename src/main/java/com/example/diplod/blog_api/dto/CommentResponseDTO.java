package com.example.diplod.blog_api.dto;

import com.example.diplod.blog_api.entity.Comment;
import lombok.Data;

import java.time.LocalDateTime;


@Data
public class CommentResponseDTO {
    private String author;
    private String text;
    private Long id;
    private LocalDateTime createdAt;

    public CommentResponseDTO(Comment comment){
        this.author = comment.getAuthor().getUsername();
        this.text = comment.getText();
        this.id = comment.getId();
        this.createdAt = comment.getCreatedAt();
    }
}
