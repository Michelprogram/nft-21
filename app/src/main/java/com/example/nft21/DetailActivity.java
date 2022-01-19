package com.example.nft21;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nft21.NFT.NFT;
import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Bundle extras = getIntent().getExtras();
        NFT nft = extras.getParcelable("NFT");

        ImageView img = findViewById(R.id.imgNFTDetail);
        TextView price = findViewById(R.id.priceDetail);
        TextView description = findViewById(R.id.descriptionDetail);

        Picasso.get().load(nft.getImg()).into(img);

        price.setText(nft.getPrice().toString());
        description.setText(nft.getDescription());

    }

}