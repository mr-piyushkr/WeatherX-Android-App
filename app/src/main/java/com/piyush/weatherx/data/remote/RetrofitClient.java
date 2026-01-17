package com.piyush.weatherx.data.remote;

import com.piyush.weatherx.utils.Constants;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static Retrofit weatherRetrofit = null;
    private static Retrofit newsRetrofit = null;

    public static ApiService getWeatherApiService() {
        if (weatherRetrofit == null) {
            weatherRetrofit = new Retrofit.Builder()
                    .baseUrl(Constants.WEATHER_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return weatherRetrofit.create(ApiService.class);
    }

    public static NewsApiService getNewsApiService() {
        if (newsRetrofit == null) {
            newsRetrofit = new Retrofit.Builder()
                    .baseUrl(Constants.NEWS_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return newsRetrofit.create(NewsApiService.class);
    }
}