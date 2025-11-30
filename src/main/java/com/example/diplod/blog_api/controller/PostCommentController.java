package com.example.diplod.blog_api.controller;

import com.example.diplod.blog_api.dto.CommentDTO;
import com.example.diplod.blog_api.dto.CommentResponseDTO;
import com.example.diplod.blog_api.entity.Comment;
import com.example.diplod.blog_api.entity.Post;
import com.example.diplod.blog_api.service.CommentService;
import com.example.diplod.blog_api.service.PostService;
import com.example.diplod.blog_api.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts/{postId}/comments")
public class PostCommentController {
    private final CommentService commentService;
    private final PostService postService;
    private final UserService userService;
    @GetMapping
    public ResponseEntity<List<CommentResponseDTO>> getPostComments(@PathVariable Long postId){
        if (!postService.existsById(postId)){
            return ResponseEntity.notFound().build();
        }List<Comment> comments = commentService.getCommentsByPost(postId);
        List<CommentResponseDTO> responseDTOs = comments.stream().map(CommentResponseDTO::new).collect(Collectors.toList());
        return ResponseEntity.ok(responseDTOs);
    }
    @PostMapping
    public ResponseEntity<CommentResponseDTO> addComment(@PathVariable Long postId, @RequestBody CommentDTO dto){
        Optional<Post> optionalPost = postService.getPostById(postId);
        if (optionalPost.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        Post post=optionalPost.get();
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        try{
            Comment createdComment=commentService.createComment(dto,post,name);
            CommentResponseDTO responseDto = new CommentResponseDTO(createdComment);
            return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
        }catch(RuntimeException e){
            return ResponseEntity.badRequest().build();
        }
    }
    @PostMapping("/{commentId}/update")
    public ResponseEntity<CommentResponseDTO> updateComment(@PathVariable Long commentId, @RequestBody String newText){
        String authorUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        try{
            Comment comment = commentService.updateComment(commentId,newText,authorUsername);
            CommentResponseDTO responseDTO = new CommentResponseDTO(comment);
            return ResponseEntity.ok().body(responseDTO);
        }catch(RuntimeException e){
            return ResponseEntity.badRequest().build();
        }

    }
    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long commentId, @PathVariable Long postId){
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        Long userId = userService.findByUsername(name).get().getId();
        if (!commentService.isCommentBelongsToPost(commentId,postId) || !commentService.isCommentOwner(commentId,userId)){
            return ResponseEntity.notFound().build();
        }
            commentService.deleteComment(commentId);
        return ResponseEntity.noContent().build();
    }
}

