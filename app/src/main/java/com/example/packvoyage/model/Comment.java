package com.example.packvoyage.model;

public class Comment {
    private String message;
    private User user;

    public Comment() { }

    public Comment(String message, User user) {
        this.message = message;
        this.user = user;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
