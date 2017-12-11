package com.example.piotr.underpoduszka;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Piotr on 28.11.2017.
 */

public class DataModel {
    public String gyroscopeValue;

    public String microphoneValue;

    public String acceleroMeterValue;

    public String currentTime;

    public String userName;

    public String city;

    public DataModel(String gyroscopeValue, String acceleroMeterValue, String microphoneValue, String UserName, String City) {
        this.gyroscopeValue = gyroscopeValue;
        this.acceleroMeterValue = acceleroMeterValue;
        this.microphoneValue = microphoneValue;
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        this.currentTime = simpleDateFormat.format(calendar.getTime());
        this.userName = UserName;
        this.city = City;
    }
    public DataModel(){

    }
}
