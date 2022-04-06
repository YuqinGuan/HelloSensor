package com.MAMN01AndoridApp.hellosensor;

import android.support.v7.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.VibrationEffect;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.hardware.SensorEvent;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.hardware.SensorEventListener;
import android.os.Vibrator;
import android.os.Bundle;
import android.widget.Button;

public class CompassActivity extends AppCompatActivity implements SensorEventListener {

    // device sensor manager
    private SensorManager SensorManage;
    // define the compass picture that will be use
    private ImageView compassimage;
    // record the angle turned of the compass picture
    private float DegreeStart = 0f;
    private Sensor mGyro;
    private TextView mheading,  mpitch,  mroll;
    TextView DegreeTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compass);

        //
        compassimage = (ImageView) findViewById(R.id.compass_image);
        // TextView that will display the degree
        DegreeTV = (TextView) findViewById(R.id.DegreeTV);
        // initialize your android device sensor capabilities
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
        // code for system's orientation sensor registered listeners
        SensorManage.registerListener(this, SensorManage.getDefaultSensor(Sensor.TYPE_ORIENTATION),
                SensorManager.SENSOR_DELAY_GAME);
        SensorManage.registerListener(this, mGyro, SensorManager.SENSOR_DELAY_UI);
    }
    @Override
    public void onSensorChanged(SensorEvent event) {
        if(event.sensor.getType() == Sensor.TYPE_ORIENTATION) {
            // get angle around the z-axis rotated
            float degree = Math.round(event.values[0]);
            DegreeTV.setText("Heading: " + Float.toString(degree) + " degrees");
            // rotation animation - reverse turn degree degrees
            RotateAnimation ra = new RotateAnimation(
                    DegreeStart,
                    -degree,
                    Animation.RELATIVE_TO_SELF, 0.5f,
                    Animation.RELATIVE_TO_SELF, 0.5f);
            // set the compass animation after the end of the reservation status
            ra.setFillAfter(true);
            // set how long the animation for the compass image will take place
            ra.setDuration(210);
            // Start animation of compass image
            compassimage.startAnimation(ra);
            DegreeStart = -degree;

            if (degree >= 355 || degree <= 5) {
                Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                // Vibrate for 50 milliseconds
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    v.vibrate(VibrationEffect.createOneShot(50, VibrationEffect.DEFAULT_AMPLITUDE));
                } else {
                    //deprecated in API 26
                    v.vibrate(50);
                    return;
                }
            }
        }
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