package com.testapplication.model.loginModel;

import com.google.gson.annotations.SerializedName;
import com.testapplication.model.userModel.UserModel;

public class LoginModel {

    @SerializedName("user")
    private UserModel userModel;

    @SerializedName("accessToken")
    private String accessToken;

    public UserModel getUserModel() {
        return userModel;
    }

    public void setUserModel(UserModel userModel) {
        this.userModel = userModel;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
