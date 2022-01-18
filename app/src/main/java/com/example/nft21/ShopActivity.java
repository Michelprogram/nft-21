package com.example.nft21;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import com.example.nft21.NFT.NFT;
import com.example.nft21.user.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShopActivity extends AppCompatActivity {
    private Map<User, ArrayList<NFT>> panier = new HashMap<>();//panier

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        //création du panier
        User daryl = new User("dcaruso8","deriendorian");
        ArrayList<NFT> nfts = new ArrayList<>();
        nfts.add(new NFT("nft trop bien","img","un nft que nathan adore",0.5,0));
        nfts.add(new NFT("nft bof","img1","un nft que dorian adore",1.6,1));
        nfts.add(new NFT("nft trop nul","img","un nft que emma adore",0.1,0));
        panier.put(daryl,nfts);

        //tranfer au panier
        Intent intent = new Intent(ShopActivity.this,CartActivity.class);
        intent.putExtra("panier",nfts);
        startActivity(intent);
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

    public List<NFT> listerCommandes(User client) {
        return panier.get(client);
    }
}