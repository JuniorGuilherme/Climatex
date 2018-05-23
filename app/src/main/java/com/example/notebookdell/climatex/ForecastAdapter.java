package com.example.notebookdell.climatex;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Notebook Dell on 16/05/2018.
 */

public class ForecastAdapter extends ArrayAdapter<Forecast> {
    public ForecastAdapter(@NonNull Context context, @NonNull List<Forecast> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Forecast forecastDay= getItem(position);

        View listCurrencies= convertView;
        if(convertView==null) {
            listCurrencies= LayoutInflater.from(getContext()).inflate(R.layout.forecast_item,null);

        }
        TextView date= listCurrencies.findViewById(R.id.txtDate);
        TextView dayWeek= listCurrencies.findViewById(R.id.txtDayWeek);
        TextView previsao= listCurrencies.findViewById(R.id.txtPrevisao);
        TextView highTemp= listCurrencies.findViewById(R.id.txtHighTemp);
        TextView lowTemp= listCurrencies.findViewById(R.id.txtLowTemp);
        date.setText(forecastDay.getDate());
        dayWeek.setText(forecastDay.getDayWeek());
        previsao.setText(forecastDay.getPrevisao());
        highTemp.setText(forecastDay.getHighTemp());
        lowTemp.setText(forecastDay.getLowTemp());


        return listCurrencies;
    }
}
