package com.techco.igotrip.data.network.model.object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Nhat on 12/30/17.
 */

public class Comment {
    public static final int NORMAL = 0;
    public static final int EDITED = 1;
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("content")
    @Expose
    private String content;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("edited")
    @Expose
    private int edited;
    @SerializedName("user_id")
    @Expose
    private int userId;
    @SerializedName("user_name")
    @Expose
    private String username;
    @SerializedName("full_name")
    @Expose
    private String fullname;
    @SerializedName("image")
    @Expose
    private String userImage;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public int getEdited() {
        return edited;
    }

    public void setEdited(int edited) {
        this.edited = edited;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }
}
