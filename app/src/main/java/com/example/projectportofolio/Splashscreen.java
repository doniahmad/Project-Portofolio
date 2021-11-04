package com.example.projectportofolio;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

import com.example.projectportofolio.auth.login;

public class Splashscreen extends AppCompatActivity {

    LinearLayout logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

        logo = findViewById(R.id.content_logo);

        Animation logo_animation = AnimationUtils.loadAnimation(this,R.anim.logo_anim);
        logo.startAnimation(logo_animation);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getApplicationContext(), login.class);
                startActivity(intent);
                finish();
            }
        },3000);
    }
}