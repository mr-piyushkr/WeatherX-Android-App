package com.piyush.weatherx.utils;

import com.piyush.weatherx.data.model.Weather;
import com.piyush.weatherx.data.model.Forecast;
import java.util.ArrayList;
import java.util.List;

public class MockWeatherData {
    
    public static Weather createMockWeather(String cityName) {
        Weather weather = new Weather();
        
        // Create mock weather data
        Weather.Main main = new Weather.Main();
        main.setTemp(22.5);
        main.setFeelsLike(25.0);
        main.setHumidity(65);
        main.setPressure(1013);
        
        Weather.Wind wind = new Weather.Wind();
        wind.setSpeed(5.2);
        
        Weather.WeatherInfo weatherInfo = new Weather.WeatherInfo();
        weatherInfo.setMain("Clear");
        weatherInfo.setDescription("clear sky");
        weatherInfo.setIcon("01d");
        
        List<Weather.WeatherInfo> weatherList = new ArrayList<>();
        weatherList.add(weatherInfo);
        
        Weather.Sys sys = new Weather.Sys();
        sys.setCountry("GB");
        
        // Set all data
        weather.setName(cityName);
        weather.setMain(main);
        weather.setWind(wind);
        weather.setWeather(weatherList);
        weather.setSys(sys);
        
        return weather;
    }
    
    public static Forecast createMockForecast() {
        Forecast forecast = new Forecast();
        List<Forecast.ForecastItem> items = new ArrayList<>();
        
        for (int i = 0; i < 5; i++) {
            Forecast.ForecastItem item = new Forecast.ForecastItem();
            
            Weather.Main main = new Weather.Main();
            main.setTemp(20 + i * 2);
            
            Weather.WeatherInfo weatherInfo = new Weather.WeatherInfo();
            weatherInfo.setMain("Clear");
            weatherInfo.setDescription("sunny");
            weatherInfo.setIcon("01d");
            
            List<Weather.WeatherInfo> weatherList = new ArrayList<>();
            weatherList.add(weatherInfo);
            
            item.setDt(System.currentTimeMillis() / 1000 + (i * 86400));
            item.setMain(main);
            item.setWeather(weatherList);
            item.setDtTxt("2024-01-" + (15 + i) + " 12:00:00");
            
            items.add(item);
        }
        
        forecast.setList(items);
        return forecast;
    }
}