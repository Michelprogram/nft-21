package com.example.nft21;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;

import com.example.nft21.NFT.NFT;
import com.example.nft21.NFT.NFTAdapter;
import com.example.nft21.NFT.Utils;
import com.example.nft21.user.User;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.squareup.picasso.Picasso;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShopActivity extends AppCompatActivity {
    private final int CART_REQUEST_CODE = 42;

    private Map<User, ArrayList<NFT>> panier = new HashMap<>();//panier
    private User currentUser;
    private NFTAdapter nftAdapter;
    private ArrayList<NFT> nftArrayList;
    private ArrayList<NFT> nftArrayListMostViewved;
    private Context context;
    private GridView gridView;
    private CarouselView carouselView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);


        //création du panier
        currentUser = new User("dcaruso8","deriendorian");
        panier.put(currentUser,new ArrayList<NFT>());
         /*
        ArrayList<NFT> nfts = new ArrayList<>();
        nfts.add(new NFT("nft trop bien","img","un nft que nathan adore",0.5,0));
        nfts.add(new NFT("nft bof","img1","un nft que dorian adore",1.6,1));
        nfts.add(new NFT("nft trop nul","img","un nft que emma adore",0.1,0));
        */

        //---------------------------------------------------------------------

        context = getApplicationContext();
        nftArrayList = new ArrayList<>();

        requestOpenSea();
        gridView = findViewById(R.id.shop_grid);

        nftAdapter = new NFTAdapter(this, nftArrayList);
        nftArrayListMostViewved = new ArrayList<>();
        gridView.setAdapter(nftAdapter);
        carouselView = findViewById(R.id.carouselView);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(ShopActivity.this, DetailActivity.class);
                intent.putExtra("NFT", nftAdapter.getItem(i));
                startActivityForResult(intent,CART_REQUEST_CODE);
            }
        });

        //transfer au panier
        //Intent intent = new Intent(ShopActivity.this,CartActivity.class);
        //intent.putExtra("panier",nftArrayList);
        //startActivity(intent);
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

    private void setCarouselView(){
        ImageListener imageListener = new ImageListener() {
            @Override
            public void setImageForPosition(int position, ImageView imageView) {
                Picasso.get().load(nftArrayListMostViewved.get(position).getImg()).into(imageView);
            }
        };

        carouselView.setPageCount(nftArrayListMostViewved.size());
        carouselView.setImageListener(imageListener);
    }

    private void requestOpenSea(){
        String urlCollection = "https://api.opensea.io/api/v1/assets?order_direction=desc&offset=0&limit=10&collection=alienfrensnft";

        Ion.getDefault(context).getConscryptMiddleware().enable(false);
        Ion.with(context)
                .load(urlCollection)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        System.out.println(result.toString());
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


                            if (nft.getMostViewed() == 0)
                                nftArrayListMostViewved.add(nft);

                            nftAdapter.add(nft);

                        }
                        setCarouselView();
                    }
                });
    }
}