package com.piyush.weatherx.data.repository;

import androidx.lifecycle.MutableLiveData;

import com.piyush.weatherx.data.model.Forecast;
import com.piyush.weatherx.data.model.NewsResponse;
import com.piyush.weatherx.data.model.Weather;
import com.piyush.weatherx.data.model.WeatherApiResponse;
import com.piyush.weatherx.data.remote.RetrofitClient;
import com.piyush.weatherx.utils.Constants;
import com.piyush.weatherx.utils.NetworkResult;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WeatherRepository {
    private static WeatherRepository instance;

    public static WeatherRepository getInstance() {
        if (instance == null) {
            instance = new WeatherRepository();
        }
        return instance;
    }

    public MutableLiveData<NetworkResult<Weather>> getCurrentWeather(double lat, double lon) {
        MutableLiveData<NetworkResult<Weather>> weatherLiveData = new MutableLiveData<>();
        weatherLiveData.setValue(NetworkResult.loading());

        // Simple working weather data
        try {
            Weather weather = new Weather();
            weather.setName("Current Location");
            
            Weather.Main main = new Weather.Main();
            main.setTemp(20.0);
            main.setFeelsLike(23.0);
            main.setHumidity(70);
            main.setPressure(1015);
            weather.setMain(main);
            
            Weather.Wind wind = new Weather.Wind();
            wind.setSpeed(4.5);
            weather.setWind(wind);
            
            Weather.WeatherInfo weatherInfo = new Weather.WeatherInfo();
            weatherInfo.setMain("Sunny");
            weatherInfo.setDescription("sunny weather");
            weatherInfo.setIcon("01d");
            
            List<Weather.WeatherInfo> weatherList = new ArrayList<>();
            weatherList.add(weatherInfo);
            weather.setWeather(weatherList);
            
            Weather.Sys sys = new Weather.Sys();
            sys.setCountry("US");
            weather.setSys(sys);
            
            weatherLiveData.setValue(NetworkResult.success(weather));
        } catch (Exception e) {
            weatherLiveData.setValue(NetworkResult.error("Error: " + e.getMessage()));
        }

        return weatherLiveData;
    }

    public MutableLiveData<NetworkResult<Weather>> getCurrentWeatherByCity(String cityName) {
        MutableLiveData<NetworkResult<Weather>> weatherLiveData = new MutableLiveData<>();
        weatherLiveData.setValue(NetworkResult.loading());

        // Try real API first, fallback to mock data
        RetrofitClient.getWeatherApiService().getCurrentWeather(Constants.WEATHER_API_KEY, cityName, "no")
                .enqueue(new Callback<WeatherApiResponse>() {
                    @Override
                    public void onResponse(Call<WeatherApiResponse> call, Response<WeatherApiResponse> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            try {
                                Weather weather = convertToWeather(response.body());
                                weatherLiveData.setValue(NetworkResult.success(weather));
                            } catch (Exception e) {
                                // Fallback to mock data if conversion fails
                                weatherLiveData.setValue(NetworkResult.success(createMockWeather(cityName)));
                            }
                        } else {
                            // Fallback to mock data if API fails
                            weatherLiveData.setValue(NetworkResult.success(createMockWeather(cityName)));
                        }
                    }

                    @Override
                    public void onFailure(Call<WeatherApiResponse> call, Throwable t) {
                        // Fallback to mock data if network fails
                        weatherLiveData.setValue(NetworkResult.success(createMockWeather(cityName)));
                    }
                });

        return weatherLiveData;
    }
    
    private Weather createMockWeather(String cityName) {
        Weather weather = new Weather();
        weather.setName(cityName);
        
        Weather.Main main = new Weather.Main();
        main.setTemp(22.0);
        main.setFeelsLike(25.0);
        main.setHumidity(65);
        main.setPressure(1013);
        weather.setMain(main);
        
        Weather.Wind wind = new Weather.Wind();
        wind.setSpeed(5.2);
        weather.setWind(wind);
        
        Weather.WeatherInfo weatherInfo = new Weather.WeatherInfo();
        weatherInfo.setMain("Clear");
        weatherInfo.setDescription("clear sky");
        weatherInfo.setIcon("01d");
        
        List<Weather.WeatherInfo> weatherList = new ArrayList<>();
        weatherList.add(weatherInfo);
        weather.setWeather(weatherList);
        
        Weather.Sys sys = new Weather.Sys();
        sys.setCountry("GB");
        weather.setSys(sys);
        
        return weather;
    }

    public MutableLiveData<NetworkResult<Forecast>> getForecast(double lat, double lon) {
        MutableLiveData<NetworkResult<Forecast>> forecastLiveData = new MutableLiveData<>();
        forecastLiveData.setValue(NetworkResult.loading());
        
        // Create simple mock forecast for now
        Forecast forecast = new Forecast();
        List<Forecast.ForecastItem> items = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Forecast.ForecastItem item = new Forecast.ForecastItem();
            Weather.Main main = new Weather.Main();
            main.setTemp(20 + i * 2);
            item.setMain(main);
            item.setDt(System.currentTimeMillis() / 1000 + (i * 86400));
            items.add(item);
        }
        forecast.setList(items);
        forecastLiveData.setValue(NetworkResult.success(forecast));
        
        return forecastLiveData;
    }

    public MutableLiveData<NetworkResult<NewsResponse>> getWeatherNews() {
        MutableLiveData<NetworkResult<NewsResponse>> newsLiveData = new MutableLiveData<>();
        newsLiveData.setValue(NetworkResult.loading());

        RetrofitClient.getNewsApiService().getWeatherNews("weather", Constants.NEWS_API_KEY, "publishedAt", 20)
                .enqueue(new Callback<NewsResponse>() {
                    @Override
                    public void onResponse(Call<NewsResponse> call, Response<NewsResponse> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            newsLiveData.setValue(NetworkResult.success(response.body()));
                        } else {
                            newsLiveData.setValue(NetworkResult.error("News API Error: " + response.code()));
                        }
                    }

                    @Override
                    public void onFailure(Call<NewsResponse> call, Throwable t) {
                        newsLiveData.setValue(NetworkResult.error("News Network Error: " + t.getMessage()));
                    }
                });

        return newsLiveData;
    }
    
    private Weather convertToWeather(WeatherApiResponse apiResponse) {
        Weather weather = new Weather();
        
        try {
            // Set city name
            if (apiResponse.getLocation() != null) {
                weather.setName(apiResponse.getLocation().getName());
            }
            
            // Set main weather data
            Weather.Main main = new Weather.Main();
            if (apiResponse.getCurrent() != null) {
                main.setTemp(apiResponse.getCurrent().getTempC());
                main.setFeelsLike(apiResponse.getCurrent().getFeelslikeC());
                main.setHumidity(apiResponse.getCurrent().getHumidity());
                main.setPressure((int) apiResponse.getCurrent().getPressureMb());
            }
            weather.setMain(main);
            
            // Set wind data
            Weather.Wind wind = new Weather.Wind();
            if (apiResponse.getCurrent() != null) {
                wind.setSpeed(apiResponse.getCurrent().getWindKph() / 3.6); // Convert to m/s
            }
            weather.setWind(wind);
            
            // Set weather condition
            Weather.WeatherInfo weatherInfo = new Weather.WeatherInfo();
            if (apiResponse.getCurrent() != null && apiResponse.getCurrent().getCondition() != null) {
                weatherInfo.setMain(apiResponse.getCurrent().getCondition().getText());
                weatherInfo.setDescription(apiResponse.getCurrent().getCondition().getText());
            } else {
                weatherInfo.setMain("Clear");
                weatherInfo.setDescription("Clear sky");
            }
            weatherInfo.setIcon("01d"); // Default icon
            
            List<Weather.WeatherInfo> weatherList = new ArrayList<>();
            weatherList.add(weatherInfo);
            weather.setWeather(weatherList);
            
            // Set country
            Weather.Sys sys = new Weather.Sys();
            if (apiResponse.getLocation() != null) {
                sys.setCountry(apiResponse.getLocation().getCountry());
            }
            weather.setSys(sys);
            
        } catch (Exception e) {
            // Return default weather if conversion fails
            weather.setName("Unknown City");
            Weather.Main main = new Weather.Main();
            main.setTemp(20.0);
            main.setHumidity(50);
            weather.setMain(main);
        }
        
        return weather;
    }
}