package com.example.cookingrecipes.model;

import com.google.gson.annotations.SerializedName;

public class UserFromApi {

    @SerializedName("id")
    private String id;

    @SerializedName("email")
    private String email;

    @SerializedName("oauth_uid")
    private String oauthUid;

    @SerializedName("oauth_provider")
    private String oauthProvider;

    @SerializedName("username")
    private String username;

    @SerializedName("full_name")
    private String fullName;

    @SerializedName("avatar")
    private String avatar;

    @SerializedName("banned")
    private String banned;

    @SerializedName("last_login")
    private String lastLogin;

    @SerializedName("last_activity")
    private String lastActivity;

    @SerializedName("date_Created")
    private String dateCreated;

    @SerializedName("forgot_exp")
    private String forgotExp;

    @SerializedName("remember_time")
    private String rememberTime;

    @SerializedName("remember_exp")
    private String rememberExp;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
