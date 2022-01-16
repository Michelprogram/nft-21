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

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //récupération de la liste des utilisateurs
        Bundle extras = getIntent().getExtras();
        ArrayList<User> users = extras.getParcelableArrayList("users");

        EditText usernameEdit = (EditText) findViewById(R.id.loginUsernameEdit);
        EditText passwordEdit = (EditText) findViewById(R.id.passwordEdit);

        Button loginButton = (Button) findViewById(R.id.loginViewButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //on vérifie que les champs sont bien remplis :
                String username = usernameEdit.getText().toString();
                String password = passwordEdit.getText().toString();

                if(!username.isEmpty() && !password.isEmpty()){
                    //on simule un nouvel utilisateur pour vérifier s'il n'existe pas déjà
                    User user = new User(username,password);

                    for(User u : users){
                        if(u.equals(user)) {//si l'utilisateur existe bien
                            if(u.getPassword().equals(password)){//on vérifie si le mdp est correct
                                Intent request = new Intent(LoginActivity.this,ProfileActivity.class);
                                request.putExtra("user",u);
                                startActivity(request);
                                return;
                            }else{
                                System.out.println("Mot de passe incorrect !");
                                return;
                            }
                        }
                    }
                    System.out.println("Le nom d'utilisateur n'existe pas !!");
            }
            }
        });
    }
}