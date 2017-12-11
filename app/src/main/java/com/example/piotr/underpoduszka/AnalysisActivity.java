package com.example.piotr.underpoduszka;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
public class AnalysisActivity extends AppCompatActivity {
    String cityChoosen = "";
    String userNameChoosen = "";
    String dateChoosen = "";
    Spinner nameSpinner;
    AsyncTask myTask;
    GraphView graph;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analysis);
        nameSpinner = (Spinner) findViewById(R.id.spinnerName);
        graph = (GraphView) findViewById(R.id.graph);
        graph.getGridLabelRenderer().setTextSize(10f);
        getListOfCity();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        myTask.cancel(true);
        graph.removeAllSeries();
    }

    private void getListOfCity() {
        new GetCityList().execute();
    }

    private void getUsersOnCity() {

        new GetUsersOnCity().execute();
    }

    private void getDate() {
        new GetDate().execute();
    }

    public void gyroGraph(View view) {
        if(myTask != null) myTask.cancel(true);
        graph.removeAllSeries();
        myTask = new GenerateGraph("Gyroscope").execute();



    }

    public void accelGraph(View view) {
        if(myTask != null) myTask.cancel(true);
        graph.removeAllSeries();
        myTask = new GenerateGraph("Accelerometer").execute();
    }

    public void microGraph(View view) {
        if(myTask != null) myTask.cancel(true);
        graph.removeAllSeries();
        myTask = new GenerateGraph("Microphone").execute();
    }


    private class GetCityList extends AsyncTask<Void,Void,String[]> {
        @Override
        protected String[] doInBackground(Void... voids) {
            RestTemplate restTemplate = new RestTemplate();
            String url = "http://192.168.0.34:8080/getCities";
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            ResponseEntity<String[]> responseEntity = restTemplate.getForEntity(url, String[].class);
            return responseEntity.getBody();
        }

        @Override
        protected void onPostExecute(String[] strings) {
            super.onPostExecute(strings);
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(AnalysisActivity.this, android.R.layout.simple_spinner_dropdown_item, strings);
            arrayAdapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
            Spinner spinner = (Spinner) findViewById(R.id.spinnerCity);
            spinner.setAdapter(arrayAdapter);
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                   cityChoosen = parent.getItemAtPosition(position).toString();
                   getUsersOnCity();

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
        }
    }

    private class GetUsersOnCity extends AsyncTask<Void,Void,String[]> {
        @Override
        protected String[] doInBackground(Void... voids) {
            RestTemplate restTemplate = new RestTemplate();
            String url = "http://192.168.0.34:8080/getUserList/{city}";
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            ResponseEntity<String[]> responseEntity = restTemplate.getForEntity(url, String[].class, cityChoosen);
            return responseEntity.getBody();
        }

        @Override
        protected void onPostExecute(String[] strings) {
            super.onPostExecute(strings);
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(AnalysisActivity.this, android.R.layout.simple_spinner_dropdown_item, strings);
            arrayAdapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
            Spinner spinner = (Spinner) findViewById(R.id.spinnerName);
            spinner.setAdapter(arrayAdapter);
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    userNameChoosen = parent.getItemAtPosition(position).toString();
                    getDate() ;
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
        }
    }


    private class GetDate extends AsyncTask <Void,Void,String[]> {
        @Override
        protected String[] doInBackground(Void... voids) {
            RestTemplate restTemplate = new RestTemplate();
            String url = "http://192.168.0.34:8080/getDate/{city}/{userName}";
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            ResponseEntity<String[]> responseEntity = restTemplate.getForEntity(url, String[].class, cityChoosen, userNameChoosen);
            return responseEntity.getBody();
        }

        @Override
        protected void onPostExecute(String[] strings) {
            super.onPostExecute(strings);
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(AnalysisActivity.this, android.R.layout.simple_spinner_dropdown_item, strings);
            arrayAdapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
            Spinner spinner = (Spinner) findViewById(R.id.spinnerDate);
            spinner.setAdapter(arrayAdapter);
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    dateChoosen = parent.getItemAtPosition(position).toString();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
        }
    }

    private class GenerateGraph extends AsyncTask<Void,Void,DataModel[]>{
        String choosenGraph;
        public GenerateGraph(String option) {
            this.choosenGraph = option;
        }

        @Override
        protected DataModel[] doInBackground(Void... voids) {
            RestTemplate restTemplate = new RestTemplate();
            String url = "http://192.168.0.34:8080/getDataSets/{city}/{userName}/{time}";
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            ResponseEntity<DataModel[]> responseEntity = restTemplate.getForEntity(url, DataModel[].class, cityChoosen, userNameChoosen, dateChoosen);
            return responseEntity.getBody();


        }


        @Override
        protected void onPostExecute(DataModel[] dataModels) {
            super.onPostExecute(dataModels);
            List<String> times = new ArrayList();
            List<String> values = new ArrayList();


            for(DataModel x : dataModels){
                times.add(x.currentTime);

                switch(choosenGraph){
                    case "Gyroscope":
                        values.add(x.gyroscopeValue);
                        break;
                    case "Accelerometer":
                        values.add(x.acceleroMeterValue);
                        break;
                    case "Microphone":
                        values.add(x.microphoneValue);
                        break;
                    default:
                        break;
                }
            }
            DateFormat parser = new SimpleDateFormat("hh:mm:ss");
            DataPoint[] dataPoints = new DataPoint[times.size()];
            for( int i = 0; i < times.size(); i++){
                Date date = new Date();
                try {
                    date = parser.parse(times.get(i));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Double x = Double.valueOf(String.valueOf(date.getHours()) + '.' + String.valueOf(date.getMinutes()));
                Double y = Double.valueOf(values.get(i));
                dataPoints[i] = new DataPoint(x,y);
            }

            LineGraphSeries<DataPoint> series = new LineGraphSeries<>(dataPoints);

            graph.addSeries(series);
            series.setDrawDataPoints(true);
            series.setColor(Color.RED);
            series.setDataPointsRadius(10);
            graph.setTitle("Wykres : " + choosenGraph);
        }
    }
}
