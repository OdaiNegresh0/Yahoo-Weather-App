package com.example.hp.yahooweatherapp.data;

import org.json.JSONObject;

public class Item implements JSONPopulator {
    private Condition condition;

    public Condition getCondition() {
        return condition;
    }

    @Override
    public void poupulate(JSONObject data) {
        condition = new Condition();
        condition.poupulate(data.optJSONObject("condition"));
    }
}
