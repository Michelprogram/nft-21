package com.example.nft21.NFT;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class NFT implements Parcelable {

    public static ArrayList<NFT> nfts;

    private Double price;
    private String description;
    private String img;
    private String name;
    private Boolean mostViewed;


    public NFT(String name, String img, String description, Double price, Boolean mostViewed){
        this.name = name;
        this.img = img;
        this.description = description;
        this.price = price;
        this.mostViewed = mostViewed;

    }

    public static ArrayList<NFT> getNfts(){
        return NFT.nfts;
    }

    public static void requestOpenSea(){

        String urlCollection = "https://api.opensea.io/api/v1/assets?order_direction=desc&offset=0&limit=50&collection=alienfrensnft";
        NFT.nfts = new ArrayList<>();


        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(urlCollection)
                .build();


        client.newCall(request).enqueue(new Callback() {
            public void onResponse(Call call, Response response)
                    throws IOException {
                try {
                    JSONObject jsonObject = new JSONObject(response.body().string());

                    JSONArray jsonArray = jsonObject.getJSONArray("assets");

                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject curent = jsonArray.getJSONObject(i);

                        JSONObject asset_contract = curent.getJSONObject("asset_contract");

                        String description = asset_contract.getString("description"),
                                img = curent.getString("image_original_url"),
                                name = curent.getString("name");

                        Double price = 0.0;

                        if (curent.isNull("sell_orders")){
                            price = Utils.randomEthPrice();
                        }else{
                            String tempo = curent.getJSONArray("sell_orders").getJSONObject(0)
                                    .getString("current_price");

                            price = Double.parseDouble(tempo.substring(0,2)) / 10;
                        }

                        NFT.nfts.add(new NFT(name,img,description,price,Utils.mostViewed()));
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }
        });

    }


    protected NFT(Parcel in) {
    }

    public static final Creator<NFT> CREATOR = new Creator<NFT>() {
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

    @Override
    public void writeToParcel(Parcel parcel, int i) {
    }




    @Override
    public String toString() {
        return "Une NFT du nom de "+name+" de "+ price + "ETH" + " description : " +description+
                "url img : " + img + (mostViewed ? "le plus vue" :"");
    }


}
