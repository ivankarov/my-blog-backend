package com.example.diplod.blog_api.service;

import com.example.diplod.blog_api.dto.UserDTO;
import com.example.diplod.blog_api.entity.User;
import com.example.diplod.blog_api.repository.UserRepository;
import com.example.diplod.blog_api.security.CustomUserDetailsService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {
    private final CommentService commentService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    public User createUser(User user){
        if (userRepository.existsByUsername(user.getUsername())){
            throw new RuntimeException("username already exists");
        }
        if (userRepository.existsByEmail(user.getEmail())){
            throw new RuntimeException("Email already exists");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }
    public Optional<User> findByUsername(String username){
        return userRepository.findByUsername(username);
    }
    public Optional<User> findById(Long id){
        return userRepository.findById(id);
    }
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }
    public boolean existsById(Long id){
        return userRepository.existsById(id);
    }
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteById(Long id){
        if (! existsById(id)){
            throw new RuntimeException("User not found");
        }
        userRepository.deleteById(id);
    }
    public List<UserDTO> getAll(){
        return userRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }
    public UserDTO convertToDTO(User user){
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setEmail(user.getEmail());
        userDTO.setUsername(user.getUsername());
        return userDTO;
    }

}
