package com.example.weatherapex;
import android.content.Intent;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    EditText cityName;
    Button search;
    TextView textViewResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cityName = findViewById(R.id.cityName);
        search = findViewById(R.id.search);
        textViewResult = findViewById(R.id.textViewResult);

        // TEMP TEST without API
        search.setOnClickListener(v -> {
            String city = cityName.getText().toString().trim();
            if (city.isEmpty()) {
                city = "Default City";
            }

            // Fake weather response
            String fakeWeather = "City: " + city + "\nTemp: 29°C\nCondition: Clear Sky 🌞";
            Intent intent = new Intent(MainActivity.this, ResultActivity.class);
            intent.putExtra("weather_data", fakeWeather);
            startActivity(intent);


            textViewResult.setText(fakeWeather);
        });
    }
}
