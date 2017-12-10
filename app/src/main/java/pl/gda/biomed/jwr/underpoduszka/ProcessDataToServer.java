package underpoduszka;


import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;

import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

/*
 * Klasa synchronizująca obsługę bazy danych
 */
@RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
public class ProcessDataToServer extends AsyncTask<Void,Void,Void> {
    DataModel dataModel;

    //konstruktor klasy
    public ProcessDataToServer(DataModel model)  {
        this.dataModel = model;
    }

    // funkcja przesylajaca wartosci do bazy danych
    @Override
    protected Void doInBackground(Void... voids) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://192.168.0.6:8080/getData/{gyroscope}/{accelerometer}/{time}/{userName}/{city}";
        restTemplate.getMessageConverters().add(new MappingJacksonHttpMessageConverter());
        //wartosci przesylane ''na sztywno'' zgodne z url'em
        restTemplate.getForObject(url, DataModel.class,1,2,3,4,5);
        return null;
    }
}
