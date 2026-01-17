package com.piyush.weatherx.data.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class Weather {
    @SerializedName("coord")
    private Coord coord;
    
    @SerializedName("weather")
    private List<WeatherInfo> weather;
    
    @SerializedName("base")
    private String base;
    
    @SerializedName("main")
    private Main main;
    
    @SerializedName("visibility")
    private int visibility;
    
    @SerializedName("wind")
    private Wind wind;
    
    @SerializedName("clouds")
    private Clouds clouds;
    
    @SerializedName("dt")
    private long dt;
    
    @SerializedName("sys")
    private Sys sys;
    
    @SerializedName("timezone")
    private int timezone;
    
    @SerializedName("id")
    private int id;
    
    @SerializedName("name")
    private String name;
    
    @SerializedName("cod")
    private int cod;

    // Getters
    public Coord getCoord() { return coord; }
    public List<WeatherInfo> getWeather() { return weather; }
    public String getBase() { return base; }
    public Main getMain() { return main; }
    public int getVisibility() { return visibility; }
    public Wind getWind() { return wind; }
    public Clouds getClouds() { return clouds; }
    public long getDt() { return dt; }
    public Sys getSys() { return sys; }
    public int getTimezone() { return timezone; }
    public int getId() { return id; }
    public String getName() { return name; }
    public int getCod() { return cod; }

    // Setters
    public void setName(String name) { this.name = name; }
    public void setMain(Main main) { this.main = main; }
    public void setWind(Wind wind) { this.wind = wind; }
    public void setWeather(List<WeatherInfo> weather) { this.weather = weather; }
    public void setSys(Sys sys) { this.sys = sys; }

    public static class Coord {
        @SerializedName("lon")
        private double lon;
        @SerializedName("lat")
        private double lat;

        public double getLon() { return lon; }
        public double getLat() { return lat; }
    }

    public static class WeatherInfo {
        @SerializedName("id")
        private int id;
        @SerializedName("main")
        private String main;
        @SerializedName("description")
        private String description;
        @SerializedName("icon")
        private String icon;

        public int getId() { return id; }
        public String getMain() { return main; }
        public String getDescription() { return description; }
        public String getIcon() { return icon; }
        
        public void setMain(String main) { this.main = main; }
        public void setDescription(String description) { this.description = description; }
        public void setIcon(String icon) { this.icon = icon; }
    }

    public static class Main {
        @SerializedName("temp")
        private double temp;
        @SerializedName("feels_like")
        private double feelsLike;
        @SerializedName("temp_min")
        private double tempMin;
        @SerializedName("temp_max")
        private double tempMax;
        @SerializedName("pressure")
        private int pressure;
        @SerializedName("humidity")
        private int humidity;

        public double getTemp() { return temp; }
        public double getFeelsLike() { return feelsLike; }
        public double getTempMin() { return tempMin; }
        public double getTempMax() { return tempMax; }
        public int getPressure() { return pressure; }
        public int getHumidity() { return humidity; }
        
        public void setTemp(double temp) { this.temp = temp; }
        public void setFeelsLike(double feelsLike) { this.feelsLike = feelsLike; }
        public void setHumidity(int humidity) { this.humidity = humidity; }
        public void setPressure(int pressure) { this.pressure = pressure; }
    }

    public static class Wind {
        @SerializedName("speed")
        private double speed;
        @SerializedName("deg")
        private int deg;

        public double getSpeed() { return speed; }
        public int getDeg() { return deg; }
        
        public void setSpeed(double speed) { this.speed = speed; }
    }

    public static class Clouds {
        @SerializedName("all")
        private int all;

        public int getAll() { return all; }
    }

    public static class Sys {
        @SerializedName("type")
        private int type;
        @SerializedName("id")
        private int id;
        @SerializedName("country")
        private String country;
        @SerializedName("sunrise")
        private long sunrise;
        @SerializedName("sunset")
        private long sunset;

        public int getType() { return type; }
        public int getId() { return id; }
        public String getCountry() { return country; }
        public long getSunrise() { return sunrise; }
        public long getSunset() { return sunset; }
        
        public void setCountry(String country) { this.country = country; }
    }
}