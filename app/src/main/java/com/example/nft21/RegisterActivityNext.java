package com.example.nft21;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class RegisterActivityNext extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_next);

        //récupération email & password
        Bundle extras = getIntent().getExtras();
        String email = extras.getString("email");
        String password = extras.getString("password");
        System.out.println(email + " "+password);
    }
}