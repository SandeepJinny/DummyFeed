package com.example.fgfdemo.model;

import java.util.ArrayList;
import java.util.List;

public class Post {
    private String userName;
    private String content;
    private String userProfileImageUrl;
    private String postImageUrl;
    private boolean isLiked;
    private List<String> comments;
    public Post(String userName, String content, String userProfileImageUrl, String postImageUrl) {
        this.userName = userName;
        this.content = content;
        this.userProfileImageUrl = userProfileImageUrl;
        this.postImageUrl = postImageUrl;
        this.isLiked = false;
        this.comments = new ArrayList<>();
    }

    // Getters
    public String getUserName() {
        return userName;
    }

    public String getContent() {
        return content;
    }
    public boolean isLiked() {
        return isLiked;
    }

    public void toggleLike() {
        isLiked = !isLiked;
    }

    public List<String> getComments() {
        return comments;
    }

    public void addComment(String comment) {
        comments.add(comment);
    }
    public String getUserProfileImageUrl() {
        return userProfileImageUrl;
    }

    public String getPostImageUrl() {
        return postImageUrl;
    }
}