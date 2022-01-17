package com.example.nft21;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.example.nft21.user.User;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {
    private final int LOGIN_REQUEST_CODE = 21;
    private final int REGISTER_REQUEST_CODE = 12;
    private ArrayList<User> users = new ArrayList(){{
       add(new User("dcaruso8","deriendorian"));
       add(new User("dorian21","jsuistropmoche"));
    }};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //login
        Button loginButton = (Button) findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent request = new Intent(HomeActivity.this,LoginActivity.class);
                request.putParcelableArrayListExtra("users",users);
                startActivity(request);
            }
        });

        //register
        Button registerButton = (Button) findViewById(R.id.registerButton);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent request = new Intent(HomeActivity.this,RegisterActivity.class);
                request.putParcelableArrayListExtra("users",users);
                startActivityForResult(request,REGISTER_REQUEST_CODE);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode , int resultCode, Intent data ) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == REGISTER_REQUEST_CODE) {
            User createdUser = data.getParcelableExtra("user");
            System.out.println(createdUser);
            users.add(createdUser);

            //redirection vers la page profile
            Intent profileIntent = new Intent(HomeActivity.this,ProfileActivity.class);
            profileIntent.putExtra("currentUser",createdUser);
            startActivity(profileIntent);
        }
    }
}