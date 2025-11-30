package com.example.diplod.blog_api.dto;

import com.example.diplod.blog_api.entity.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserDTO {
    private Long id;
    @NotBlank(message="Имя пользователя обязательно")
    private String username;
    @NotBlank(message="Email обязателен")
    @Email(message="Email должен быть валидным")
    private String email;
    private User.Role role;
    public UserDTO(){

    }
    public UserDTO(User user){
        this.id=user.getId();
        this.username=user.getUsername();
        this.email=user.getEmail();
        this.role=user.getRole();
    }
}
