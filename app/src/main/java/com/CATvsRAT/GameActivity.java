package com.CATvsRAT;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.learntodroid.androidminesweeper.R;

public class GameActivity extends AppCompatActivity implements SensorEventListener {
    public static final long TIMER_LENGTH = 999000L;    // 999 seconds in milliseconds
    public int size_x = 7;
    public int size_y = 5;
    private int highScore = 0,counter = 0,secondsElapsed;

    private final static String TAG = "SENSOR_SERVICE";
    private String value;

    private boolean isLocked = false;
    private float firstX = 0, firstY = 0, firstZ = 0;
    private final double THRESHOLD = 3;

    private GameGridRecyclerAdapter gameGridRecyclerAdapter;
    private TextView timer;
    private HunterGame hunterGame;
    private ImageButton up, down, left, right;
    private Button btn;
    private TextView heart1, heart2, heart3;
    private CountDownTimer countDownTimer;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private SensorManager sensorManager;
    private Sensor accelerometer;
    private MediaPlayer eatCheese;
    private MediaPlayer eatMouse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        read();

        eatCheese = MediaPlayer.create(GameActivity.this, R.raw.bite);
        eatMouse = MediaPlayer.create(GameActivity.this, R.raw.mouse);

        btn = findViewById(R.id.btn);
        up =  findViewById(R.id.arrow_up_btn);
        down =  findViewById(R.id.arrow_down_btn);
        left =  findViewById(R.id.arrow_left_btn);
        right =  findViewById(R.id.arrow_right_btn);
        heart1 =  findViewById(R.id.heart1);
        heart2 =  findViewById(R.id.heart2);
        heart3 =  findViewById(R.id.heart3);

        if(value.equals("sensors")){
            btn.setVisibility(View.INVISIBLE);
            right.setVisibility(View.INVISIBLE);
            left.setVisibility(View.INVISIBLE);
            down.setVisibility(View.INVISIBLE);
            up.setVisibility(View.INVISIBLE);
        }

        //build the grid with the wanted size.
        RecyclerView grid = findViewById(R.id.activity_main_grid);
        grid.setLayoutManager(new GridLayoutManager(this, size_y));

        hunterGame = new HunterGame(size_x, size_y);
        gameGridRecyclerAdapter = new GameGridRecyclerAdapter(hunterGame.getGameGrid().getCells());
        grid.setAdapter(gameGridRecyclerAdapter);

        if(!value.equals("buttons")) {
            sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
            accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            sensorManager.registerListener(GameActivity.this, accelerometer, sensorManager.SENSOR_DELAY_NORMAL);
        }

        timer = findViewById(R.id.activity_main_timer);
        timer.setTextColor(Color.parseColor("#000000")); // timer color
        countDownTimer = new CountDownTimer(TIMER_LENGTH, 1000) {
            @SuppressLint("LongLogTag")
            public void onTick(long millisUntilFinished) {// increase the counter on each tivk.
                counter++;

                if( secondsElapsed%2==0){
                    if(hunterGame.getGameGrid().randomCatMove().equals("CAT"))
                        eatMouse.start();
                }else{
                    if(hunterGame.getGameGrid().catDown().equals("CAT"))
                        eatMouse.start();
                }
                if(secondsElapsed%4==0){
                    hunterGame.getGameGrid().randomCheeseMove();
                }
                gameGridRecyclerAdapter.setCells(hunterGame.getGameGrid().getCells());
                secondsElapsed += 1;
                timer.setText(String.format("%03d", secondsElapsed + hunterGame.getGameGrid().getCheeseScore()));
                updateHearts(hunterGame,heart1,heart2,heart3);

                if(hunterGame.getGameGrid().getHearts()==0){
                    //restartTheGame();
                    openGameOverActivity();
                }

            }

            public void onFinish() {
                Toast.makeText(getApplicationContext(), "Game Over: Timer Expired", Toast.LENGTH_SHORT).show();
                gameGridRecyclerAdapter.setCells(HunterGame.getGameGrid().getCells());
            }
        };
        countDownTimer.start();

        up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String check = hunterGame.getGameGrid().up();
                makeSound(check);
                gameGridRecyclerAdapter.setCells(hunterGame.getGameGrid().getCells());
                updateHearts(hunterGame,heart1,heart2,heart3);

            }
        });
        down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String check = hunterGame.getGameGrid().down();
                makeSound(check);
                gameGridRecyclerAdapter.setCells(hunterGame.getGameGrid().getCells());
                updateHearts(hunterGame,heart1,heart2,heart3);

            }
        });
        left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String check = hunterGame.getGameGrid().left();
                makeSound(check);
                gameGridRecyclerAdapter.setCells(hunterGame.getGameGrid().getCells());
                updateHearts(hunterGame,heart1,heart2,heart3);

            }
        });
        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String check = hunterGame.getGameGrid().right();
                makeSound(check);
                gameGridRecyclerAdapter.setCells(hunterGame.getGameGrid().getCells());
                updateHearts(hunterGame,heart1,heart2,heart3);
            }
        });
    }

    public void updateHearts(HunterGame hunterGame,TextView heart1,TextView heart2,TextView heart3){
        switch(hunterGame.getGameGrid().getHearts()){

            case 2:
                heart3.setText("");
                break;
            case 1:
                heart2.setText("");
                break;
            case 0:
                heart1.setText("");
                break;
            default:
                break;
        }
    }

    public void restartTheGame(){
            hunterGame = new HunterGame(size_x, size_y);
            gameGridRecyclerAdapter.setCells(hunterGame.getGameGrid().getCells());
            secondsElapsed = 0;
            heart1.setText(R.string.heart);
            heart2.setText(R.string.heart);
            heart3.setText(R.string.heart);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        if (isLocked == false) {
            isLocked = true;
            firstX = event.values[0];
            firstY = event.values[1];
            firstZ = event.values[2];
        } else {
            float absDiffX = firstX - event.values[0];
            float absDiffY = firstY - event.values[1];


            if (absDiffX > THRESHOLD) {// aa hay ho
                String check = hunterGame.getGameGrid().right();
                makeSound(check);
            }
            if (absDiffX < 0 - THRESHOLD) {
                String check = hunterGame.getGameGrid().left();
                makeSound(check);
            }
            if (absDiffY > THRESHOLD) {
                String check = hunterGame.getGameGrid().up();
                makeSound(check);
            }

            if (absDiffY < 0 - THRESHOLD) {
                String check = hunterGame.getGameGrid().down();
                makeSound(check);
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    public void makeSound(String check){
        if(check.equals("CAT")) {
            eatMouse.start();
        }
        else{
            if(check.equals("RAT"))
                eatCheese.start();
        }
    }


    public void openGameOverActivity() {

        Intent intent = new Intent(this, GameOverActivity.class);

        counter+= hunterGame.getGameGrid().getCheeseScore();
        if (highScore < counter)
            highScore = counter;

        countDownTimer.cancel();
        save();
        startActivity(intent);
    }


    private void save() {

        pref = getSharedPreferences("HIGH_SCORES", Context.MODE_PRIVATE);
        editor = pref.edit();
        editor.putString("HIGH_SCORE", "" + highScore);
        editor.putString("CURRENT_SCORE", "" + counter);
        editor.commit();

    }

    private void read() {

        pref = getSharedPreferences("HIGH_SCORES", Context.MODE_PRIVATE);
        highScore = Integer.parseInt(pref.getString("HIGH_SCORE", "0"));
        value = pref.getString("OPTION","sensors");
    }
}
