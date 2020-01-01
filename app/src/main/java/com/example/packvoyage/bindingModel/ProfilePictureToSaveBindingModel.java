package com.example.packvoyage.bindingModel;

public class ProfilePictureToSaveBindingModel {
    private String Content;
    private Integer AccommodationId;
    private Integer ActivityId;
    private String UserId;
    private Integer PackId;

    public ProfilePictureToSaveBindingModel(String content, Integer accommodationId, Integer activityId, String userId, Integer packId) {
        Content = content;
        AccommodationId = accommodationId;
        ActivityId = activityId;
        UserId = userId;
        PackId = packId;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public Integer getAccommodationId() {
        return AccommodationId;
    }

    public void setAccommodationId(Integer accommodationId) {
        AccommodationId = accommodationId;
    }

    public Integer getActivityId() {
        return ActivityId;
    }

    public void setActivityId(Integer activityId) {
        ActivityId = activityId;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public Integer getPackId() {
        return PackId;
    }

    public void setPackId(Integer packId) {
        PackId = packId;
    }
}
