package com.example.tomaszvolanek.tamz_project_com;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends Activity {

    private Button settingsButton;
    private Button highScoreButton;
    private Button playButton;

    private View mainActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);


        initializeScreen();


    }

    public void initializeScreen() {

        playButton = findViewById(R.id.button);
        highScoreButton = findViewById(R.id.button2);
        settingsButton = findViewById(R.id.button3);

        //assigning text to buttons
        playButton.setText("PLAY");
        highScoreButton.setText("HIGH SCORE");
        settingsButton.setText("SETTINGS");


        //assigning listeners to buttons
        playButton.setOnTouchListener(playTouchListener);
        settingsButton.setOnTouchListener(settingsTouchListener);

        //visual design of the buttons
        playButton.setBackgroundColor(Color.rgb(100,55, 100));
        playButton.setAlpha(0.75f);
        highScoreButton.setBackgroundColor(Color.rgb(100,55, 100));
        highScoreButton.setAlpha(0.75f);
        settingsButton.setBackgroundColor(Color.rgb(100,55, 100));
        settingsButton.setAlpha(0.75f);



        mainActivity = findViewById(R.id.mainAct);
        mainActivity.setBackgroundResource(R.drawable.init_screen);


    }

    Button.OnTouchListener playTouchListener = new Button.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch(event.getAction())
            {
                case MotionEvent.ACTION_DOWN:
                {
                    Intent intent = new Intent(getApplicationContext(), PlayActivity.class);
                    startActivity(intent);
                    break;
                }

            }
            return false;
        }
    };
    Button.OnTouchListener settingsTouchListener = new Button.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch(event.getAction())
            {
                case MotionEvent.ACTION_DOWN:
                {
                    Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
                    startActivity(intent);
                    break;
                }

            }
            return false;
        }
    };
}
