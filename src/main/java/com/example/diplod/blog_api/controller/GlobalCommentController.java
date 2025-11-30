package com.example.diplod.blog_api.controller;


import com.example.diplod.blog_api.entity.Comment;
import com.example.diplod.blog_api.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comments")
public class GlobalCommentController {
    private final CommentService commentService;
    @GetMapping
    public List<Comment> getAllComments(){
        return commentService.getAllComments();
    }
}
