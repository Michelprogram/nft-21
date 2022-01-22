package com.example.nft21;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.example.nft21.user.User;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class ProfileActivity extends AppCompatActivity {
    private MediaPlayer mediaPlayer;

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

        //play sound
        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.wii_sports_title);
        if(!mediaPlayer.isPlaying())
            mediaPlayer.start();

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

    @Override
    protected void onPause() {
        super.onPause();
        if (mediaPlayer != null) {
            mediaPlayer.pause();
            if (isFinishing()) {
                mediaPlayer.stop();
                mediaPlayer.release();
            }
        }
    }
}