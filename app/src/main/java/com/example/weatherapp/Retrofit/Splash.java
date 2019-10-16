package com.example.weatherapp.Retrofit;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.weatherapp.MainActivity;
import com.example.weatherapp.R;

public class Splash extends AppCompatActivity {
    ImageView Welcome_Logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
//        ActionBar actionBar = getSupportActionBar();
//        getSupportActionBar().setTitle("Welcome to Food NutrI");
//        actionBar.setSubtitle("You can search best food here");

        Welcome_Logo= findViewById(R.id.Welcome_Logo);


        Animation ani = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate);
        ani.setDuration(3000);

        Welcome_Logo.setAnimation(ani);

        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate);
        animation.setDuration(3000);
        Welcome_Logo.setAnimation(animation);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(Splash.this, MainActivity.class);
                startActivity(i);
                finish();

            }
        },10000);

    }
}
