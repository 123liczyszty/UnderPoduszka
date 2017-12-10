package underpoduszka;

import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.piotr.underpoduszka.R;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

/*
 * Klasa generująca wykresy i analizująca wartości z sensorów
 */
@RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
public class AnalysisActivity extends AppCompatActivity {
    // inicjalizacja zmiennych
    String cityChoosen = "";
    String userNameChoosen = "";
    String dateChoosen = "";
    Spinner nameSpinner;

    // funkcja zwracajaca widok poczatkowy aplikacji
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analysis);
        nameSpinner = (Spinner) findViewById(R.id.spinnerName);
        getListOfCity();
    }

    // funkcja pobierająca listę wpisanych miast
    private void getListOfCity() {
        new GetCityList().execute();
    }

    // funkcja pobierająca listę użytkowników przypisanych do danych miast
    private void getUsersOnCity() {

        new GetUsersOnCity().execute();
    }

    // funkcja pobierająca datę
    private void getDate() {
        new GetDate().execute();
    }

    // funkcja generująca wykrsy
    public void gyroGraph(View view) {
        new GenerateGraph().execute();
    }


    /*
    * Klasa umożliwiająca połączenie z bazą danych i asynchroniczne pobieranie listy miast
    */
    private class GetCityList extends AsyncTask<Void,Void,String[]> {
        @Override
        protected String[] doInBackground(Void... voids) {
            RestTemplate restTemplate = new RestTemplate();
            String url = "http://192.168.0.6:8080/getCities";
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

    /*
    * Klasa umożliwiająca połączenie z bazą danych i asynchroniczne pobieranie listy użytkowników
    */
    private class GetUsersOnCity extends AsyncTask<Void,Void,String[]> {
        @Override
        protected String[] doInBackground(Void... voids) {
            RestTemplate restTemplate = new RestTemplate();
            String url = "http://192.168.0.6:8080/getUserList/{city}";
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

    /*
    * Klasa umożliwiająca połączenie z bazą danych i asynchroniczne pobieranie daty
    */
    private class GetDate extends AsyncTask <Void,Void,String[]> {
        @Override
        protected String[] doInBackground(Void... voids) {
            RestTemplate restTemplate = new RestTemplate();
            String url = "http://192.168.0.6:8080/getDate/{city}/{userName}";
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


    /*
    * Klasa umożliwiająca połączenie z bazą danych i asynchroniczne generowanie wykresów na podstawie wartości z sensorów
    */
    private class GenerateGraph extends AsyncTask<Void,Void,DataModel[]>{
        @Override
        protected DataModel[] doInBackground(Void... voids) {
            RestTemplate restTemplate = new RestTemplate();
            String url = "http://192.168.0.6:8080/getDataSets/{city}/{userName}/{time}";
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            ResponseEntity<DataModel[]> responseEntity = restTemplate.getForEntity(url, DataModel[].class, cityChoosen, userNameChoosen, dateChoosen);
            return responseEntity.getBody();
        }

        @Override
        protected void onPostExecute(DataModel[] dataModels) {
            super.onPostExecute(dataModels);
        }
    }
}
