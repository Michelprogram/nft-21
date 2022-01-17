package com.example.nft21;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.nft21.nft.NFT;
import com.example.nft21.user.User;
import com.example.nft21.user.iPanier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShopActivity extends AppCompatActivity implements iPanier {
    private Map<User, ArrayList<NFT>> panier = new HashMap<>();//panier

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);
    }

    @Override
    public void ajouterAuPanier(User client, NFT article) {
        panier.get(client).add(article);
    }

    @Override
    public void supprimerDuPanier(User client, NFT article) {
        panier.get(client).remove(article);
    }

    @Override
    public double consulterMontantPanierETH(User client) {
        return 0;
    }

    @Override
    public double consulterMontantPanierEuros(User client) {
        return 0;
    }

    @Override
    public void viderPanier(User client) {

    }

    @Override
    public List<NFT> listerCommandes(User client) {
        return null;
    }
}