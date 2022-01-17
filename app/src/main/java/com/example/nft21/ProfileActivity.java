package com.example.nft21;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.WindowManager;
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
        User user = extras.getParcelable("currentUser");

        TextView usernameTextView = (TextView) findViewById(R.id.profileUsername);
        if(user!=null){
            usernameTextView.setText(user.getUsername());
        }
    }
}