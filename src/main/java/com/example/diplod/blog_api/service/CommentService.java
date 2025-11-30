package com.example.diplod.blog_api.service;

import com.example.diplod.blog_api.dto.CommentDTO;
import com.example.diplod.blog_api.entity.Comment;
import com.example.diplod.blog_api.entity.Post;
import com.example.diplod.blog_api.entity.User;
import com.example.diplod.blog_api.repository.CommentRepository;
import com.example.diplod.blog_api.repository.PostRepository;
import com.example.diplod.blog_api.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentService {
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    public Comment createComment(CommentDTO dto, Post post, String name){
        User author = Optional.ofNullable(userRepository.findByUsername(name).orElseThrow(() -> new RuntimeException("User not found with username: " + name))).get();
        Comment comment = new Comment();
        comment.setPost(post);
        comment.setAuthor(author);
        comment.setText(dto.getText());
        return commentRepository.save(comment);
    }
    public List<Comment> getAllComments(){
        return commentRepository.findAll();
    }
    public List<Comment> getCommentsByPost(Long postId){
        return commentRepository.findByPostIdOrderByCreatedAtDesc(postId);
    }
    public List<Comment> getCommentsByUser(Long userId){
        return commentRepository.findByAuthorId(userId);
    }
    public List<Comment> getCommentById(Long id){
        return commentRepository.findByPostId(id);
    }
    public Comment updateComment(Long id, String newText,String authorUsername){
        String realName=commentRepository.findById(id).get().getAuthor().getUsername();
        if (!realName.equals(authorUsername)){
            throw new RuntimeException("get the FUCK out here!");
        }
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new RuntimeException("comment not found"));
        comment.setText(newText);
        return commentRepository.save(comment);
    }
    public void deleteComment(Long id){
        commentRepository.deleteById(id);
    }
    public boolean isCommentOwner(Long commentId, Long userId){
        return commentRepository.findById(commentId).map(comment -> comment.getAuthor().getId().equals(userId)).orElse(false);
    }
    public long getCommentCountForPost(Long postId){
        return commentRepository.findByPostId(postId).size();
    }
    public boolean isCommentBelongsToPost(Long commentId, Long postId){
       List<Comment> commentsOnPost =  this.getCommentsByPost(postId);
       for (Comment comment: commentsOnPost){
           if (commentId.equals(comment.getId())){
               return true;
           }
       }return false;
    }
}
