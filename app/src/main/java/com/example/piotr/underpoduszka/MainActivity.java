package com.example.piotr.underpoduszka;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setImage();
    }

    private void setImage() {
        ImageView pillow = (ImageView) findViewById(R.id.pillow);
       // pillow.setImage(R.drawable.pillow);
    }

    public void startMonitor(View view) {
        Intent intent = new Intent(this, MonitoringActivity.class);
        startActivity(intent);
    }
}
