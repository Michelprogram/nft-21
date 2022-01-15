package com.example.nft21;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.example.nft21.user.User;

import java.util.ArrayList;

public class RegisterActivity extends AppCompatActivity {
    private final int REGISTER_NEXT_REQUEST_CODE = 11;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //récupération de la liste des utilisateurs
        Bundle extras = getIntent().getExtras();
        ArrayList<User> users = extras.getParcelableArrayList("users");

        EditText emailEdit = (EditText) findViewById(R.id.emailEdit);
        EditText passwordEdit = (EditText) findViewById(R.id.passwordEdit);

        Button registerNextButton = (Button) findViewById(R.id.registerNextButton);
        registerNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //on vérifie que les champs sont bien remplis :
                String email = emailEdit.getText().toString();
                String password = passwordEdit.getText().toString();

                if(!email.isEmpty() && !password.isEmpty()){
                    Intent request = new Intent(RegisterActivity.this,RegisterActivityNext.class);
                    request.putExtra("email",email);
                    request.putExtra("password",password);
                    startActivity(request);
                }
            }
        });
    }
}