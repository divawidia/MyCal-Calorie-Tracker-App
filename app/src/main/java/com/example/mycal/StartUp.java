package com.example.mycal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class StartUp extends AppCompatActivity {
    Button signup_button, login_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_up);

        signup_button = (Button)findViewById(R.id.sign_up);


        /* Listener submit */
        signup_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(StartUp.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
    }
}