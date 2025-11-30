package com.example.diplod.blog_api.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name="comments")
@Data
@NoArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    @Column(columnDefinition = "TEXT",nullable=false)
    private String text;
    @CreationTimestamp
    @Column(name="created_at",nullable = false,updatable=false)
    private LocalDateTime createdAt;
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="post_id",nullable=false)
    private Post post;
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="user_id",nullable=false)
    private User author;
}
