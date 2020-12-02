package com.bytedance.network.model;

import com.google.gson.annotations.SerializedName;

public class Video {
    @SerializedName("_id")
    private String _id;

    @SerializedName("student_id")
    private String studentId;

    @SerializedName("user_name")
    private String userName;

    @SerializedName("video_url")
    private String videoUrl;

    @SerializedName("image_url")
    private String imageUrl;

    @SerializedName("image_w")
    private int imageW;

    @SerializedName("image_h")
    private int imageH;

    @SerializedName("createdAt")
    private String createdAt;

    @SerializedName("updatedAt")
    private String updatedAt;

    public String getStudentId() {
        return studentId;
    }
    public String getUserName() {
        return userName;
    }
    public String getVideoUrl() {
        return videoUrl;
    }
    public String getImageUrl() {
        return imageUrl;
    }
}
