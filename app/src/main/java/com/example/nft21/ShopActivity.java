package com.example.nft21;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;

import com.example.nft21.NFT.NFT;
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

    //------------------------------------gestion du panier--------------------------------
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
        double montant = 0.0;
        for(NFT nft : panier.get(client)){
            montant+=nft.getPrice();
        }
        return montant;
    }

    @Override
    public double consulterMontantPanierEuros(User client) {
        double montant = 0.0;
        for(NFT nft : panier.get(client)){
            montant+=nft.getPrice();
        }

        //convertir en euros !!!
        return montant;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void viderPanier(User client) {
        panier.replace(client,new ArrayList<NFT>());
    }

    @Override
    public List<NFT> listerCommandes(User client) {
        return panier.get(client);
    }
}