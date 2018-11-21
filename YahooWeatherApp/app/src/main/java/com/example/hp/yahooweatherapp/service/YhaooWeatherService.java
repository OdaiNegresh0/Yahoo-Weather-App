package com.example.hp.yahooweatherapp.service;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.AsyncTask;

import com.example.hp.yahooweatherapp.data.Channel2;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.channels.Channel;

public class YhaooWeatherService {
    private WeatherServiceCallback callback;
    private String location;
    private Exception error;

    public YhaooWeatherService (WeatherServiceCallback callback)
    {
        this.callback = callback;
    }


    public String getLocation(){
        return location;
    }



    @SuppressLint("StaticFieldLeak")
    public void refreshWeather(String l){
        this.location = l;
        new AsyncTask<String, Void, String>() {
            @Override
            protected String doInBackground(String... strings) {
                String YQL = String.format("select * from weather.forecast where woeid in (select woeid from geo.places(1) where text=\"%s\")", strings[0]);

                String endPoint = String.format("https://query.yahooapis.com/v1/public/yql?q=%s&format=json" , Uri.encode(YQL));

                try {
                    URL url = new URL(endPoint);

                    URLConnection connection = url.openConnection();

                    InputStream inputStream = connection.getInputStream();

                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

                    StringBuilder result = new StringBuilder();
                    String line;
                     while ((line = reader.readLine())!= null)
                     {
                         result.append(line);
                     }

                     return result.toString();

                } catch (Exception e) {
                    error = e;
                }
                return null;
            }

            @Override
            protected void onPostExecute(String s) {

                if(s == null && error != null){
                    callback.serviceFailure(error);
                    return;
                }


                try {
                    JSONObject data = new JSONObject(s);

                    JSONObject queryResult = data.optJSONObject("query");

                    int count = queryResult.optInt("count");

                    if(count == 0){
                        callback.serviceFailure(new LocationWeatherException("No wather information found for "+location));
                        return;
                    }
                    Channel2 channel2  = new Channel2();
                    channel2.poupulate(queryResult.optJSONObject("results").optJSONObject("channel"));

                    callback.serviceSuccess(channel2);


                } catch (JSONException e) {
                   callback.serviceFailure(e);
                }
            }
        }.execute(location);
    }

    public class LocationWeatherException extends Exception{

        public LocationWeatherException(String detailMessage){
            super(detailMessage);
        }
    }
}
