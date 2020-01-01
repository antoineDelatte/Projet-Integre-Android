package com.example.packvoyage.bindingModel;

public class CloudinaryUnsignedUploadBody {
    private byte[] file;
    private String upload_preset;

    public CloudinaryUnsignedUploadBody(byte[] file, String upload_preset) {
        this.file = file;
        this.upload_preset = upload_preset;
    }

    public byte[] getFile() {
        return file;
    }

    public void setFile(byte[] file) {
        this.file = file;
    }

    public String getUpload_preset() {
        return upload_preset;
    }

    public void setUpload_preset(String upload_preset) {
        this.upload_preset = upload_preset;
    }
}
