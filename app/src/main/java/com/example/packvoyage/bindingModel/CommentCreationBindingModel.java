package com.example.packvoyage.bindingModel;

public class CommentCreationBindingModel {
    private Integer Note;
    private String Comment;
    private Integer PackId;
    private String UserId;

    public CommentCreationBindingModel(Integer note, String comment, Integer packId, String userId) {
        Note = note;
        Comment = comment;
        PackId = packId;
        UserId = userId;
    }

    public Integer getNote() {
        return Note;
    }

    public void setNote(Integer note) {
        Note = note;
    }

    public String getComment() {
        return Comment;
    }

    public void setComment(String comment) {
        Comment = comment;
    }

    public Integer getPackId() {
        return PackId;
    }

    public void setPackId(Integer packId) {
        PackId = packId;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }
}
