package com.example.nft21;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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

        EditText usernameEdit = (EditText) findViewById(R.id.registerUsernameEdit);
        EditText passwordEdit = (EditText) findViewById(R.id.passwordEdit);
        TextView errorText = (TextView) findViewById(R.id.registerErrorText);

        Button signUpButton = (Button) findViewById(R.id.registerSignUpButton);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                errorText.setVisibility(View.INVISIBLE);

                //on vérifie que les champs sont bien remplis :
                String username = usernameEdit.getText().toString();
                String password = passwordEdit.getText().toString();

                if(!username.isEmpty() && !password.isEmpty()){
                    //on simule un nouvel utilisateur pour vérifier s'il n'existe pas déjà
                    User user = new User(username,password);

                    for(User u : users){
                        if(u.equals(user)) {//si l'utilisateur existe dejà
                            errorText.setText(R.string.usernameTakenText);
                            errorText.setVisibility(View.VISIBLE);
                            return;
                        }
                    }

                    users.add(user);//on ajoute l'utilisateur dans la liste
                    //Intent request = new Intent(RegisterActivity.this,ProfileActivity.class);
                    Intent intent = new Intent();
                    intent.putExtra("user",user);
                    setResult(Activity.RESULT_OK,intent);
                    finish();

                }else{
                    errorText.setText(R.string.fieldsEmptyText);
                    errorText.setVisibility(View.VISIBLE);
                }
                }
        });
    }
}