package com.piyush.weatherx.data.model;

import com.google.gson.annotations.SerializedName;

public class WeatherApiResponse {
    @SerializedName("location")
    private Location location;
    
    @SerializedName("current")
    private Current current;

    public Location getLocation() { return location; }
    public Current getCurrent() { return current; }

    public static class Location {
        @SerializedName("name")
        private String name;
        
        @SerializedName("country")
        private String country;

        public String getName() { return name; }
        public String getCountry() { return country; }
    }

    public static class Current {
        @SerializedName("temp_c")
        private double tempC;
        
        @SerializedName("feelslike_c")
        private double feelslikeC;
        
        @SerializedName("humidity")
        private int humidity;
        
        @SerializedName("pressure_mb")
        private double pressureMb;
        
        @SerializedName("wind_kph")
        private double windKph;
        
        @SerializedName("condition")
        private Condition condition;

        public double getTempC() { return tempC; }
        public double getFeelslikeC() { return feelslikeC; }
        public int getHumidity() { return humidity; }
        public double getPressureMb() { return pressureMb; }
        public double getWindKph() { return windKph; }
        public Condition getCondition() { return condition; }
    }

    public static class Condition {
        @SerializedName("text")
        private String text;
        
        @SerializedName("icon")
        private String icon;

        public String getText() { return text; }
        public String getIcon() { return icon; }
    }
}