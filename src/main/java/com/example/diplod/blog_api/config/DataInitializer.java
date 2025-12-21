package com.example.diplod.blog_api.config;

import com.example.diplod.blog_api.entity.User;
import com.example.diplod.blog_api.service.UserService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

@Slf4j
@Configuration
@RequiredArgsConstructor
@Profile("!test")
public class DataInitializer{
    private PasswordEncoder passwordEncoder;
    private final UserService userService;
    @PostConstruct
    public void init(){
        try{
            initUser();
        }catch(Exception e){
            log.error("Data initializer failed",e);
        }
    }
    private void initUser(){
        if (userService.existsByUsername("admin")){
            log.info("Admin already exists");
            return;
        }
        User admin= new User();
        admin.setUsername("admin");
        admin.setEmail("admin@blog.com");
        admin.setPassword(passwordEncoder.encode("admin123"));
        admin.setRole(User.Role.ADMIN);
        userService.createUser(admin);
        log.info("created admin user: admin/admin123");
        User user=new User();
        user.setUsername("user");
        user.setEmail("user@blog.com");
        user.setPassword("user123");
        user.setRole(User.Role.USER);
        log.info("created user: user/user123");
    }

}
