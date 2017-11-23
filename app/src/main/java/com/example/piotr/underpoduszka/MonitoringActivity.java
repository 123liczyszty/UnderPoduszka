package com.example.piotr.underpoduszka;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class MonitoringActivity extends AppCompatActivity {
    Sensor gyroscopeSensor;
    Sensor accelerometerSensor;
    SensorManager manager ;
    TextView gyroscope;
    TextView acelerometer;
    float last_Update  = 0;
    TextView microphone;
    Button button;
    SensorEventListener gyroscopeListener;
    SensorEventListener accelerometerListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monitoring);
        gyroscope = (TextView) findViewById(R.id.gyroscope);
        acelerometer = (TextView) findViewById(R.id.akcelometer);
        microphone = (TextView) findViewById(R.id.microphone);
    }

    private void GyroscopeListener() {
        gyroscopeSensor = manager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);


        gyroscopeListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                float x = event.values[0];
                float y = event.values[1];
                String t1 = String.format(Locale.getDefault(),"%.2f",x);
                String t2 = String.format(Locale.getDefault(),"%.2f",y);
                gyroscope.setText(t1+"\n"+t2+"\n");
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };
        manager.registerListener(gyroscopeListener,gyroscopeSensor, SensorManager.SENSOR_DELAY_FASTEST);
    }

    public void StartStop(View view) {
        button = (Button) findViewById(R.id.startstop);
        if(button.getText() == "STOP"){
            button.setText("START");
            manager.unregisterListener(gyroscopeListener, gyroscopeSensor);
        }
        else {
            manager = (SensorManager) getSystemService(SENSOR_SERVICE);
            button.setText("STOP");
            GyroscopeListener();
            AcelerometerListener();
            //MicrophoneListener();
        }
    }

    private void MicrophoneListener() {
        accelerometerSensor = manager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        gyroscopeListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {

                Toast.makeText(getApplicationContext(), "sadsa", Toast.LENGTH_SHORT).show();
                float x = event.values[0];
                float y = event.values[1];
                float z = event.values[3];



            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };
        manager.registerListener(gyroscopeListener,gyroscopeSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    private void AcelerometerListener() {
        accelerometerSensor = manager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);


        accelerometerListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                float x = event.values[0];
                float y = event.values[1];
                float z = event.values[2];
                String t1 = String.format(Locale.getDefault(),"%.2f",x);
                String t2 = String.format(Locale.getDefault(),"%.2f",y);
                String t3 = String.format(Locale.getDefault(),"%.2f",z);
                acelerometer.setText(t1+"\n"+t2+"\n"+t3);
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };
        manager.registerListener(accelerometerListener,accelerometerSensor, SensorManager.SENSOR_DELAY_FASTEST);
    }
}
