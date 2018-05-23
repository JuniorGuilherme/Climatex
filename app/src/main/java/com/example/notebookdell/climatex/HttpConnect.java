package com.example.notebookdell.climatex;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Notebook Dell on 16/05/2018.
 */

public class HttpConnect {
    public static String URL="https://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20weather.forecast%20where%20woeid%20in%20(select%20woeid%20from%20geo.places(1)%20where%20text%3D%22Brazil%2C%20S%C3%A3o%20Jo%C3%A3o%20do%20Sul%22)&format=json&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys";


    private static HttpURLConnection connectar(String urlWebservice) {

        final int SEGUNDOS = 10000;

        try {
            java.net.URL url = new URL(urlWebservice);
            HttpURLConnection conexao = (HttpURLConnection) url.openConnection();
            conexao.setReadTimeout(10 * SEGUNDOS);
            conexao.setConnectTimeout(15 * SEGUNDOS);
            conexao.setRequestMethod("GET");
            conexao.setDoInput(true);
            conexao.setDoOutput(false);
            conexao.connect();
            return conexao;

        } catch (IOException e) {
            Log.d("ERRO", e.getMessage());
            e.printStackTrace();
        }

        return null;
    }

    public static boolean hasConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        return (info != null && info.isConnected());
    }


    public static Forecast getForecastFromJson(JSONObject json){
        String previsao;
        Integer highTemp, lowTemp;
        String dayWeek;
        String date;
        Forecast time=null;

        try {
            previsao=json.getString("text");
            dayWeek=json.getString("day");
            date=json.getString("date");
            highTemp=json.getInt("high");
            lowTemp=json.getInt("low");
            time = new Forecast(previsao, highTemp, lowTemp, dayWeek, date);
        }catch (JSONException ex){
            Log.d("ERROR",ex.getMessage());
        }
        return time;
    }


    public static ArrayList<Forecast> readJson(JSONObject json) {

        ArrayList<Forecast> arrayList = new ArrayList<>();
        try {
             JSONObject results = json.getJSONObject("results");
            JSONArray jsonForecast = results.getJSONArray("forecast");

            for(Integer i=0; i<jsonForecast.length(); i++){
                arrayList.add(getForecastFromJson(jsonForecast.getJSONObject(i)));
            }


        } catch (JSONException e) {

            Log.d("Json", e.getMessage());
            e.printStackTrace();
        }
        return arrayList;

    }

    public static ArrayList<Forecast> loadForecastWeek() {
        try {
            HttpURLConnection connection = connectar(URL);
            int response = connection.getResponseCode();
            if (response == HttpURLConnection.HTTP_OK) {
                InputStream inputStream = connection.getInputStream();
                JSONObject json = new JSONObject(bytesParaString(inputStream));
                ArrayList<Forecast>  forecastWeek =readJson(json);
                return forecastWeek;
            }

        } catch (Exception e) {
            Log.d("ERRO", e.getMessage());
        }
        return null;
    }
    private static String bytesParaString(InputStream inputStream) {
        byte[] buffer = new byte[1024];
        ByteArrayOutputStream bufferzao = new ByteArrayOutputStream();
        int byteslidos;
        try {
            while ((byteslidos = inputStream.read(buffer)) != -1) {
                bufferzao.write(buffer, 0, byteslidos);

            }
            return new String(bufferzao.toByteArray(), "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
