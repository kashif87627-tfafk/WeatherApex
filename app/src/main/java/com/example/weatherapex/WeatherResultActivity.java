package com.example.weatherapex;

import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class WeatherResultActivity extends AppCompatActivity {

    private TextView resultText;
    private ImageView resultBg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_result);

        resultText = findViewById(R.id.resultText);
        resultBg = findViewById(R.id.resultBg);

        // Get weather data from MainActivity
        String weatherData = getIntent().getStringExtra("weather_result");
        String condition = getIntent().getStringExtra("weather_condition");

        if (weatherData != null) {
            resultText.setText(weatherData);
        }

        // Decide which background image to use
        int bgRes = R.drawable.weatherbgg; // default
        if (condition != null) {
            if (condition.contains("rain")) {
                bgRes = R.drawable.rainy_bgg;
            } else if (condition.contains("cloud")) {
                bgRes = R.drawable.cloudy_bgg;
            } else if (condition.contains("clear")) {
                bgRes = R.drawable.clear_bgg;
            } else if (condition.contains("snow")) {
                bgRes = R.drawable.snow_bgg;
            }
        }

        // Apply crossfade animation
        crossfadeBackground(bgRes);
    }

    private void crossfadeBackground(int newImageRes) {
        // Fade out
        AlphaAnimation fadeOut = new AlphaAnimation(1.0f, 0.0f);
        fadeOut.setDuration(500); // 0.5s
        fadeOut.setFillAfter(true);

        // Fade in
        AlphaAnimation fadeIn = new AlphaAnimation(0.0f, 1.0f);
        fadeIn.setDuration(500);
        fadeIn.setFillAfter(true);

        resultBg.startAnimation(fadeOut);

        fadeOut.setAnimationListener(new android.view.animation.Animation.AnimationListener() {
            @Override
            public void onAnimationStart(android.view.animation.Animation animation) { }

            @Override
            public void onAnimationEnd(android.view.animation.Animation animation) {
                resultBg.setImageResource(newImageRes);
                resultBg.startAnimation(fadeIn);
            }

            @Override
            public void onAnimationRepeat(android.view.animation.Animation animation) { }
        });
    }
}
