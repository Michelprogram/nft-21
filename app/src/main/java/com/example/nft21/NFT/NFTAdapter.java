package com.example.nft21.NFT;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nft21.DetailActivity;
import com.example.nft21.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class NFTAdapter extends RecyclerView.Adapter<NFTAdapter.Viewholder> {

    private Context context;
    private ArrayList<NFT> NFTArrayList;

    public NFTAdapter(Context context, ArrayList<NFT> NFTModelArray) {
        this.context = context;
        this.NFTArrayList = NFTModelArray;
    }

    public void add(NFT nft){
        System.out.println(nft.getImg());
        NFTArrayList.add(nft);
    }

    public void clean(){
        NFTArrayList.clear();
    }

    @Override
    public NFTAdapter.Viewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout, parent, false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(NFTAdapter.Viewholder holder, int position) {
        NFT model = NFTArrayList.get(position);
        Picasso.get().load(model.getImg()).into(holder.imgNFT);
        holder.descriptionNFT.setText(model.getDescription());
        holder.priceNFT.setText("" + model.getPrice());
    }

    @Override
    public int getItemCount() {
        return NFTArrayList.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private ImageView imgNFT;
        private TextView priceNFT, descriptionNFT;

        public Viewholder(View itemView) {
            super(itemView);
            itemView.setClickable(true);
            itemView.setOnClickListener(this);
            imgNFT = itemView.findViewById(R.id.imgNFT);
            priceNFT = itemView.findViewById(R.id.priceNft);
            descriptionNFT = itemView.findViewById(R.id.descriptionNft);
        }

        @Override
        public void onClick(View view) {
            System.out.println("OUIIIIIIIIIIIIII"+ getAdapterPosition());

            Intent intent = new Intent(context, DetailActivity.class);

            intent.putExtra("NFT", "oui");

            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            context.startActivity(intent);

        }

    }
}
