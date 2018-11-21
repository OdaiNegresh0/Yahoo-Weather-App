package com.example.hp.yahooweatherapp.data;

import org.json.JSONObject;

public class Units implements JSONPopulator{
    private String temperature;

    public String getTemperature() {
        return temperature;
    }

    @Override
    public void poupulate(JSONObject data) {
        temperature = data.optString("temperature");
    }
}
