package com.piyush.weatherx.data.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class Forecast {
    @SerializedName("cod")
    private String cod;
    
    @SerializedName("message")
    private int message;
    
    @SerializedName("cnt")
    private int cnt;
    
    @SerializedName("list")
    private List<ForecastItem> list;
    
    @SerializedName("city")
    private City city;

    public String getCod() { return cod; }
    public int getMessage() { return message; }
    public int getCnt() { return cnt; }
    public List<ForecastItem> getList() { return list; }
    public City getCity() { return city; }
    
    public void setList(List<ForecastItem> list) { this.list = list; }

    public static class ForecastItem {
        @SerializedName("dt")
        private long dt;
        
        @SerializedName("main")
        private Weather.Main main;
        
        @SerializedName("weather")
        private List<Weather.WeatherInfo> weather;
        
        @SerializedName("clouds")
        private Weather.Clouds clouds;
        
        @SerializedName("wind")
        private Weather.Wind wind;
        
        @SerializedName("visibility")
        private int visibility;
        
        @SerializedName("pop")
        private double pop;
        
        @SerializedName("dt_txt")
        private String dtTxt;

        public long getDt() { return dt; }
        public Weather.Main getMain() { return main; }
        public List<Weather.WeatherInfo> getWeather() { return weather; }
        public Weather.Clouds getClouds() { return clouds; }
        public Weather.Wind getWind() { return wind; }
        public int getVisibility() { return visibility; }
        public double getPop() { return pop; }
        public String getDtTxt() { return dtTxt; }
        
        public void setDt(long dt) { this.dt = dt; }
        public void setMain(Weather.Main main) { this.main = main; }
        public void setWeather(List<Weather.WeatherInfo> weather) { this.weather = weather; }
        public void setDtTxt(String dtTxt) { this.dtTxt = dtTxt; }
    }

    public static class City {
        @SerializedName("id")
        private int id;
        
        @SerializedName("name")
        private String name;
        
        @SerializedName("coord")
        private Weather.Coord coord;
        
        @SerializedName("country")
        private String country;
        
        @SerializedName("population")
        private int population;
        
        @SerializedName("timezone")
        private int timezone;
        
        @SerializedName("sunrise")
        private long sunrise;
        
        @SerializedName("sunset")
        private long sunset;

        public int getId() { return id; }
        public String getName() { return name; }
        public Weather.Coord getCoord() { return coord; }
        public String getCountry() { return country; }
        public int getPopulation() { return population; }
        public int getTimezone() { return timezone; }
        public long getSunrise() { return sunrise; }
        public long getSunset() { return sunset; }
    }
}