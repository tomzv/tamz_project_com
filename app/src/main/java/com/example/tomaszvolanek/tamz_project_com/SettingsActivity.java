package com.example.tomaszvolanek.tamz_project_com;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class SettingsActivity extends Activity {

    private SharedPreferences myPreferences;
    private SharedPreferences.Editor mySharedEditor;
    private RadioGroup radioButtonGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        myPreferences = getSharedPreferences("myPref", Context.MODE_PRIVATE);
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
