package com.example.perds.andrew_perdec_a3;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Perds on 3/23/2016.
 */
public class SocialMedia implements Parcelable {

    // Declaring variables
    private String name;
    private String userName;
    private String password;

    // To string only returns name for the listview
    @Override
    public String toString() {
        return name;
    }

    // Constructor for only name
    public SocialMedia(String name) {
        this.name = name;
    }

    public SocialMedia(String name, String userName, String password) {
        this.name = name;
        this.userName = userName;
        this.password = password;
    }

    // Update allows the variable to be switched with another object of social media
    public void update(SocialMedia socialMedia) {
        name = socialMedia.name;
        userName = socialMedia.userName;
        password = socialMedia.password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // Parcel methods below for sending through intent

    protected SocialMedia(Parcel in) {
        name = in.readString();
        userName = in.readString();
        password = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(userName);
        dest.writeString(password);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<SocialMedia> CREATOR = new Creator<SocialMedia>() {
        @Override
        public SocialMedia createFromParcel(Parcel in) {
            return new SocialMedia(in);
        }

        @Override
        public SocialMedia[] newArray(int size) {
            return new SocialMedia[size];
        }
    };
}
