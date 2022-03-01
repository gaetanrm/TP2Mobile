package com.example.proximity;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener{

    SensorManager sensorMgr;
    Sensor proxySensor;
    ImageView near, away;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sensorMgr = (SensorManager) getSystemService(SENSOR_SERVICE);
        proxySensor = sensorMgr.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        near = (ImageView)findViewById(R.id.near);
        away = (ImageView)findViewById(R.id.away);

        Boolean supported = sensorMgr.registerListener((SensorEventListener) this,
                proxySensor, SensorManager.SENSOR_DELAY_UI);
        sensorMgr.unregisterListener
                ((SensorEventListener) this, proxySensor);
        if (!supported) {
            ((TextView)findViewById(R.id.nosensor)).setText("Pas de capteur de proximité");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorMgr.registerListener((SensorEventListener) this, proxySensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        sensorMgr.unregisterListener(this, proxySensor);
        super.onPause();
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        float dist;
        if (sensorEvent.sensor.getType() == proxySensor.getType()) {
            dist = sensorEvent.values[0];
            if (dist>0.1) {
                near.setVisibility(View.INVISIBLE);
                away.setVisibility(View.VISIBLE);
            }
            else {
                near.setVisibility(View.VISIBLE);
                away.setVisibility(View.INVISIBLE);
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}