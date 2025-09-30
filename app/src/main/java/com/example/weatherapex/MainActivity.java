package com.example.weatherapex;

import android.content.Intent;
import android.os.Bundle;
import android.widget.AutoCompleteTextView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;

public class MainActivity extends AppCompatActivity {

    private AutoCompleteTextView cityName;
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        cityName = findViewById(R.id.cityName);
        Button searchButton = findViewById(R.id.search);

        requestQueue = Volley.newRequestQueue(this);
        String[] cities = {"Hyderabad, IN", "Mumbai, IN", "Delhi, IN", "London, UK", "New York, USA"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, cities);
        cityName.setAdapter(adapter);
        cityName.setThreshold(1);

        searchButton.setOnClickListener(v -> {
            String city = cityName.getText().toString().trim();
            if (!city.isEmpty()) fetchWeather(city);
            else Toast.makeText(this, "⚠️ Please enter a city name!", Toast.LENGTH_SHORT).show();
        });
    }

    private void fetchWeather(String city) {
        String apiKey = "3b44ccd69cb5838f3f5849f7322ff4b5";
        String url = "https://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=" + apiKey + "&units=metric";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        JSONObject main = response.getJSONObject("main");
                        double temp = main.getDouble("temp");
                        double feelsLike = main.getDouble("feels_like");
                        double tempMin = main.getDouble("temp_min");
                        double tempMax = main.getDouble("temp_max");
                        int pressure = main.getInt("pressure");
                        int humidity = main.getInt("humidity");

                        JSONObject wind = response.getJSONObject("wind");
                        double windSpeed = wind.getDouble("speed");
                        int windDeg = wind.getInt("deg");

                        JSONObject clouds = response.getJSONObject("clouds");
                        int cloudiness = clouds.getInt("all");

                        int visibility = response.has("visibility") ? response.getInt("visibility") : -1;

                        JSONObject sys = response.getJSONObject("sys");
                        String country = sys.getString("country");
                        long sunrise = sys.getLong("sunrise");
                        long sunset = sys.getLong("sunset");

                        JSONObject coord = response.getJSONObject("coord");
                        double lat = coord.getDouble("lat");
                        double lon = coord.getDouble("lon");

                        JSONArray weatherArray = response.getJSONArray("weather");
                        JSONObject weather = weatherArray.getJSONObject(0);
                        String conditionMain = weather.getString("main");
                        String conditionDesc = weather.getString("description");

                        // Start result activity
                        Intent intent = new Intent(MainActivity.this, WeatherResultActivity.class);
                        intent.putExtra("city", city);
                        intent.putExtra("country", country);
                        intent.putExtra("temp", temp);
                        intent.putExtra("feelsLike", feelsLike);
                        intent.putExtra("tempMin", tempMin);
                        intent.putExtra("tempMax", tempMax);
                        intent.putExtra("humidity", humidity);
                        intent.putExtra("pressure", pressure);
                        intent.putExtra("windSpeed", windSpeed);
                        intent.putExtra("windDeg", windDeg);
                        intent.putExtra("cloudiness", cloudiness);
                        intent.putExtra("visibility", visibility);
                        intent.putExtra("sunrise", sunrise);
                        intent.putExtra("sunset", sunset);
                        intent.putExtra("lat", lat);
                        intent.putExtra("lon", lon);
                        intent.putExtra("conditionMain", conditionMain);
                        intent.putExtra("conditionDesc", conditionDesc);

                        startActivity(intent);

                    } catch (JSONException e) {
                        //noinspection CallToPrintStackTrace
                        e.printStackTrace();
                        Toast.makeText(this, "Error parsing weather data", Toast.LENGTH_SHORT).show();
                    }

                }, error -> Toast.makeText(this, "Failed to get data! Check city name.", Toast.LENGTH_SHORT).show()
        );

        requestQueue.add(request);
    }
}
