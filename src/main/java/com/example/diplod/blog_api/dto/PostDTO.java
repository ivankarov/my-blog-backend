package com.example.diplod.blog_api.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PostDTO {
    private String title;
    private String content;
//    private String authorUsername;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

//    public String getAuthorUsername() {
//        return authorUsername;
//    }
//
//    public void setAuthorUsername(String authorUsername) {
//        this.authorUsername = authorUsername;
//    }

    public PostDTO(){

    }
}
