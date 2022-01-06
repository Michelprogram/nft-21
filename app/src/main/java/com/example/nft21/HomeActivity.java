package com.example.nft21;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.nft21.NFT.NFT;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;

public class HomeActivity extends AppCompatActivity {

    private ArrayAdapter<NFT> nfts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NFT.requestOpenSea();

        Button button = findViewById(R.id.button);
        ListView listView = findViewById(R.id.listview);

        nfts = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,NFT.nfts);

        listView.setAdapter(nfts);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (NFT nft: NFT.nfts) {
                    nfts.add(nft);
                }
            }
        });

        listView.setOnClickListener();

    }
}