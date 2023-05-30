package com.example.animation;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.materialswitch.MaterialSwitch;

public class MainActivity extends AppCompatActivity {

    private View dayBackground;
    private View nightBackground;

    private ImageView dayscapeImg;
    private ImageView nightscapeImg;
    private ImageView moonImg;
    private ImageView sunImg;
    private ImageView cloudImg;
    private ImageView starsImg;
    private ImageView airplaneImg;
    private ImageView ufoImg;


    private TextView greetingTxt;
    private MaterialSwitch timeSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dayBackground = findViewById(R.id.background_day);
        nightBackground = findViewById(R.id.background_night);

        dayscapeImg = findViewById(R.id.img_dayscape);
        nightscapeImg = findViewById(R.id.img_nightscape);
        moonImg = findViewById(R.id.moon_img);
        sunImg = findViewById(R.id.sun_img);
        cloudImg = findViewById(R.id.cloud_img);
        starsImg = findViewById(R.id.img_stars);
        airplaneImg = findViewById(R.id.img_airplane);
        ufoImg = findViewById(R.id.img_ufo);

        // set start position
        sunImg.setTranslationX(500);
        sunImg.setTranslationY(-400);
        cloudImg.setTranslationY(60);
        airplaneImg.setTranslationX(-240);
        ufoImg.setTranslationX(-240);

        greetingTxt = findViewById(R.id.text_greeting);
        timeSwitch = findViewById(R.id.switch_time);

        // Cloud
        ObjectAnimator translateCloudDown = ObjectAnimator.ofFloat(cloudImg, "translationY", 250);
        translateCloudDown.setDuration(600);

        ObjectAnimator translateCloudUp = ObjectAnimator.ofFloat(cloudImg, "translationY", 60);
        translateCloudUp.setDuration(600);

        ObjectAnimator movingCloud = ObjectAnimator.ofFloat(cloudImg, "translationX", -200);
        movingCloud.setRepeatCount(ObjectAnimator.INFINITE);
        movingCloud.setRepeatMode(ObjectAnimator.REVERSE);
        movingCloud.setDuration(2000);
        movingCloud.start();

        AnimatorSet cloudAnimSet = new AnimatorSet();
        cloudAnimSet.playSequentially(translateCloudUp, movingCloud);

        // stars
        ObjectAnimator blinkStars = ObjectAnimator.ofFloat(starsImg, "alpha", 1);
        blinkStars.setRepeatCount(ObjectAnimator.INFINITE);
        blinkStars.setRepeatMode(ObjectAnimator.REVERSE);
        blinkStars.setDuration(1000);

        // plane
        ObjectAnimator movingPlane = ObjectAnimator.ofFloat(airplaneImg, "translationX", 1800);
        movingPlane.setDuration(3000);
        movingPlane.setRepeatCount(ValueAnimator.INFINITE);
        movingPlane.start();

        // ufo
        ObjectAnimator movingUfo = ObjectAnimator.ofFloat(ufoImg, "translationX", 1600);
        movingUfo.setDuration(2500);
        movingUfo.setRepeatCount(ValueAnimator.INFINITE);
        movingUfo.setRepeatMode(ValueAnimator.REVERSE);

        timeSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                // text
                greetingTxt.setText(R.string.night_greeting);
                greetingTxt.setTextColor(Color.WHITE);

                // bg
                nightBackground.animate().alpha(1).setDuration(600);
                // landscape
                nightscapeImg.animate().alpha(1).setDuration(600);

                // sun and moon
                animateDiagonal(sunImg, 0, 0);
                animateDiagonal(moonImg, -500, -400);

                // stars
                blinkStars.start();

                // cloud
                translateCloudDown.start();
                cloudAnimSet.cancel();
                movingCloud.cancel();

                // plane
                airplaneImg.animate().alpha(0).setDuration(400);

                // ufo
                movingUfo.start();
                ufoImg.animate().alpha(1).setDuration(400);
            } else {
                // text
                greetingTxt.setText(R.string.day_greeting);
                greetingTxt.setTextColor(Color.BLACK);

                // bg
                nightBackground.animate().alpha(0).setDuration(600);
                // landscape
                nightscapeImg.animate().alpha(0).setDuration(600);

                // sun and moon
                animateDiagonal(sunImg, 500, -400);
                animateDiagonal(moonImg, 0, 0);

                // cloud
                cloudAnimSet.start();

                // stars
                starsImg.animate().alpha(0).setDuration(500);
                blinkStars.cancel();

                // plane
                airplaneImg.animate().alpha(1).setDuration(400);

                // ufo
                movingUfo.cancel();
                ufoImg.animate().alpha(0).setDuration(400);
            }
        });
    }

    private void animateDiagonal(View view, int x, int y) {
        final AnimatorSet animSetXY = new AnimatorSet();
        ObjectAnimator translateX = ObjectAnimator.ofFloat(view, "translationX", x);
        ObjectAnimator translateY = ObjectAnimator.ofFloat(view, "translationY", y);

        animSetXY.playTogether(translateX, translateY);
        animSetXY.setDuration(400);
        animSetXY.start();
    }
}