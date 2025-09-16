package com.example.weatherapex;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        TextView resultText = findViewById(R.id.resultText);

        // Get weather data sent from MainActivity
        String weatherInfo = getIntent().getStringExtra("weather_data");

        resultText.setText(weatherInfo);
    }
}
