package com.example.nft21;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.nft21.user.User;

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

        EditText usernameEdit = (EditText) findViewById(R.id.usernameEdit);
        Button signUpButton = (Button) findViewById(R.id.signUpButton);

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //on vérifie que le champs est bien rempli :
                String username = usernameEdit.getText().toString();

                if(!username.isEmpty()){
                    //Fabrication du user
                    User user = new User(username,email,password);
                    System.out.println(user);
                    
                    //Intent request = new Intent(RegisterActivityNext.this,RegisterActivityNext.class);
                    //request.putExtra("email",email);
                    //request.putExtra("password",password);
                    //startActivityForResult(request,REGISTER_NEXT_REQUEST_CODE);
                }
            }
        });
    }
}