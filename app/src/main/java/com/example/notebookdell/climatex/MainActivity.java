package com.example.notebookdell.climatex;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ForecastTask mTask;
    ArrayList<Forecast> mForecast;
    ListView mListForecast;
    ArrayAdapter<Forecast> mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mListForecast = findViewById(R.id.listForecast);
        search();
    }

    private void search() {
        if (mForecast == null) {
            mForecast = new ArrayList<Forecast>();
        }

        mAdapter = new ForecastAdapter(getApplicationContext(), mForecast);
        mListForecast.setAdapter(mAdapter);
        if (mTask == null) {
            if (HttpConnect.hasConnected(this)) {
                startDownload();
            } else {
                Toast.makeText(getApplicationContext(), "Sem conexão...", Toast.LENGTH_LONG).show();
            }
        } else if (mTask.getStatus() == AsyncTask.Status.RUNNING) {
            Toast.makeText(getApplicationContext(), "......", Toast.LENGTH_LONG).show();
        }
    }


    public void startDownload() {
        if (mTask == null || mTask.getStatus() != AsyncTask.Status.RUNNING) {
            mTask = new ForecastTask();
            mTask.execute();
        }
    }

    class ForecastTask extends AsyncTask<Void, Void, ArrayList<Forecast>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //showProgress(true);
            Toast.makeText(getApplicationContext(), "Pronto...", Toast.LENGTH_LONG).show();
        }

        @Override
        protected ArrayList<Forecast> doInBackground(Void... strings) {
            ArrayList<Forecast> forecastWeek = HttpConnect.loadForecastWeek();
            return forecastWeek;
        }
        @Override
        protected void onPostExecute(ArrayList<Forecast> forecastWeek) {
            super.onPostExecute(forecastWeek);
            //     showProgress(false);
            if (forecastWeek != null) {
                mForecast.clear();
                mForecast.addAll(forecastWeek);
                mAdapter.notifyDataSetChanged();
            } else {

                Toast.makeText(getApplicationContext(), "Buscando...", Toast.LENGTH_LONG).show();
            }
        }
    }
}
