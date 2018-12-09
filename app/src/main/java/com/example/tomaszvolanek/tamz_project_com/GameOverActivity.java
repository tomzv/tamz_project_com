package com.example.tomaszvolanek.tamz_project_com;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class GameOverActivity extends Activity {

    private Button playAgain;
    private Button menu;
    private TextView text;
    private View gameOverActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        playAgain = findViewById(R.id.playAgain);
        menu = findViewById(R.id.menu);
        text = findViewById(R.id.gameOverText);

        //assigning text to buttons
        playAgain.setText("PLAY AGAIN");
        menu.setText("MENU");
        text.setText("GAME OVER");
        text.setTextColor(Color.WHITE);

        //assigning listeners to buttons
        playAgain.setOnTouchListener(playAgainTouchListener);
        menu.setOnTouchListener(menuTouchListener);

        //visual design of the buttons
        playAgain.setBackgroundColor(Color.rgb(100,55, 100));
        playAgain.setAlpha(0.75f);
        menu.setBackgroundColor(Color.rgb(100,55, 100));
        menu.setAlpha(0.75f);


        gameOverActivity = findViewById(R.id.gameOverAct);
        gameOverActivity.setBackgroundColor(Color.BLACK);
    }
    Button.OnTouchListener menuTouchListener = new Button.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch(event.getAction())
            {
                case MotionEvent.ACTION_DOWN:
                {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    break;
                }

            }
            return false;
        }
    };
    Button.OnTouchListener playAgainTouchListener = new Button.OnTouchListener() {
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
