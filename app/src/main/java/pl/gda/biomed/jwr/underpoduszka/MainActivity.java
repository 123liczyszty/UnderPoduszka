package pl.gda.biomed.jwr.underpoduszka;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.example.piotr.underpoduszka.R;

/*
 * Klasa opisująca fnkcjonalności głównego menu
 */
public class MainActivity extends AppCompatActivity {

    // funkcja zwracajaca widok poczatkowy aplikacji
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setImage();
    }

    // obraz menu głównego aplikacji
    private void setImage() {
        ImageView pillow = (ImageView) findViewById(R.id.pillow);
    }

    // rozpoczecie monitorowania snu użytkownika (mikrofon, akcelerometr, żyroskop)
    public void startMonitor(View view) {
        Intent intent = new Intent(this, MonitoringActivity.class);
        startActivity(intent);
    }

    // analiza otrzymanych wyników w formie wykresów
    public void analyze(View view){
        Intent intent = new Intent(this, AnalysisActivity.class);
        startActivity(intent);
    }
}
