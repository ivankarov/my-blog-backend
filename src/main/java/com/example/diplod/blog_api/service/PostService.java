package com.example.diplod.blog_api.service;

import com.example.diplod.blog_api.dto.PostDTO;
import com.example.diplod.blog_api.entity.Comment;
import com.example.diplod.blog_api.entity.Post;
import com.example.diplod.blog_api.entity.User;
import com.example.diplod.blog_api.repository.PostRepository;
import com.example.diplod.blog_api.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class PostService{
    private final CommentService commentService;
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    public Post createPost(@RequestBody PostDTO dto, String name){
        User author = userRepository.findByUsername(name).get();
        Post post = new Post();
        post.setTitle(dto.getTitle());
        post.setContent(dto.getContent());
        post.setCreatedAt(LocalDateTime.now());
        post.setAuthor(author);
        return postRepository.save(post);
    }
    public Page<Post> getAllPosts(int page, int size){
        Pageable pageable = PageRequest.of(page,size, Sort.by("createdAt").descending());
        return postRepository.findAll(pageable);
    }
    public List<Post> getAllPosts(){
        return postRepository.findByOrderByCreatedAtDesc();
    }
    public Optional<Post> getPostById(Long id){
        return postRepository.findById(id);
    }
    public List<Post> getPostByUser(Long userId){
        return postRepository.findByAuthorId(userId);
    }
    public Post updatePost(Long id, PostDTO postDetails, String name){
        Post post = postRepository.findById(id).orElseThrow(() -> new RuntimeException("Post not found"));
        if (!post.getAuthor().getUsername().equals(name)){
            throw new RuntimeException("fuck you!");
        }
        post.setTitle(postDetails.getTitle());
        post.setContent(postDetails.getContent());
        return postRepository.save(post);
    }
    public void deletePost(Long id, String name){
        Post post = postRepository.findById(id).orElseThrow(()-> new RuntimeException("Post not found"));
        if (!post.getAuthor().getUsername().equals(name)){
            throw new RuntimeException("Fuck you!!!!");
        }
        if(!commentService.getAllComments().isEmpty()){
            this.deletePostWithComments(id,name);
        }
        postRepository.deleteById(id);
    }
    public List<Post> searchPosts(String title){
        return postRepository.findByTitleContainingIgnoreCase(title);
    }
    public void deletePostWithComments(Long postId, String name){
        List<Comment> comments = commentService.getCommentsByPost(postId);
        for (Comment comment : comments){
            commentService.deleteComment(comment.getId());
        }
        deletePost(postId, name);
    }
    public boolean existsById(Long id){
        return postRepository.existsById(id);
    }
}
