package com.example.weatherapex;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private EditText cityName;
    private Button searchButton;
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Initialize UI components
        cityName = findViewById(R.id.cityName);
        searchButton = findViewById(R.id.search);

        // Initialize Volley request queue
        requestQueue = Volley.newRequestQueue(this);

        // Button click
        searchButton.setOnClickListener(v -> {
            String city = cityName.getText().toString().trim();

            if (!city.isEmpty()) {
                fetchWeather(city);
            } else {
                Toast.makeText(MainActivity.this, "⚠️ Please enter a city name!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fetchWeather(String city) {
        String apiKey = "3b44ccd69cb5838f3f5849f7322ff4b5";

        String url = "https://api.openweathermap.org/data/2.5/weather?q="
                + city + "&appid=" + apiKey + "&units=metric";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET, url, null,
                response -> {
                    try {
                        JSONObject main = response.getJSONObject("main");
                        double temperature = main.getDouble("temp");

                        JSONObject weather = response.getJSONArray("weather").getJSONObject(0);
                        String condition = weather.getString("description");

                        /// Prepare result string
                        String resultText = "🌍 City: " + city + "\n" +
                                "🌡️ Temperature: " + temperature + "°C\n" +
                                "⛅ Condition: " + condition;


                        Intent intent = new Intent(MainActivity.this, WeatherResultActivity.class);
                        intent.putExtra("weather_result", resultText);
                        intent.putExtra("weather_condition", condition.toLowerCase()); // send condition
                        startActivity(intent);


                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(MainActivity.this, "Error parsing data", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> Toast.makeText(MainActivity.this, "Failed to get data! Check city name.", Toast.LENGTH_SHORT).show()
        );

        // Add request to queue
        requestQueue.add(jsonObjectRequest);
    }
}
