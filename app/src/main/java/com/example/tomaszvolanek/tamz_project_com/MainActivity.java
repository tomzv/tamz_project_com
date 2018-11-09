package com.example.tomaszvolanek.tamz_project_com;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        playButton = findViewById(R.id.button);
        highScoreButton = findViewById(R.id.button2);
        settingsButton = findViewById(R.id.button3);

        playButton.setText("PLAY");

        highScoreButton.setText("HIGH SCORE");

        settingsButton.setText("SETTINGS");

        playButton.setOnTouchListener(playTouchListener);
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

}
