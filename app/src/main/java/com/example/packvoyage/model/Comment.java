package com.example.packvoyage.model;

public class Comment {
    private Integer id;
    private String message;
    private User user;

    public Comment() { }

    public Comment(Integer id, String message, User user) {
        this.id = id;
        this.message = message;
        this.user = user;
    }

    public Comment(String message, User user) {
        this(null, message, user);
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
