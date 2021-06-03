package com.testapplication.model.vehicleModel;

import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class VehicleModel implements Serializable, Parcelable {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("make_id")
    @Expose
    private int makeId;
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("deleted_at")
    @Expose
    private String deletedAt;
    public final static Creator<VehicleModel> CREATOR = new Creator<VehicleModel>() {

        @SuppressWarnings({"unchecked"})
        public VehicleModel createFromParcel(android.os.Parcel in) {
            return new VehicleModel(in);
        }

        public VehicleModel[] newArray(int size) {
            return (new VehicleModel[size]);
        }

    };

    private final static long serialVersionUID = 5797473436114461156L;

    protected VehicleModel(android.os.Parcel in) {
        this.id = ((int) in.readValue((int.class.getClassLoader())));
        this.makeId = ((int) in.readValue((int.class.getClassLoader())));
        this.code = ((String) in.readValue((String.class.getClassLoader())));
        this.title = ((String) in.readValue((String.class.getClassLoader())));
        this.createdAt = ((String) in.readValue((String.class.getClassLoader())));
        this.updatedAt = ((String) in.readValue((String.class.getClassLoader())));
        this.deletedAt = ((String) in.readValue((String.class.getClassLoader())));
    }

    public VehicleModel() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMakeId() {
        return makeId;
    }

    public void setMakeId(int makeId) {
        this.makeId = makeId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(String deletedAt) {
        this.deletedAt = deletedAt;
    }

    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(makeId);
        dest.writeValue(code);
        dest.writeValue(title);
        dest.writeValue(createdAt);
        dest.writeValue(updatedAt);
        dest.writeValue(deletedAt);
    }

    public int describeContents() {
        return 0;
    }

}
