package com.example.packvoyage.bindingModel;

public class TraductionBindingModel {
    private int packId;
    private int languageId;
    private String description;

    public TraductionBindingModel(int packId, int languageId, String description) {
        this.packId = packId;
        this.languageId = languageId;
        this.description = description;
    }

    public int getPackId() {
        return packId;
    }

    public void setPackId(int packId) {
        this.packId = packId;
    }

    public int getLanguageId() {
        return languageId;
    }

    public void setLanguageId(int languageId) {
        this.languageId = languageId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
