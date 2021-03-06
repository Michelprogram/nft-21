package com.example.nft21;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.interfaces.ItemClickListener;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.nft21.NFT.NFT;
import com.example.nft21.NFT.NFTAdapter;
import com.example.nft21.NFT.Utils;
import com.example.nft21.user.User;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ShopActivity extends AppCompatActivity {
    private final int CART_REQUEST_CODE = 42;
    private MediaPlayer mediaPlayer;

    private Map<User, ArrayList<NFT>> panier = new HashMap<>();//panier
    private User currentUser;
    private NFTAdapter nftAdapter;
    private Context context;
    private ListView listView;

    private ImageSlider imageSlider;
    private List<SlideModel> slideModels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //création du panier
        Bundle extras = getIntent().getExtras();
        currentUser = extras.getParcelable("currentUser");

        ArrayList<NFT> nftArrayList = new ArrayList<>();
        panier.put(currentUser,new ArrayList<NFT>());
        //---------------------------------------------------------------------
        context = getApplicationContext();

        requestOpenSeaV2();
        listView = findViewById(R.id.shop_list);

        nftAdapter = new NFTAdapter(this, nftArrayList);
        listView.setAdapter(nftAdapter);

        imageSlider = findViewById(R.id.image_slider);
        slideModels = new ArrayList<>();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(ShopActivity.this, DetailActivity.class);
                intent.putExtra("NFT", nftAdapter.getItem(i));
                startActivityForResult(intent,CART_REQUEST_CODE);
            }
        });

        listView.setOnTouchListener(new View.OnTouchListener() {
            // Setting on Touch Listener for handling the touch inside ScrollView
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // Disallow the touch request for parent scroll on touch of child view
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });

        //transfer au panier
        ImageView cartLogo = (ImageView) findViewById(R.id.shopCartLogo);
        cartLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ShopActivity.this,CartActivity.class);
                intent.putExtra("panier",panier.get(currentUser));
                startActivity(intent);
            }
        });

        //play sound
        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.wii_shop_channel_background_music_hd);
        if(!mediaPlayer.isPlaying())
            mediaPlayer.start();
    }

    @Override
    protected void onActivityResult(int requestCode , int resultCode, Intent data ) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == CART_REQUEST_CODE) {
            //récupération du nft choisi
            NFT chosenNft = data.getParcelableExtra("chosenNft");
            this.ajouterAuPanier(currentUser,chosenNft);
        }
    }

    //------------------------------------gestion du panier--------------------------------
    public void ajouterAuPanier(User client, NFT article) {
        panier.get(client).add(article);
    }

    public void supprimerDuPanier(User client, NFT article) {
        panier.get(client).remove(article);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void viderPanier(User client) {
        panier.replace(client,new ArrayList<NFT>());
    }


    private void requestOpenSeaV2(){
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://api.opensea.io/api/v1/assets?order_direction=desc&offset=0&limit=10&collection=alienfrensnft")
                .addHeader("Accept", "application/json")
                .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) " +
                        "AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.100 Safari/537.36")
                .build();

        client.newCall(request)
                .enqueue(new Callback() {
                    @Override
                    public void onFailure(final Call call, IOException e) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast toast = Toast.makeText(context,
                                        "Problème avec l'api Open Sea", Toast.LENGTH_SHORT);
                                toast.show();
                            }
                        });
                    }

                    @Override
                    public void onResponse(Call call, final Response response) throws IOException {

                        String res = response.body().string();

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                JsonObject result = JsonParser.parseString(res).getAsJsonObject();

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

                                    NFT nft = new NFT(name, img, description, price, Utils.mostViewed());

                                    if (nft.getMostViewed() == 1)
                                        slideModels.add(new SlideModel(nft.getImg()));


                                    nftAdapter.add(nft);

                                }

                                setSlider();
                            }
                        });

                    }
                });

    }

    private void setSlider(){

        imageSlider.setImageList(slideModels, true);

        imageSlider.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemSelected(int i) {

                int j = 0;
                boolean flag = false;

                NFT nft = null;

                String link = slideModels.get(i).getImageUrl();

                while(!flag){
                    nft = nftAdapter.getItem(j);
                    if (nft.getImg().equals(link)){
                        flag = true;
                    }
                    j++;
                }

                Intent intent = new Intent(ShopActivity.this, DetailActivity.class);
                intent.putExtra("NFT", nft);
                startActivityForResult(intent,CART_REQUEST_CODE);

            }
        });

    }


}