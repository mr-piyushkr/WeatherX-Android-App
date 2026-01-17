package com.piyush.weatherx.ui.view;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.piyush.weatherx.R;
import com.piyush.weatherx.data.model.Weather;
import com.piyush.weatherx.databinding.ActivityMainBinding;
import com.piyush.weatherx.ui.adapter.ForecastAdapter;
import com.piyush.weatherx.ui.adapter.NewsAdapter;
import com.piyush.weatherx.ui.viewmodel.WeatherViewModel;
import com.piyush.weatherx.utils.Constants;
import com.piyush.weatherx.utils.LocationHelper;
import com.piyush.weatherx.utils.NetworkResult;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private WeatherViewModel viewModel;
    private LocationHelper locationHelper;
    private ForecastAdapter forecastAdapter;
    private NewsAdapter newsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initViews();
        setupObservers();
        checkLocationPermission();
    }

    private void initViews() {
        viewModel = new ViewModelProvider(this).get(WeatherViewModel.class);
        locationHelper = new LocationHelper(this);

        // Setup RecyclerViews
        forecastAdapter = new ForecastAdapter();
        binding.forecastRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        binding.forecastRecyclerView.setAdapter(forecastAdapter);

        newsAdapter = new NewsAdapter(this);
        binding.newsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.newsRecyclerView.setAdapter(newsAdapter);

        // Search functionality
        binding.searchButton.setOnClickListener(v -> {
            String cityName = binding.searchEditText.getText().toString().trim();
            if (!cityName.isEmpty()) {
                Toast.makeText(this, "Searching for: " + cityName, Toast.LENGTH_SHORT).show();
                viewModel.fetchWeatherByCity(cityName);
                binding.searchEditText.setText("");
            } else {
                Toast.makeText(this, "Testing with London", Toast.LENGTH_SHORT).show();
                viewModel.fetchWeatherByCity("London");
            }
        });

        // Refresh button
        binding.refreshButton.setOnClickListener(v -> {
            getCurrentLocation();
        });

        // Load weather news
        viewModel.fetchWeatherNews();
    }

    private void setupObservers() {
        // Weather observer
        viewModel.getWeatherLiveData().observe(this, result -> {
            switch (result.getStatus()) {
                case LOADING:
                    binding.progressBar.setVisibility(View.VISIBLE);
                    binding.weatherCard.setVisibility(View.GONE);
                    break;
                case SUCCESS:
                    binding.progressBar.setVisibility(View.GONE);
                    binding.weatherCard.setVisibility(View.VISIBLE);
                    updateWeatherUI(result.getData());
                    break;
                case ERROR:
                    binding.progressBar.setVisibility(View.GONE);
                    String errorMsg = result.getMessage() != null ? result.getMessage() : "Failed to fetch weather data";
                    Toast.makeText(this, "Error: " + errorMsg, Toast.LENGTH_LONG).show();
                    // Try default city as fallback
                    if (!errorMsg.contains("London")) {
                        viewModel.fetchWeatherByCity("London");
                    }
                    break;
            }
        });

        // Forecast observer
        viewModel.getForecastLiveData().observe(this, result -> {
            if (result.getStatus() == NetworkResult.Status.SUCCESS && result.getData() != null) {
                forecastAdapter.setForecastList(result.getData().getList());
            }
        });

        // News observer
        viewModel.getNewsLiveData().observe(this, result -> {
            switch (result.getStatus()) {
                case LOADING:
                    binding.newsProgressBar.setVisibility(View.VISIBLE);
                    break;
                case SUCCESS:
                    binding.newsProgressBar.setVisibility(View.GONE);
                    if (result.getData() != null && result.getData().getArticles() != null) {
                        newsAdapter.setNewsList(result.getData().getArticles());
                    }
                    break;
                case ERROR:
                    binding.newsProgressBar.setVisibility(View.GONE);
                    break;
            }
        });
    }

    private void updateWeatherUI(Weather weather) {
        if (weather == null) return;

        try {
            if (weather.getName() != null) {
                binding.cityNameTextView.setText(weather.getName());
            }
            
            if (weather.getMain() != null) {
                binding.temperatureTextView.setText((int) Math.round(weather.getMain().getTemp()) + "°C");
                binding.humidityTextView.setText(weather.getMain().getHumidity() + "%");
                binding.pressureTextView.setText(weather.getMain().getPressure() + " hPa");
                binding.feelsLikeTextView.setText("Feels like " + (int) Math.round(weather.getMain().getFeelsLike()) + "°C");
            }
            
            if (weather.getWind() != null) {
                binding.windSpeedTextView.setText(String.format("%.1f m/s", weather.getWind().getSpeed()));
            }
            
            if (weather.getWeather() != null && !weather.getWeather().isEmpty()) {
                binding.weatherDescriptionTextView.setText(weather.getWeather().get(0).getDescription());
                
                // Load weather icon
                String iconUrl = Constants.WEATHER_ICON_URL + weather.getWeather().get(0).getIcon() + ".png";
                Glide.with(this)
                        .load(iconUrl)
                        .placeholder(R.drawable.ic_placeholder)
                        .into(binding.weatherIconImageView);
            }
        } catch (Exception e) {
            Toast.makeText(this, "Error displaying weather data", Toast.LENGTH_SHORT).show();
        }
    }

    private void checkLocationPermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, 
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 
                Constants.LOCATION_PERMISSION_REQUEST_CODE);
        } else {
            getCurrentLocation();
        }
    }

    private void getCurrentLocation() {
        locationHelper.getCurrentLocation(this, new LocationHelper.LocationListener() {
            @Override
            public void onLocationReceived(double latitude, double longitude) {
                viewModel.fetchCurrentWeather(latitude, longitude);
            }

            @Override
            public void onLocationError(String error) {
                Toast.makeText(MainActivity.this, error, Toast.LENGTH_SHORT).show();
                // Default to a city if location fails
                viewModel.fetchWeatherByCity("London");
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == Constants.LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getCurrentLocation();
            } else {
                Toast.makeText(this, "Location permission denied. Using default city.", Toast.LENGTH_SHORT).show();
                viewModel.fetchWeatherByCity("London");
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (locationHelper != null) {
            locationHelper.stopLocationUpdates();
        }
    }
}