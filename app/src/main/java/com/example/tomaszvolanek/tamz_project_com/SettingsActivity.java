package com.example.tomaszvolanek.tamz_project_com;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class SettingsActivity extends Activity {

    private SharedPreferences myPreferences;
    private SharedPreferences.Editor mySharedEditor;
    private RadioButton easy;
    private RadioButton medium;
    private RadioButton hard;
    private View settingsActivity;
    private TextView difficultyText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        //Setting the color of the background
        settingsActivity = findViewById(R.id.settingsAct);
        settingsActivity.setBackgroundColor(Color.BLACK);

        //Design of the radio buttons
        easy = findViewById(R.id.easy);
        medium = findViewById(R.id.medium);
        hard = findViewById(R.id.hard);

        easy.setText("EASY");
        easy.setTextColor(Color.WHITE);
        medium.setText("MEDIUM");
        medium.setTextColor(Color.WHITE);
        hard.setText("HARD");
        hard.setTextColor(Color.WHITE);

        //Making sure the radio button stays selected after the user quits
        myPreferences = getSharedPreferences("myPref", Context.MODE_PRIVATE);
        String difficulty = myPreferences.getString("difficulty", "easy");
        switch(difficulty){
            case("easy"):
                easy.setChecked(true);
                break;
            case("medium"):
                medium.setChecked(true);
                break;
            case("hard"):
                hard.setChecked(true);
                break;
        }

        //Text view appearance
        difficultyText = findViewById(R.id.textViewDifficulty);
        difficultyText.setText("CHOOSE FROM ONE OF THE DIFFICULTIES BELOW");
        difficultyText.setTextColor(Color.WHITE);

    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();
        mySharedEditor = myPreferences.edit();
        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.easy:
                if (checked) {
                    mySharedEditor.putString("difficulty", "easy");
                    mySharedEditor.apply();
                }
                    break;
            case R.id.medium:
                if (checked) {
                    mySharedEditor.putString("difficulty", "medium");
                    mySharedEditor.apply();
                }
                    break;
            case R.id.hard:
                if(checked) {
                    mySharedEditor.putString("difficulty", "hard");
                    mySharedEditor.apply();
                }
                break;
        }
    }
}
