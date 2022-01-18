package com.example.nft21;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.nft21.NFT.NFT;
import com.example.nft21.user.User;

import java.util.ArrayList;

public class CartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        //récupération des infos des achats
        Bundle extras = getIntent().getExtras();
        ArrayList<NFT> nfts = extras.getParcelableArrayList("panier");

        //calcul du montant total
        double totalETH = this.consulterMontantPanierETH(nfts);
        double totalEuros = this.consulterMontantPanierEuros(nfts);

        //mise à jour de la vue
        TextView totalETHText = (TextView) findViewById(R.id.cartTotalPriceETH);
        TextView totalEurosText = (TextView) findViewById(R.id.cartTotalPriceEuros);

        totalETHText.setText(Double.toString(totalETH));
        totalEurosText.setText(Double.toString(totalEuros));
    }

    public double consulterMontantPanierETH(ArrayList<NFT> nfts) {
        double montant = 0.0;
        for(NFT nft : nfts){
            montant+=nft.getPrice();
        }
        return montant;
    }

    public double consulterMontantPanierEuros(ArrayList<NFT> nfts) {
        double montant = 0.0;
        for(NFT nft : nfts){
            montant+=nft.getPrice();
        }

        //convertir en euros !!!
        return montant;
    }
}