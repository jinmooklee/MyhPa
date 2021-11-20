package com.jml.myhpa;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    TextView TextView_hPa;
    float hPaOfPressure = 0.0f;
    private SensorManager sensorManager;
    private Sensor pressure;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView_hPa = findViewById(R.id.TextView_hPa);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        pressure = sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE);

        if (pressure != null) {
            sensorManager.registerListener(this, pressure, SensorManager.SENSOR_DELAY_NORMAL);
        } else {
            TextView_hPa.setText("N/A");
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        hPaOfPressure = event.values[0];
        hPaOfPressure = (float) (Math.round(event.values[0]*100)/100.0);

        TextView_hPa.setText( String.valueOf(hPaOfPressure) );
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onResume() {
        // Register a listener for the sensor.
        super.onResume();
        sensorManager.registerListener(this, pressure, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        // Be sure to unregister the sensor when the activity pauses.
        super.onPause();
        sensorManager.unregisterListener(this);
    }
}