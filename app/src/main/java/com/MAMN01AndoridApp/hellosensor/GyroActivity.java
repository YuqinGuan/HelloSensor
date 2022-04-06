package com.MAMN01AndoridApp.hellosensor;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

public class GyroActivity extends AppCompatActivity implements SensorEventListener {

    // device sensor manager
    private SensorManager SensorManage;

    private Sensor mGyro;
    private TextView mheading,  mpitch,  mroll;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gyro);

        SensorManage = (SensorManager) getSystemService(SENSOR_SERVICE);
        mGyro = SensorManage.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        SensorManage.registerListener(this, mGyro, SensorManager.SENSOR_DELAY_UI);
        mheading = findViewById(R.id.heading);
        mpitch = findViewById(R.id.pitch);
        mroll = findViewById(R.id.roll);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // to stop the listener and save battery
        SensorManage.unregisterListener(this);
    }
    @Override
    protected void onResume() {
        super.onResume();
        SensorManage.registerListener(this, mGyro, SensorManager.SENSOR_DELAY_UI);
    }
    @Override
    public void onSensorChanged(SensorEvent event) {

        if(event.sensor.getType() == Sensor.TYPE_GYROSCOPE){
            updateOrientation(event.values[0], event.values[1], event.values[2]);
        }
    }



    private void updateOrientation(float heading, float pitch, float roll) {
        // Update the UI
        mheading.setText("Heading "+Float.toString(heading));
        mpitch.setText("Pitch " +Float.toString(pitch));
        mroll.setText("Roll "+Float.toString(roll));

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // not in use
    }
}