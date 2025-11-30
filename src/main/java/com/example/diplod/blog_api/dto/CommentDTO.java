package com.example.diplod.blog_api.dto;

import lombok.Data;

@Data
public class CommentDTO {
    private  String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public CommentDTO(){

    }
}
