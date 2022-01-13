package com.example.nft21;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.nft21.NFT.NFT;
import com.example.nft21.NFT.NFTAdapter;
import com.example.nft21.NFT.Utils;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;


public class HomeActivity extends AppCompatActivity {


    private NFTAdapter nftAdapter;
    private ArrayList<NFT> nftArrayList;

    private Context context;

    private GridView gridView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = getApplicationContext();

        nftArrayList = new ArrayList<>();

        requestOpenSea();

        gridView = findViewById(R.id.shop_grid);

        nftAdapter = new NFTAdapter(this, nftArrayList);

        gridView.setAdapter(nftAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(HomeActivity.this, DetailActivity.class);

                intent.putExtra("NFT", nftAdapter.getItem(i));

                startActivity(intent);

            }
        });

    }

    private void requestOpenSea(){
        String urlCollection = "https://api.opensea.io/api/v1/assets?order_direction=desc&offset=0&limit=2&collection=alienfrensnft";

        Ion.with(context)
        .load(urlCollection)
        .asJsonObject()
         .setCallback(new FutureCallback<JsonObject>() {
            @Override
            public void onCompleted(Exception e, JsonObject result) {

                JsonArray jsonArray = result.getAsJsonArray("assets");
                Double price = 0.0;

                for (int i = 0; i < jsonArray.size(); i++) {

                    JsonObject curent = jsonArray.get(i).getAsJsonObject();

                    JsonObject asset_contract = curent.getAsJsonObject("asset_contract");

                    String description = asset_contract.get("description").getAsString(),
                            img = curent.get("image_original_url").getAsString(),
                            name = curent.get("name").getAsString();

                    boolean test = curent.get("sell_orders").isJsonNull();

                    if (test) {
                        price = Utils.randomEthPrice();
                    } else {
                        String tempo = curent.getAsJsonArray("sell_orders")
                                .get(0)
                                .getAsJsonObject()
                                .get("current_price")
                                .getAsString();

                        price = Double.parseDouble(tempo.substring(0, 2)) / 10;
                    }

                    nftAdapter.add(new NFT(name, img, description, price, Utils.mostViewed()));
                }
            }
        });
    }
}