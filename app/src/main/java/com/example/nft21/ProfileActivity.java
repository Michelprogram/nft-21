package com.example.nft21;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.nft21.user.User;

import java.util.ArrayList;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //récupération de l'utilisateur
        Bundle extras = getIntent().getExtras();
        User user = extras.getParcelable("user");

        System.out.println(user);
    }
}