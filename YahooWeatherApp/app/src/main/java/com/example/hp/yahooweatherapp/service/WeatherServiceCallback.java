package com.example.hp.yahooweatherapp.service;


import com.example.hp.yahooweatherapp.data.Channel2;

public interface WeatherServiceCallback {
    void serviceSuccess(Channel2 channel2);
    void serviceFailure(Exception exception);

}
