package com.example.nft21.user;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {
    private String username;
    private String email;
    private String password;

    public User(String username,String email,String password){
        this.username = username;
        this.email = email;
        this.password = password;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int i) {
        dest.writeString(username);
        dest.writeString(email);
        dest.writeString(password);
    }

    protected User(Parcel in) {
        username = in.readString();
        email = in.readString();
        password = in.readString();
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
}
//https://api.unsplash.com/search/photos/?client_id=i-2wKyejotToqVenHUZx5GkWdaqCE3UmIzYSTF81dV0&query=marble