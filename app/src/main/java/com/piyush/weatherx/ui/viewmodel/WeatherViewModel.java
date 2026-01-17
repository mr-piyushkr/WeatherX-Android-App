package com.piyush.weatherx.ui.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.piyush.weatherx.data.model.Forecast;
import com.piyush.weatherx.data.model.NewsResponse;
import com.piyush.weatherx.data.model.Weather;
import com.piyush.weatherx.data.repository.WeatherRepository;
import com.piyush.weatherx.utils.NetworkResult;

public class WeatherViewModel extends ViewModel {
    private WeatherRepository repository;
    private MutableLiveData<NetworkResult<Weather>> weatherLiveData;
    private MutableLiveData<NetworkResult<Forecast>> forecastLiveData;
    private MutableLiveData<NetworkResult<NewsResponse>> newsLiveData;

    public WeatherViewModel() {
        repository = WeatherRepository.getInstance();
        weatherLiveData = new MutableLiveData<>();
        forecastLiveData = new MutableLiveData<>();
        newsLiveData = new MutableLiveData<>();
    }

    public LiveData<NetworkResult<Weather>> getWeatherLiveData() {
        return weatherLiveData;
    }

    public LiveData<NetworkResult<Forecast>> getForecastLiveData() {
        return forecastLiveData;
    }

    public LiveData<NetworkResult<NewsResponse>> getNewsLiveData() {
        return newsLiveData;
    }

    public void fetchCurrentWeather(double lat, double lon) {
        try {
            MutableLiveData<NetworkResult<Weather>> result = repository.getCurrentWeather(lat, lon);
            result.observeForever(weatherResult -> {
                weatherLiveData.setValue(weatherResult);
                if (weatherResult.getStatus() == NetworkResult.Status.SUCCESS) {
                    fetchForecast(lat, lon);
                }
            });
        } catch (Exception e) {
            weatherLiveData.setValue(NetworkResult.error("Error: " + e.getMessage()));
        }
    }

    public void fetchWeatherByCity(String cityName) {
        try {
            MutableLiveData<NetworkResult<Weather>> result = repository.getCurrentWeatherByCity(cityName);
            result.observeForever(weatherResult -> {
                weatherLiveData.setValue(weatherResult);
                if (weatherResult.getStatus() == NetworkResult.Status.SUCCESS) {
                    fetchForecast(0, 0); // Use default coordinates for forecast
                }
            });
        } catch (Exception e) {
            weatherLiveData.setValue(NetworkResult.error("Error: " + e.getMessage()));
        }
    }

    private void fetchForecast(double lat, double lon) {
        try {
            MutableLiveData<NetworkResult<Forecast>> result = repository.getForecast(lat, lon);
            result.observeForever(forecastResult -> {
                forecastLiveData.setValue(forecastResult);
            });
        } catch (Exception e) {
            forecastLiveData.setValue(NetworkResult.error("Forecast error: " + e.getMessage()));
        }
    }

    public void fetchWeatherNews() {
        try {
            MutableLiveData<NetworkResult<NewsResponse>> result = repository.getWeatherNews();
            result.observeForever(newsResult -> {
                newsLiveData.setValue(newsResult);
            });
        } catch (Exception e) {
            newsLiveData.setValue(NetworkResult.error("News error: " + e.getMessage()));
        }
    }
}