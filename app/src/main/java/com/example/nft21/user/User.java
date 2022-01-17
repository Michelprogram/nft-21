package com.example.nft21.user;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Objects;

public class User implements Parcelable {
    private String username;
    private String password;
    private String PP;

    public User(String username,String password){
        this.username = username;
        this.password = password;
        this.PP = "profile_picture";
    }

    public String getPassword(){
        return this.password;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int i) {
        dest.writeString(username);
        dest.writeString(password);
        dest.writeString(PP);
    }

    protected User(Parcel in) {
        username = in.readString();
        password = in.readString();
        PP = in.readString();
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

    //deux utilisateurs sont égaux s'ils possèdent le même username
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(username, user.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, password, PP);
    }

    public String toString(){
        return "User "+username+" has password : "+ password + "\n";
    }
}
//https://api.unsplash.com/search/photos/?client_id=i-2wKyejotToqVenHUZx5GkWdaqCE3UmIzYSTF81dV0&query=marble