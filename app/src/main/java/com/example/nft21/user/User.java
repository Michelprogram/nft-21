package com.example.nft21.user;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Objects;

public class User implements Parcelable {
    private String username;
    private String email;
    private String password;
    private String PP;

    public User(String username,String email,String password,String PP){
        this.username = username;
        this.email = email;
        this.password = password;
        this.PP = PP;
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
        dest.writeString(PP);
    }

    protected User(Parcel in) {
        username = in.readString();
        email = in.readString();
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(username, user.username) && Objects.equals(email, user.email) && Objects.equals(password, user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, email, password, PP);
    }
}
//https://api.unsplash.com/search/photos/?client_id=i-2wKyejotToqVenHUZx5GkWdaqCE3UmIzYSTF81dV0&query=marble