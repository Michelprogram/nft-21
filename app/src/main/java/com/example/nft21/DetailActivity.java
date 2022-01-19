package com.example.nft21;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nft21.NFT.NFT;
import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Bundle extras = getIntent().getExtras();
        NFT nft = extras.getParcelable("NFT");

        ImageView img = findViewById(R.id.imgNFTDetail);
        TextView price = findViewById(R.id.priceDetail);
        TextView description = findViewById(R.id.descriptionDetail);
        TextView title = findViewById(R.id.detailName);

        Picasso.get().load(nft.getImg()).into(img);

        price.setText(nft.getPrice().toString());
        description.setText(nft.getDescription());
        title.setText(nft.getName());

        //ajout d'un item dans le panier
        Button addToCartButton = (Button) findViewById(R.id.button_detail);
        addToCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("chosenNft",nft);//le nft choisi est renvoyé à l'activité shop
                setResult(Activity.RESULT_OK,intent);
                finish();
            }
        });
    }

}