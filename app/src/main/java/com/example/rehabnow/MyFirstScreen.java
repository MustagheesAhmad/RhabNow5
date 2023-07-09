package com.example.rehabnow;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


public class MyFirstScreen extends AppCompatActivity {
ImageView HeaderImage;
TextView heading, slogan;
Button create1btn, login1btn;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_first_screen);
    HeaderImage=findViewById(R.id.HeaderImage);
    heading=findViewById(R.id.heading);
    slogan=findViewById(R.id.slogan);
    create1btn=findViewById(R.id.create1btn);
    login1btn=findViewById(R.id.login1btn);
    create1btn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent signup=new Intent(MyFirstScreen.this, Signup.class);
            startActivity(signup);
        }

    });
        login1btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent loginPage=new Intent(MyFirstScreen.this,MainActivity.class);
                startActivity(loginPage);
            }
        });

    }
}