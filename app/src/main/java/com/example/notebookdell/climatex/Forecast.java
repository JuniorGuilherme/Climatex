package com.example.notebookdell.climatex;

/**
 * Created by Notebook Dell on 16/05/2018.
 */

public class Forecast {
    private String previsao;
    private Integer highTemp, lowTemp;
    private String dayWeek;
    private String date;


    public Forecast(String previsao, Integer highTemp, Integer lowTemp, String dayWeek, String date){
        this.previsao = previsao;
        this.highTemp = highTemp;
        this.lowTemp = lowTemp;
        this.dayWeek = dayWeek;
        this.date = date;
    }

    public String getPrevisao() {
        return previsao;
    }

    public String getHighTemp() {
        return "Temperatura: "+highTemp.toString();
    }

    public String getLowTemp() {
        return "Temperatura: "+lowTemp.toString();
    }

    public String getDayWeek() {
        return dayWeek;
    }

    public String getDate() {
        return date;
    }


}
