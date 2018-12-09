package com.example.tomaszvolanek.tamz_project_com;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener2;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tomaszvolanek.tamz_project_com.GameObjects.Player;

public class PlayActivity extends Activity implements SensorEventListener2{

    protected GameSurfaceView gameView;
    private SensorManager sensorManager;
    private ConstraintLayout clayout;

    private TextView score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature((Window.FEATURE_NO_TITLE));
        super.onCreate(savedInstanceState);
        //only run in portrait mode
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //Binding the ACCELEROMETER
        sensorManager = (SensorManager) getSystemService(getApplicationContext().SENSOR_SERVICE);
        sensorManager
                .registerListener(this,
                        sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                        SensorManager.SENSOR_DELAY_GAME);
        //Fullscreen window
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN
                ,WindowManager.LayoutParams.FLAG_FULLSCREEN);


        gameView = new GameSurfaceView(this);
        gameView.setOnTouchListener(myTouchListener);
        setContentView(gameView);
        gameView.player = new Player(100, 100, BitmapFactory
                .decodeResource(this.getResources(), R.drawable.ship), 0, 0);


    }

    View.OnTouchListener myTouchListener = new ImageView.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            gameView.onTouch(event);
            return true;
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        gameView.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        gameView.pause();
    }

    @Override
    protected void onStop() {
        sensorManager.unregisterListener(this);
        super.onStop();

    }

    @Override
    public void onFlushCompleted(Sensor sensor) {

    }

    //Calls onSensorEvent from gameView if the accelerometer detetcs acceleration
    @Override
    public void onSensorChanged(SensorEvent event) {
        if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            gameView.onSensorEvent(event);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
