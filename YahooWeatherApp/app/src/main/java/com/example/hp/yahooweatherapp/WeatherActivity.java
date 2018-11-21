package com.example.hp.yahooweatherapp;

import android.app.ProgressDialog;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hp.yahooweatherapp.data.Channel2;
import com.example.hp.yahooweatherapp.data.Item;
import com.example.hp.yahooweatherapp.service.WeatherServiceCallback;
import com.example.hp.yahooweatherapp.service.YhaooWeatherService;

import java.nio.channels.Channel;

public class WeatherActivity extends AppCompatActivity implements WeatherServiceCallback {

    private ImageView weatherIconImageView;
    private TextView temperatureTextView;
    private TextView conditionTextView;
    private TextView locationTextView;

    private YhaooWeatherService service;
    private ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        weatherIconImageView = (ImageView)findViewById(R.id.weatherIconImageView);
        temperatureTextView = (TextView) findViewById(R.id.temperatureTextView);
        conditionTextView = (TextView)findViewById(R.id.conditionTextView);
        locationTextView = (TextView)findViewById(R.id.locationTextView);

        service = new YhaooWeatherService(this);

        dialog = new ProgressDialog(this);
        dialog.setMessage("Loading...");
        dialog.show();

        service.refreshWeather("Irbid , Jordan");
    }

    @Override
    public void serviceSuccess(Channel2 channel2) {
        dialog.hide();

        Item item = channel2.getItem();
        int resourceId = getResources().getIdentifier("drawable/icon_"+ item.getCondition().getCode() , null , getPackageName());

        Drawable weatherIconDrawable = getResources().getDrawable(resourceId);
        weatherIconImageView.setImageDrawable(weatherIconDrawable);

        temperatureTextView.setText(item.getCondition().getTemperature()+"\u00B0"+channel2.getUnits().getTemperature());

        conditionTextView.setText(item.getCondition().getDescribtoion());

        locationTextView.setText(service.getLocation());
    }


    @Override
    public void serviceFailure(Exception exception) {
        dialog.hide();
        Toast.makeText(this , exception.getMessage() , Toast.LENGTH_LONG).show();
    }
}
