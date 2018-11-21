package com.example.hp.yahooweatherapp.data;

import org.json.JSONObject;

public class Condition implements JSONPopulator {
    private  int code;
    private int temperature;
    private  String describtoion;

    public int getCode() {
        return code;
    }

    public int getTemperature() {
        return temperature;
    }

    public String getDescribtoion() {
        return describtoion;
    }

    @Override
    public void poupulate(JSONObject data) {
        code = data.optInt("code");
        temperature = data.optInt("temp");
        describtoion = data.optString("text");
    }
}
