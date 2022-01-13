package com.example.nft21;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

public class RegisterActivity extends AppCompatActivity {
    private final int REGISTER_NEXT_REQUEST_CODE = 11;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Button loginButton = (Button) findViewById(R.id.signUpButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent request = new Intent(RegisterActivity.this,RegisterActivityNext.class);
                startActivityForResult(request,REGISTER_NEXT_REQUEST_CODE);
            }
        });
    }
}