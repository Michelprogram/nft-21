package com.example.nft21.NFT;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.RequiresApi;

import com.koushikdutta.ion.Ion;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.*;

public class NFT implements Parcelable {


    private Double price;
    private String description;
    private String img;
    private String name;
    private Integer mostViewed;

    public NFT(String name, String img, String description, Double price, Integer mostViewed){
        this.name = name;
        this.img = img;
        this.description = description;
        this.price = price;
        this.mostViewed = mostViewed;

    }


    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getMostViewed() {
        return mostViewed;
    }

    public void setMostViewed(Boolean mostViewed) {
        this.mostViewed = mostViewed;
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    protected NFT(Parcel in) {
        description = in.readString();
        name = in.readString();
        img = in.readString();
        price = in.readDouble();
        mostViewed = in.readBoolean();

    }

    public static final Creator<NFT> CREATOR = new Creator<NFT>() {
        @RequiresApi(api = Build.VERSION_CODES.Q)
        @Override
        public NFT createFromParcel(Parcel in) {
            return new NFT(in);
        }

        @Override
        public NFT[] newArray(int size) {
            return new NFT[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(description);
        parcel.writeString(name);
        parcel.writeString(img);
        parcel.writeDouble(price);
        parcel.writeBoolean(mostViewed);

    }


    @Override
    public String toString() {
        return "Une NFT du nom de "+name+" de "+ price + "ETH" + " description : " +description+
                "url img : " + img + (mostViewed ? "le plus vue" :"");
    }


}
