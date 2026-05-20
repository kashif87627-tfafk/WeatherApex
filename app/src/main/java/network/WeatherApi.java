package com.example.weatherapex.network;


import com.example.weatherapex.models.WeatherResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherApi {
    @GET("weather")
    Call<WeatherResponse> getCurrentWeather(
            @Query("q") String city,
            @Query("appid") String apiKey,
            @Query("units") String units
    );
}
