package com.piyush.weatherx.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.piyush.weatherx.R;
import com.piyush.weatherx.data.model.Forecast;
import com.piyush.weatherx.utils.Constants;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ForecastAdapter extends RecyclerView.Adapter<ForecastAdapter.ForecastViewHolder> {
    private List<Forecast.ForecastItem> forecastList = new ArrayList<>();

    public void setForecastList(List<Forecast.ForecastItem> forecastList) {
        this.forecastList = forecastList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ForecastViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_forecast, parent, false);
        return new ForecastViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ForecastViewHolder holder, int position) {
        Forecast.ForecastItem item = forecastList.get(position);
        
        // Format date
        Date date = new Date(item.getDt() * 1000);
        SimpleDateFormat dayFormat = new SimpleDateFormat("EEE", Locale.getDefault());
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
        
        holder.dayTextView.setText(dayFormat.format(date));
        holder.timeTextView.setText(timeFormat.format(date));
        
        // Temperature
        int temp = (int) Math.round(item.getMain().getTemp());
        holder.temperatureTextView.setText(temp + "°C");
        
        // Weather description
        if (item.getWeather() != null && !item.getWeather().isEmpty()) {
            holder.descriptionTextView.setText(item.getWeather().get(0).getDescription());
            
            // Weather icon
            String iconUrl = Constants.WEATHER_ICON_URL + item.getWeather().get(0).getIcon() + "@2x.png";
            Glide.with(holder.itemView.getContext())
                    .load(iconUrl)
                    .into(holder.weatherIconImageView);
        }
    }

    @Override
    public int getItemCount() {
        return forecastList.size();
    }

    static class ForecastViewHolder extends RecyclerView.ViewHolder {
        TextView dayTextView, timeTextView, temperatureTextView, descriptionTextView;
        ImageView weatherIconImageView;

        public ForecastViewHolder(@NonNull View itemView) {
            super(itemView);
            dayTextView = itemView.findViewById(R.id.dayTextView);
            timeTextView = itemView.findViewById(R.id.timeTextView);
            temperatureTextView = itemView.findViewById(R.id.temperatureTextView);
            descriptionTextView = itemView.findViewById(R.id.descriptionTextView);
            weatherIconImageView = itemView.findViewById(R.id.weatherIconImageView);
        }
    }
}