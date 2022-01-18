package com.example.nft21;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class CartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        //récupération des infos des achats
        Bundle extras = getIntent().getExtras();
        double totalETH = extras.getDouble("totalETH");
        double totalEuros = extras.getDouble("totalEuros");

        //mise à jour de la vue
        TextView totalETHText = (TextView) findViewById(R.id.cartTotalPriceETH);
        TextView totalEurosText = (TextView) findViewById(R.id.cartTotalPriceEuros);

        totalETHText.setText(Double.toString(totalETH));
        totalETHText.setText(Double.toString(totalEuros));
    }
}