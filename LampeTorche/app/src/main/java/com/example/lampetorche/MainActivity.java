package com.example.lampetorche;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener{

    SensorManager sensorMgr;
    Sensor gyroSensor;
    CameraManager torch;
    boolean on_off;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sensorMgr = (SensorManager) getSystemService(SENSOR_SERVICE);
        gyroSensor = sensorMgr.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        torch = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        on_off = false;

        Boolean supported = sensorMgr.registerListener((SensorEventListener) this,
                gyroSensor, SensorManager.SENSOR_DELAY_UI);
        sensorMgr.unregisterListener
                ((SensorEventListener) this, gyroSensor);
        if (!supported) {
            ((TextView)findViewById(R.id.nosensor)).setText("Pas de gyroscope");
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        sensorMgr.registerListener((SensorEventListener) this, gyroSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        sensorMgr.unregisterListener(this, gyroSensor);
        super.onPause();
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        float x, y, z;
        if (sensorEvent.sensor.getType() == gyroSensor.getType()) {
            x = sensorEvent.values[0];
            y = sensorEvent.values[1];
            z = sensorEvent.values[2];

            if (x>2 || y>2 || z>2){
                if (on_off)
                    on_off = turnOff(on_off);
                else
                    on_off = turnOn(on_off);
            }


        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    public boolean turnOn(boolean on_off){
        String cameraId = null;
        try {
            cameraId = torch.getCameraIdList()[0];
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                torch.setTorchMode(cameraId, true);
            }
            on_off = true;
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
        return on_off;
    }

    public boolean turnOff(boolean on_off){
        String cameraId = null;
        try {
            cameraId = torch.getCameraIdList()[0];
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                torch.setTorchMode(cameraId, false);
            }
            on_off = false;
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
        return on_off;
    }
}