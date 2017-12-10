package underpoduszka;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/*
 * Klasa przechowująca model danych dla bazy danych
 */
public class DataModel {

    public String gyroscopeValue;

    public String microPhoneValue;

    public String acceleroMeterValue;

    public String currentTime;

    public String userName;

    public String city;

    public DataModel(String gyroscopeValue, String acceleroMeterValue, String UserName, String City) {
        this.gyroscopeValue = gyroscopeValue;
        this.acceleroMeterValue = acceleroMeterValue;

        // ustawienie czasu pobieranego automatycznie przez aplikacje
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        this.currentTime = simpleDateFormat.format(calendar.getTime());
        this.userName = UserName;
        this.city = City;
    }

    // konstruktor klasy
    public DataModel(String gyroscopeValue, String acceleroMeterValue, String UserName, String City, String microPhoneValue) {
        this.gyroscopeValue = gyroscopeValue;
        this.acceleroMeterValue = acceleroMeterValue;

        // ustawienie czasu pobieranego automatycznie przez aplikacje
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        this.currentTime = simpleDateFormat.format(calendar.getTime());
        this.userName = UserName;
        this.city = City;
        this.microPhoneValue = microPhoneValue;
    }

    // konstruktor klasy - pusty
    public DataModel(){

    }
}
