package com.piyush.weatherx.data.remote;

import com.piyush.weatherx.data.model.NewsResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NewsApiService {
    
    @GET("everything")
    Call<NewsResponse> getWeatherNews(
            @Query("q") String query,
            @Query("apiKey") String apiKey,
            @Query("sortBy") String sortBy,
            @Query("pageSize") int pageSize
    );
}