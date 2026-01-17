package com.piyush.weatherx.utils;

import com.piyush.weatherx.BuildConfig;

public class Constants {
    // WeatherAPI.com - Free 1M calls/month
    public static final String WEATHER_BASE_URL = "https://api.weatherapi.com/v1/";
    public static final String WEATHER_API_KEY = BuildConfig.WEATHER_API_KEY;
    public static final String WEATHER_ICON_URL = "https://cdn.weatherapi.com/weather/64x64/day/";
    
    // NewsAPI
    public static final String NEWS_BASE_URL = "https://newsapi.org/v2/";
    public static final String NEWS_API_KEY = BuildConfig.NEWS_API_KEY;
    
    public static final int LOCATION_PERMISSION_REQUEST_CODE = 1001;
}