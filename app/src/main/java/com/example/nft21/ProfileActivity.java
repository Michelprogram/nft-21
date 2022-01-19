package com.example.nft21;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.example.nft21.user.User;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //récupération de l'utilisateur
        Bundle extras = getIntent().getExtras();
        User currentUser = extras.getParcelable("currentUser");

        TextView usernameTextView = (TextView) findViewById(R.id.profileUsername);
        if(currentUser!=null){
            usernameTextView.setText(currentUser.getUsername());
        }

        //boutique
        Button shopButton = (Button) findViewById(R.id.profileShopButton);
        shopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileActivity.this,ShopActivity.class);
                intent.putExtra("currentUser",currentUser);
                startActivity(intent);
            }
        });
    }
}