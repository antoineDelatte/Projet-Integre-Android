package com.example.packvoyage.bindingModel;

public class EvaluationBindingModel {
    private Integer id;
    private String comment;
    private String userId;
    private UserForEvaluationBindingModel user;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public UserForEvaluationBindingModel getUser() {
        return user;
    }

    public void setUser(UserForEvaluationBindingModel user) {
        this.user = user;
    }
}
