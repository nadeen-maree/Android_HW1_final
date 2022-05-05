package com.CATvsRAT;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

import com.google.gson.Gson;
import com.learntodroid.androidminesweeper.R;

import java.io.IOException;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    private String highScore;
    private Scores scores;
    private String value = "sensors";
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //start the game.
        scores = new Scores();
        loadHighScoresFromFile();
        try {
            saveHighScoresToFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        read();

        if(scores.getScores().size()!=0){
            if (!scores.getScores().get(scores.getScores().size() - 1).equals(highScore)) {
                scores.add(highScore);
            }
        }else{
            scores.add(highScore);
        }

        pref = getSharedPreferences("HIGH_SCORES", Context.MODE_PRIVATE);
        editor = pref.edit();

        Gson gson = new Gson();
        String json = gson.toJson(scores);
        editor.putString("scores", json);
        editor.commit();


        Button button =findViewById(R.id.startButton);
        Button highScores = (Button) findViewById(R.id.HighScores);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save();
                try {
                    saveHighScoresToFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                openGameActivity(value);
            }
        });

        RadioButton sensors = findViewById(R.id.sensorsRB);
        RadioButton buttons = findViewById(R.id.buttonsRB);

        sensors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                value = "sensors";
            }
        });
        buttons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                value = "buttons";
            }
        });

        highScores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {openHighScoresActivity();
            }
        });

    }

    private void save() {
        pref = getSharedPreferences("HIGH_SCORES", Context.MODE_PRIVATE);
        editor = pref.edit();
        editor.putString("HIGH_SCORE", highScore);
        editor.commit();
    }

    private void read() {
        pref = getSharedPreferences("HIGH_SCORES", Context.MODE_PRIVATE);
        highScore = pref.getString("HIGH_SCORE", "0");

    }

    public void openHighScoresActivity() {
        Intent intent = new Intent(this, HighScoresActivity.class);
        startActivity(intent);
    }
    public void openGameActivity(String val) {
        Intent intent = new Intent(this, GameActivity.class);
        editor.putString("OPTION", value);
        editor.commit();
        startActivity(intent);
    }


    private void saveHighScoresToFile() throws IOException {
        FileHelper fh = new FileHelper();
        String filePath = getFilesDir().getPath() + "/HighScoresFile/";
        String fileName = "hs.txt";
        fh.saveBoardJSONToFile(scores, filePath, fileName);
    }

    private void loadHighScoresFromFile() {
        FileHelper fh = new FileHelper();
        String filePath = getFilesDir().getPath() + "/HighScoresFile/";
        String fileName = "hs.txt";
        Scores scores = fh.readGameFromFile(filePath, fileName);
        if (scores != null)
            this.scores = scores;
    }
}

