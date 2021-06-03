package com.testapplication.model.userModel;

import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class UserModel implements Serializable, Parcelable {

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("email_verified_at")
    @Expose
    private String emailVerifiedAt;

    @SerializedName("created_at")
    @Expose
    private String createdAt;

    @SerializedName("updated_at")
    @Expose
    private String updatedAt;

    @SerializedName("two_factor_recovery_codes")
    @Expose
    private String twoFactorRecoveryCodes;

    @SerializedName("has2fa")
    @Expose
    private boolean has2fa;

    @SerializedName("status")
    @Expose
    private int status;

    public final static Creator<UserModel> CREATOR = new Creator<UserModel>() {

        @SuppressWarnings({"unchecked"})
        public UserModel createFromParcel(android.os.Parcel in) {
            return new UserModel(in);
        }

        public UserModel[] newArray(int size) {
            return (new UserModel[size]);
        }

    };

    private final static long serialVersionUID = 3250717616412593438L;

    protected UserModel(android.os.Parcel in) {
        this.id = ((int) in.readValue((int.class.getClassLoader())));
        this.name = ((String) in.readValue((String.class.getClassLoader())));
        this.email = ((String) in.readValue((String.class.getClassLoader())));
        this.emailVerifiedAt = ((String) in.readValue((String.class.getClassLoader())));
        this.createdAt = ((String) in.readValue((String.class.getClassLoader())));
        this.updatedAt = ((String) in.readValue((String.class.getClassLoader())));
        this.twoFactorRecoveryCodes = ((String) in.readValue((String.class.getClassLoader())));
        this.has2fa = ((boolean) in.readValue((boolean.class.getClassLoader())));
        this.status = ((int) in.readValue((int.class.getClassLoader())));
    }

    public UserModel() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmailVerifiedAt() {
        return emailVerifiedAt;
    }

    public void setEmailVerifiedAt(String emailVerifiedAt) {
        this.emailVerifiedAt = emailVerifiedAt;
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

    public String getTwoFactorRecoveryCodes() {
        return twoFactorRecoveryCodes;
    }

    public void setTwoFactorRecoveryCodes(String twoFactorRecoveryCodes) {
        this.twoFactorRecoveryCodes = twoFactorRecoveryCodes;
    }

    public boolean isHas2fa() {
        return has2fa;
    }

    public void setHas2fa(boolean has2fa) {
        this.has2fa = has2fa;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(name);
        dest.writeValue(email);
        dest.writeValue(emailVerifiedAt);
        dest.writeValue(createdAt);
        dest.writeValue(updatedAt);
        dest.writeValue(twoFactorRecoveryCodes);
        dest.writeValue(has2fa);
        dest.writeValue(status);
    }

    public int describeContents() {
        return 0;
    }

}
