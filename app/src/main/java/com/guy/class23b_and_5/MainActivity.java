package com.guy.class23b_and_5;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.google.android.material.textview.MaterialTextView;
import com.google.gson.Gson;
import com.guy.class23b_and_5.objects.WeatherModel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.WeakHashMap;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {

    private MaterialTextView main_LBL_temp;
    private MaterialTextView main_LBL_humidity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        main_LBL_temp = findViewById(R.id.main_LBL_temp);
        main_LBL_humidity = findViewById(R.id.main_LBL_humidity);


        new Thread(new Runnable() {
            @Override
            public void run() {
                String API_KEY = "";
                String lat = "32.11321986808";
                String lon = "34.81776352918";

                String http = String.format("https://api.openweathermap.org/data/2.5/weather?lat=%s&lon=%s&units=metric&appid=%s", lat, lon, API_KEY);
                Log.d("pttt", "HTTP:" + http);

                String json = makeApiCall(http);
                Log.d("pttt", json);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        handleResponce(json);
                    }
                });
            }
        }).start();

    }

    private void handleResponce(String json) {
        Log.d("pttt", "Thread= " + Thread.currentThread().getName());
        // 1. cast json string to object

        WeatherModel weatherModel = new Gson().fromJson(json, WeatherModel.class);
        Log.d("pttt", "Temperature= " + weatherModel.getMain().getTemp());

        // 2. extract temp, clouds, a.g.

        double temp = weatherModel.getMain().getTemp();
        double maxTemp = weatherModel.getMain().getTemp_max();
        double minTemp = weatherModel.getMain().getTemp_min();
        int humidity = weatherModel.getMain().getHumidity();
        int clouds = weatherModel.getClouds().getAll();
        double windSpeed = weatherModel.getWind().getSpeed();
        double windDirection = weatherModel.getWind().getDeg();
        long sunrise = weatherModel.getSys().getSunrise();
        long sunset = weatherModel.getSys().getSunset();

        // 3. update UI

        main_LBL_temp.setText(new DecimalFormat("##.##").format(temp));
        main_LBL_humidity.setText(humidity + "%");
    }

    public static String makeApiCall(String url) {
        String data = "";
        HttpsURLConnection con = null;
        try {
            HttpsURLConnection con2 = (HttpsURLConnection) new URL(url).openConnection();
            con2.connect();
            BufferedReader br = new BufferedReader(new InputStreamReader(con2.getInputStream()));
            StringBuilder sb = new StringBuilder();
            while (true) {
                String readLine = br.readLine();
                String line = readLine;
                if (readLine == null) {
                    break;
                }
                sb.append(line + "\n");
            }
            br.close();
            data = sb.toString();
            if (con2 != null) {
                try {
                    con2.disconnect();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        } catch (MalformedURLException ex2) {
            ex2.printStackTrace();
            if (con != null) {
                con.disconnect();
            }
        } catch (IOException ex3) {
            ex3.printStackTrace();
            if (con != null) {
                con.disconnect();
            }
        } catch (Throwable th) {
            if (con != null) {
                try {
                    con.disconnect();
                } catch (Exception ex4) {
                    ex4.printStackTrace();
                }
            }
            throw th;
        }
        return data;
    }
}

