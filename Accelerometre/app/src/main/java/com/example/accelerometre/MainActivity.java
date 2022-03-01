package com.example.accelerometre;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    SensorManager sensorMgr;
    Sensor accSensor;
    ConstraintLayout layout;
    int green, black, red;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        layout = ((ConstraintLayout)findViewById(R.id.layout));
        sensorMgr = (SensorManager) getSystemService(SENSOR_SERVICE);
        accSensor = sensorMgr.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        green = Color.GREEN;
        black = Color.BLACK;
        red = Color.RED;

        Boolean supported = sensorMgr.registerListener((SensorEventListener) this,
                accSensor, SensorManager.SENSOR_DELAY_UI);
        sensorMgr.unregisterListener
                ((SensorEventListener) this, sensorMgr.getDefaultSensor(Sensor.TYPE_ACCELEROMETER));
        if (!supported) {
            ((TextView) findViewById(R.id.acc)).setText("Pas d’accelerometre");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorMgr.registerListener((SensorEventListener) this, accSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        float x, y, z;
        if (sensorEvent.sensor.getType() == accSensor.getType()) {
            x = sensorEvent.values[0];
            y = sensorEvent.values[1];
            z = sensorEvent.values[2];

            if (x<0.33 && y<0.33 && z<0.33)
                layout.setBackgroundColor(green);

            else if (x>0.66 && y>0.66 && z>0.66)
                layout.setBackgroundColor(red);
            
            else
                layout.setBackgroundColor(black);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
    }

    @Override
    protected void onPause() {
        sensorMgr.unregisterListener(this, accSensor);
        super.onPause();
    }
}
