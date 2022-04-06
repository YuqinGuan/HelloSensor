package com.MAMN01AndoridApp.hellosensor;

import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class MainActivity extends AppCompatActivity {

    private Button compassButton;
    private Button accButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        compassButton = (Button) findViewById(R.id.compass_button);
        compassButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                compass(v);
            }
        });
        accButton = (Button) findViewById(R.id.accelerometer_button);
        accButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                accelerometer(v);
            }
        });
    }

    /** Called when the user taps the Compass button */
    public void compass(View view){
        Intent intent = new Intent(this, CompassActivity.class);
        startActivity(intent);
    }

    /** Called when the user taps the Direction button */
    public void accelerometer(View view){
        Intent intent = new Intent(this, AccelerometerActivity.class);
        startActivity(intent);
    }

}