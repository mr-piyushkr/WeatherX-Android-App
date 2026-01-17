package com.piyush.weatherx.data.remote;

import com.piyush.weatherx.data.model.NewsResponse;
import com.piyush.weatherx.data.model.WeatherApiResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {
    
    // WeatherAPI.com current weather
    @GET("current.json")
    Call<WeatherApiResponse> getCurrentWeather(
            @Query("key") String apiKey,
            @Query("q") String location,
            @Query("aqi") String aqi
    );
}