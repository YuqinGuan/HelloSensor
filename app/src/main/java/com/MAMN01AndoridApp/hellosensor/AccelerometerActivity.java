package com.MAMN01AndoridApp.hellosensor;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

public class AccelerometerActivity extends AppCompatActivity implements SensorEventListener{
    SensorManager mSensorManager;
    Sensor mAccelerometer;
    private TextView outputX, outputY, outputZ,directionText;
    private String[] direction = {null, null};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accelerometer);

        outputX = findViewById(R.id.xAxis);
        outputY = findViewById(R.id.yAxis);
        outputZ = findViewById(R.id.zAxis);
        directionText = findViewById(R.id.directionText);


        mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_UI);


    }
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        Sensor mySensor = event.sensor;

        if(mySensor.getType() == Sensor.TYPE_ACCELEROMETER){
            float xValue = event.values[0];
            float yValue = event.values[1];
            float zValue = event.values[2];
            outputX.setText("X: " + Float.toString(xValue));
            outputY.setText("Y: " + Float.toString(yValue));
            outputZ.setText("Z: " + Float.toString(zValue));
            // Gives direction depending on x-axis value
            if(xValue < -0.5) {
                direction[0] = "Right";
            } else if (xValue > 0.5) {
                direction[0] = "Left";
            } else {
                direction[0] = null;
            }
            // Gives direction depending on y-axis value
            if(yValue < -0.5) {
                direction[1] = "Down";
            } else if (yValue > 0.5) {
                direction[1] = "Up";
            } else {
                direction[1] = null;
            }
            // Shows direction of phone in text and changes background color when flat
            if(direction[0] != null || direction[1] != null) {
                if(direction[0] != null && direction[1] != null) {
                    directionText.setText(direction[0] + " + " + direction[1]);
                } else if (direction[0] != null) {
                    directionText.setText(direction[0]);
                } else if (direction[1] != null) {
                    directionText.setText(direction[1]);
                }

                getWindow().getDecorView().setBackgroundColor(Color.WHITE); // Change background to white if previously green
            } else {
                directionText.setText("FLAT");
                getWindow().getDecorView().setBackgroundColor(Color.GREEN); // change background to green
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}