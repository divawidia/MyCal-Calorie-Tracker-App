package com.example.mycal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class SignUpActivity extends AppCompatActivity {

    EditText email, username, password;
    Button signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up_activity);

        signup = (Button) findViewById(R.id.buttonSignUp);

        email = (EditText) findViewById(R.id.editTextEmail);
        username = (EditText) findViewById(R.id.editTextUsername);
        password = (EditText) findViewById(R.id.editTextPassword);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signUpSubmit();
            }
        });
    }

    public void signUpSubmit(){

        String stringEmail = email.getText().toString();
        if(stringEmail.isEmpty() || stringEmail.startsWith(" ")){
            Toast.makeText(SignUpActivity.this,"Please fill an e-mail address.", Toast.LENGTH_LONG).show();
        }

        String stringUsername = username.getText().toString();
        if(stringUsername.isEmpty() || stringUsername.startsWith(" ")){
            Toast.makeText(SignUpActivity.this,"Please fill a username", Toast.LENGTH_LONG).show();
        }

        String stringPassword = password.getText().toString();
        if(stringPassword.isEmpty() || stringPassword.startsWith(" ")){
            Toast.makeText(SignUpActivity.this,"Please fill a password", Toast.LENGTH_LONG).show();
        }

        //memasukan ke database
        DBAdapter db = new DBAdapter(this);
        db.open();

        String stringEmailSQL = db.quoteSmart(stringEmail);
        String stringUsernameSQL = db.quoteSmart(stringUsername);
        String stringPasswordSQL = db.quoteSmart(stringPassword);

        String stringInput = "NULL, " + stringEmailSQL + "," + stringPasswordSQL + "," + stringUsernameSQL;
        db.insert("users",
                "_id, user_email, user_password, user_alias",
                stringInput);

        //menutup database
        db.close();

        Intent i = new Intent(SignUpActivity.this, ProfileDetails.class);
        startActivity(i);
    }
}