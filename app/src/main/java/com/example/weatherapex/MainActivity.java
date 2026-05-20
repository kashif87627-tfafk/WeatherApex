package com.example.weatherapex;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private AutoCompleteTextView cityName;
    private RequestQueue requestQueue;
    private FusedLocationProviderClient fusedLocationClient;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        cityName = findViewById(R.id.cityName);
        Button searchButton = findViewById(R.id.search);
        Button fetchLocationButton = findViewById(R.id.fetchLocation);

        requestQueue = Volley.newRequestQueue(this);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);


        ArrayAdapter<String> adapter = getStringArrayAdapter();
        cityName.setAdapter(adapter);
        cityName.setThreshold(1);


        searchButton.setOnClickListener(v -> {
            String city = cityName.getText().toString().trim();
            if (!city.isEmpty()) fetchWeather(city);
            else Toast.makeText(this, "⚠️ Please enter a city name!", Toast.LENGTH_SHORT).show();
        });


        fetchLocationButton.setOnClickListener(v -> getCurrentLocation());
    }

    @NonNull
    private ArrayAdapter<String> getStringArrayAdapter() {
        String[] cities = {"Hyderabad, IN", "Mumbai, IN", "Delhi, IN", "Bengaluru, IN", "Chennai, IN",
                "Kolkata, IN", "Pune, IN", "Ahmedabad, IN", "Jaipur, IN", "Lucknow, IN",
                "Chandigarh, IN", "Bhopal, IN", "Indore, IN", "Nagpur, IN", "Surat, IN",
                "Patna, IN", "Varanasi, IN", "Amritsar, IN", "Goa, IN", "Thiruvananthapuram, IN",
                "Kochi, IN", "London, UK", "Paris, FR", "Berlin, DE", "Rome, IT", "Madrid, ES",
                "New York, USA", "Los Angeles, USA", "Chicago, USA", "Toronto, CA",
                "Vancouver, CA", "Sydney, AU", "Melbourne, AU", "Tokyo, JP", "Osaka, JP",
                "Seoul, KR", "Beijing, CN", "Shanghai, CN", "Singapore, SG", "Dubai, AE"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, cities);
        return adapter;
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
                        e.printStackTrace();
                        Toast.makeText(this, "Error parsing weather data", Toast.LENGTH_SHORT).show();
                    }

                }, error -> Toast.makeText(this, "Failed to get data! Check city name.", Toast.LENGTH_SHORT).show()
        );

        requestQueue.add(request);
    }


    private void fetchWeatherByCoordinates(double lat, double lon) {
        String apiKey = "3b44ccd69cb5838f3f5849f7322ff4b5";
        String url = "https://api.openweathermap.org/data/2.5/weather?lat=" + lat +
                "&lon=" + lon + "&appid=" + apiKey + "&units=metric";

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

                        JSONArray weatherArray = response.getJSONArray("weather");
                        JSONObject weather = weatherArray.getJSONObject(0);
                        String conditionMain = weather.getString("main");
                        String conditionDesc = weather.getString("description");

                        String city = response.getString("name");

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
                        e.printStackTrace();
                        Toast.makeText(this, "Error parsing weather data", Toast.LENGTH_SHORT).show();
                    }

                }, error -> Toast.makeText(this, "Failed to get weather from location!", Toast.LENGTH_SHORT).show()
        );

        requestQueue.add(request);
    }


    private void getCurrentLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
        } else {
            fusedLocationClient.getLastLocation()
                    .addOnSuccessListener(this, location -> {
                        if (location != null) {
                            double lat = location.getLatitude();
                            double lon = location.getLongitude();
                            fetchWeatherByCoordinates(lat, lon);
                        } else {
                            Toast.makeText(this, "Unable to get current location", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getCurrentLocation();
            } else {
                Toast.makeText(this, "Location permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
