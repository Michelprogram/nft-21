package com.example.nft21;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.nft21.NFT.NFT;
import com.example.nft21.NFT.Utils;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.concurrent.ExecutionException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HomeActivity extends AppCompatActivity {

    private ArrayAdapter<NFT> nfts;
    private ArrayList<NFT> nftArrayList;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = getApplicationContext();

        Button button = findViewById(R.id.button);
        ListView listView = findViewById(R.id.listview);

        nftArrayList = new ArrayList<>();
        nfts = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,nftArrayList);

        listView.setAdapter(nfts);

        requestOpenSea();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

    }

    private void requestOpenSea(){
        String urlCollection = "https://api.opensea.io/api/v1/assets?order_direction=desc&offset=0&limit=50&collection=alienfrensnft";

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

                    nfts.add(new NFT(name, img, description, price, Utils.mostViewed()));
                }
            }
        });
    }
}