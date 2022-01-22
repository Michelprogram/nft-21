package com.example.nft21.NFT;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nft21.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class NFTAdapter extends ArrayAdapter<NFT> {
        private ArrayList<NFT> nfts = new ArrayList<>();
        private Context context;

        public NFTAdapter(Context context, ArrayList<NFT> NFTModelArray) {
            super(context, 0, NFTModelArray);
            this.nfts = NFTModelArray;
            this.context = context;
        }

        @Override
        public int getCount() {
            return nfts.size();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View listItem = convertView;

            if(listItem == null)
                listItem = LayoutInflater.from(context).inflate(R.layout.card_layout,parent,false);

            NFT nft = nfts.get(position);

            ImageView image = listItem.findViewById(R.id.shop_img);

            Picasso.get().load(nft.getImg()).into(image);

            TextView name = listItem.findViewById(R.id.shop_price);
            name.setText(nft.getPrice().toString());

            return listItem;
        }

    }
