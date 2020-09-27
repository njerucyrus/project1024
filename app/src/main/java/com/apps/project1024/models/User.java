package com.apps.project1024.models;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {
    private String name;
    private String phoneNumber;
    private String gender;
    private String userType;
    private String ageBand;
    private String userId;
    private boolean admin;
    public User(){

    }

    public User(String name, String phoneNumber, String gender, String userType, String ageBand, String userId, boolean admin) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
        this.userType = userType;
        this.ageBand = ageBand;
        this.userId = userId;
        this.admin = admin;
    }

    protected User(Parcel in) {
        name = in.readString();
        phoneNumber = in.readString();
        gender = in.readString();
        userType = in.readString();
        ageBand = in.readString();
        userId = in.readString();
        admin = in.readByte() != 0;
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getAgeBand() {
        return ageBand;
    }

    public void setAgeBand(String ageBand) {
        this.ageBand = ageBand;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(phoneNumber);
        parcel.writeString(gender);
        parcel.writeString(userType);
        parcel.writeString(ageBand);
        parcel.writeString(userId);
        parcel.writeByte((byte) (admin ? 1 : 0));
    }
}
