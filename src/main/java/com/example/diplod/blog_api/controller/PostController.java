package com.example.diplod.blog_api.controller;


import com.example.diplod.blog_api.dto.PostDTO;
import com.example.diplod.blog_api.entity.Post;
import com.example.diplod.blog_api.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class PostController {
    private final PostService postService;
    @GetMapping
    public ResponseEntity<Page<Post>> getAllPosts(@RequestParam(defaultValue="0", required=false) int page,@RequestParam(defaultValue="10", required=false) int size){
        Page<Post> posts = postService.getAllPosts(page,size);
        return ResponseEntity.ok(posts);
    }
    @GetMapping("/all")
    public ResponseEntity<List<Post>> getAllPostsWithoutPagination(){
        List<Post> posts=postService.getAllPosts();
        return ResponseEntity.ok(posts);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Post> getPostById(@PathVariable Long id){
        return postService.getPostById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
    @PostMapping
    public ResponseEntity<Post> createPost(@Valid @RequestBody PostDTO dto){
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        Post createdPost=postService.createPost(dto,name);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPost);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Post> updatePost(@PathVariable Long id, @Valid @RequestBody PostDTO postDetails){
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        try{
            Post updatePost=postService.updatePost(id,postDetails,name);
            return ResponseEntity.ok(updatePost);
        }catch(RuntimeException e){
            if (e.getMessage().contains("fuck you")){
                return ResponseEntity.status(403).build();
            }
            return ResponseEntity.notFound().build();
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id){
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        try{
            postService.deletePost(id,name);
        }catch(RuntimeException e){
            if (e.getMessage().contains("not found")){
                return ResponseEntity.notFound().build();
            }
            if(e.getMessage().contains("Fuck you!!!!")){
                return ResponseEntity.status(403).build();
            }
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.notFound().build();
    }
    @GetMapping("/search")
    public ResponseEntity<List<Post>> searchPosts(@RequestParam String title){
        List<Post> posts=postService.searchPosts(title);
        return ResponseEntity.ok(posts);
    }
}
