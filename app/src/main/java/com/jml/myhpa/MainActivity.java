package com.jml.myhpa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    TextView TextView_hPa;
    float hPaOfPressure = 0.0f;
    TextView TextView_temp;
    float cTemperature = 0.0f;

    private SensorManager sensorManager;
    private Sensor pressure;
    private Sensor temperature;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView_hPa = findViewById(R.id.TextView_hPa);
        TextView_temp = findViewById(R.id.TextView_Temp);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        pressure = sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE);
        temperature = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);

        if (temperature == null)
            Toast.makeText(getApplicationContext(), "Temperature sensor not present", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        //hPaOfPressure = (float) (Math.round(event.values[0]*100)/100.0);
        Sensor sensor = event.sensor;
        if (sensor.getType() == Sensor.TYPE_PRESSURE)
            hPaOfPressure = event.values[0];
        else if (sensor.getType() == Sensor.TYPE_AMBIENT_TEMPERATURE)
            cTemperature = event.values[0];
        DecimalFormat df = new DecimalFormat("#0.00");
        DecimalFormat df2 = new DecimalFormat("#0.0");
        TextView_hPa.setText(df.format(hPaOfPressure));
        if (this.temperature != null)
            TextView_temp.setText(df2.format(cTemperature));
        else
            TextView_temp.setText("N/A");
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onResume() {
        // Register a listener for the sensor.
        super.onResume();
        sensorManager.registerListener(this, pressure, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, temperature, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        // Be sure to unregister the sensor when the activity pauses.
        super.onPause();
        sensorManager.unregisterListener(this); // unregister all sensors
    }
}