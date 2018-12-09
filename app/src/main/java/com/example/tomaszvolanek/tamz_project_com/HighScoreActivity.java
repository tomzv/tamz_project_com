package com.example.tomaszvolanek.tamz_project_com;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class HighScoreActivity extends Activity {

    private ListView simpleList;
    private SharedPreferences sharedPreferences;
    private View highScoreActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_score);

        simpleList = findViewById(R.id.scoreListView);

        sharedPreferences = getSharedPreferences("scorePref", Context.MODE_PRIVATE);
        Set<String> scoreSet = new HashSet<String>();
        scoreSet = sharedPreferences.getStringSet("highScore", new HashSet<String>());
        List<String> scores = new ArrayList<String>(scoreSet);
        scores.sort(new MyComparator());

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.listtextview, scores);
        simpleList.setAdapter(arrayAdapter);

        highScoreActivity = findViewById(R.id.highScoreAct);
        highScoreActivity.setBackgroundColor(Color.BLACK);

    }
    //my implementation of the Comparator class
    public class MyComparator implements Comparator<String> {

        @Override
        public int compare(String first, String second) {
            if(Integer.parseInt(first) > Integer.parseInt(second)) {
                return -1;
            } else if(Integer.parseInt(first) < Integer.parseInt(second)) {
                return 1;
            } else return 0;
        }
    }
}
