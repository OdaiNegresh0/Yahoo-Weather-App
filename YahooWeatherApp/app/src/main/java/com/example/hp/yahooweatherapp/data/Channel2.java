package com.example.hp.yahooweatherapp.data;

import org.json.JSONObject;

public class Channel2 implements JSONPopulator{
    private Units units;
    private Item item;

    public Units getUnits() {
        return units;
    }

    public Item getItem() {
        return item;
    }


    @Override
    public void poupulate(JSONObject data) {
        units = new Units();
        units.poupulate(data.optJSONObject("units"));

        item = new Item();
        item.poupulate(data.optJSONObject("item"));
    }
}
