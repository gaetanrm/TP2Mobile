package com.example.indispocapteurs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private SensorManager mSensorManager;

    TextView sensor1, sensor2, sensor3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sensor1 = (TextView)findViewById(R.id.textView);
        sensor2 = (TextView)findViewById(R.id.textView2);
        sensor3 = (TextView)findViewById(R.id.textView3);

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        if (mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD) !=null){
            sensor1.setText("Capteur magnétic présent sur ce smartphone");
            sensor1.setTextColor(Color.BLUE);
        }
        else {
            sensor1.setText("Capteur magnétic non présent sur ce smartphone");
            sensor1.setTextColor(Color.RED);
        }

        if (mSensorManager.getDefaultSensor(Sensor.TYPE_GEOMAGNETIC_ROTATION_VECTOR) !=null){
            sensor2.setText("Capteur de rotation présent sur ce smartphone");
            sensor2.setTextColor(Color.BLUE);
        }
        else {
            sensor2.setText("Capteur de rotation non présent sur ce smartphone");
            sensor2.setTextColor(Color.RED);
        }
        if (mSensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE) !=null){
            sensor3.setText("Capteur de température présent sur ce smartphone");
            sensor3.setTextColor(Color.BLUE);
        }
        else {
            sensor3.setText("Capteur de température non présent \nsur ce smartphone");
            sensor3.setTextColor(Color.RED);
        }
    }
}