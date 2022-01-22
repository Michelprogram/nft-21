package com.example.nft21;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nft21.NFT.NFT;
import com.example.nft21.NFT.NFTCartAdapter;
import com.example.nft21.user.User;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class CartActivity extends AppCompatActivity {

    private ArrayList<NFT> nfts;
    private TextView totalEth;
    private TextView totalEur;
    private TextView emptyText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //récupération des infos des achats
        Bundle extras = getIntent().getExtras();
        nfts = extras.getParcelableArrayList("panier");

        emptyText = findViewById(R.id.cartEmptyTextView);

        if(nfts.isEmpty())
            emptyText.setVisibility(View.VISIBLE);
        else
            emptyText.setVisibility(View.INVISIBLE);

        //affichage de la liste des nfts
        ListView cartListView = (ListView) findViewById(R.id.cartListView);
        NFTCartAdapter adapter = new NFTCartAdapter(CartActivity.this,nfts);
        cartListView.setAdapter(adapter);


        //mise à jour de la vue
        totalEth = findViewById(R.id.cartTotalPriceETH);
        totalEur = findViewById(R.id.cartTotalPriceEuros);

        convertEthToEur();
    }

    private double consulterMontantPanierETH(ArrayList<NFT> nfts) {
        double montant = 0.0;
        for(NFT nft : nfts){
            montant+=nft.getPrice();
        }
        return montant;
    }

    private void convertEthToEur(){
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://min-api.cryptocompare.com/data/price?fsym=ETH&tsyms=ETH,EUR")
                .addHeader("Accept", "application/json")
                .build();

        client.newCall(request)
                .enqueue(new Callback() {
                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException e) {
                        Toast.makeText(getApplicationContext(),
                                "Problème avec la conversion entre ETH et EUR", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                        String res = response.body().string();

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                JsonObject jsonObject = JsonParser.parseString(res).getAsJsonObject();

                                Double valueEur = jsonObject.get("EUR").getAsDouble();
                                Double valueEth = consulterMontantPanierETH(nfts);

                                Double total = valueEth * valueEur;

                                totalEth.setText(String.format("%.2f", valueEth));
                                totalEur.setText(String.format("%.2f", total));

                            }
                        });

                    }
                });

    }
}