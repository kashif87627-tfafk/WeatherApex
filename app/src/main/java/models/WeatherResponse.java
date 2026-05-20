package com.example.weatherapex.models;

import com.google.gson.annotations.SerializedName;

public class WeatherResponse {

    @SerializedName("name")
    private String cityName;

    @SerializedName("main")
    private Main main;

    @SerializedName("weather")
    private Weather[] weather;

    public String getCityName() {
        return cityName;
    }

    public Main getMain() {
        return main;
    }

    public Weather[] getWeather() {
        return weather;
    }

    // inner class for "main"
    public class Main {
        @SerializedName("temp")
        private double temp;

        public double getTemp() {
            return temp;
        }
    }

    // inner class for "weather"
    public class Weather {
        @SerializedName("description")
        private String description;

        public String getDescription() {
            return description;
        }
    }
}
