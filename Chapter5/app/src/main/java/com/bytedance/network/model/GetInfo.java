package com.bytedance.network.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetInfo {
    @SerializedName("feeds")
    private List<Video> feeds;

    @SerializedName("success")
    private boolean success;

    public List<Video> getFeeds() {
        return feeds;
    }
}
