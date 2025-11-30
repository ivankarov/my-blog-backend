package com.example.diplod.blog_api.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="users")
@Data
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @Column(unique=true,nullable=false,length=50)
    private String username;
    @Enumerated(EnumType.STRING)
    @Column(nullable=false)
    private Role role = Role.USER;
    @Column(unique=true,nullable=false,length=50)
    private String email;
    @Column(nullable=false)
    private String password;
    @OneToMany(
            mappedBy ="author",
            cascade=CascadeType.ALL,
            fetch=FetchType.LAZY)
    @JsonIgnore
    private List<Post> posts = new ArrayList<>();
    public enum Role{
        USER,
        ADMIN
    }
}
