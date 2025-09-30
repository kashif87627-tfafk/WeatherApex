package com.example.weatherapex;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

public class WeatherResultActivity extends AppCompatActivity {

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_result);

        ImageView resultBg = findViewById(R.id.resultBg);

        TextView cityCountry = findViewById(R.id.cityCountry);
        TextView tempInfo = findViewById(R.id.tempInfo);
        TextView weatherCondition = findViewById(R.id.weatherCondition);
        TextView humidityPressure = findViewById(R.id.humidityPressure);
        TextView windClouds = findViewById(R.id.windClouds);
        TextView visibilitySunriseSunset = findViewById(R.id.visibilitySunriseSunset);
        TextView coordinates = findViewById(R.id.coordinates);

        // Get data from intent
        Bundle b = getIntent().getExtras();
        if (b != null) {
            String city = b.getString("city");
            String country = b.getString("country");
            double temp = b.getDouble("temp");
            double feelsLike = b.getDouble("feelsLike");
            double tempMin = b.getDouble("tempMin");
            double tempMax = b.getDouble("tempMax");
            int humidity = b.getInt("humidity");
            int pressure = b.getInt("pressure");
            double windSpeed = b.getDouble("windSpeed");
            int windDeg = b.getInt("windDeg");
            int cloudiness = b.getInt("cloudiness");
            int visibility = b.getInt("visibility");
            long sunrise = b.getLong("sunrise");
            long sunset = b.getLong("sunset");
            double lat = b.getDouble("lat");
            double lon = b.getDouble("lon");
            String conditionMain = b.getString("conditionMain");
            String conditionDesc = b.getString("conditionDesc");

            // Set background according to weather
            // Set background according to weather
            switch (Objects.requireNonNull(conditionMain).toLowerCase()) {
                case "clear":
                    resultBg.setImageResource(R.drawable.clear_bgg);
                    break;
                case "clouds":
                    resultBg.setImageResource(R.drawable.cloudy_bgg);
                    break;
                case "rain":
                case "drizzle":
                    resultBg.setImageResource(R.drawable.rainy_bgg);
                    break;
                case "thunderstorm":
                    resultBg.setImageResource(R.drawable.thunder);
                    break;
                case "snow":
                    resultBg.setImageResource(R.drawable.snow_bgg);
                    break;
                case "mist":
                case "smoke":
                case "haze":
                case "fog":
                case "dust":
                case "sand":
                case "ash":
                case "squall":
                case "tornado":
                    resultBg.setImageResource(R.drawable.thunder); // or create tornado.png if you have
                    break;
                default:
                    resultBg.setImageResource(R.drawable.weatherbgg);
            }


            cityCountry.setText(city + ", " + country);
            tempInfo.setText("🌡️ Temperature: " + temp + "°C (Feels like " + feelsLike + "°C)\nMin: " + tempMin + "°C | Max: " + tempMax + "°C");
            weatherCondition.setText("⛅ Condition: " + conditionMain + " (" + conditionDesc + ")");
            humidityPressure.setText("💧 Humidity: " + humidity + "% | Pressure: " + pressure + " hPa");
            windClouds.setText("🌬️ Wind: " + windSpeed + " m/s, " + windDeg + "° | Clouds: " + cloudiness + "%");
            visibilitySunriseSunset.setText("👁️ Visibility: " + visibility + " m\n🌅 Sunrise: " + unixToTime(sunrise) + " | 🌇 Sunset: " + unixToTime(sunset));
            coordinates.setText("📍 Coordinates: " + lat + ", " + lon);
        }
    }

    private String unixToTime(long unixSeconds) {
        java.util.Date date = new java.util.Date(unixSeconds * 1000L);
        @SuppressLint("SimpleDateFormat") java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("HH:mm");
        sdf.setTimeZone(java.util.TimeZone.getDefault());
        return sdf.format(date);
    }
}
