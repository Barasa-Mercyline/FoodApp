package com.example.foodapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.example.foodapp.databinding.ActivitySplashBinding;

public class SplashActivity extends AppCompatActivity {
Animation topanim,bottomanim;
    ActivitySplashBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivitySplashBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        topanim= AnimationUtils.loadAnimation(this,R.anim.topanimation);
        bottomanim= AnimationUtils.loadAnimation(this,R.anim.bottomanimation);
        binding.circleImageView.setAnimation(topanim);
        binding.text1.setAnimation(bottomanim);

        int SPLASH_SCREEN=4000;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(SplashActivity.this, CategoryActivity.class);
                startActivity(intent);
                finish();
            }
        },SPLASH_SCREEN);

    }

   
}