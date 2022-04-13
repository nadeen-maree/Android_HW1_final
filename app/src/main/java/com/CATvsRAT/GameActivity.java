package com.CATvsRAT;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.learntodroid.androidminesweeper.R;

public class GameActivity extends AppCompatActivity {
    public static final long TIMER_LENGTH = 999000L;    // 999 seconds in milliseconds
    public int size_x = 5;
    public int size_y = 3;

    private GameGridRecyclerAdapter gameGridRecyclerAdapter;
    private TextView timer;
    private int secondsElapsed;
    private HunterGame hunterGame;
    private ImageButton up;
    private ImageButton down;
    private ImageButton left;
    private ImageButton right;
    private TextView heart1;
    private TextView heart2;
    private TextView heart3;
    private CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        up =  findViewById(R.id.arrow_up_btn);
        down =  findViewById(R.id.arrow_down_btn);
        left =  findViewById(R.id.arrow_left_btn);
        right =  findViewById(R.id.arrow_right_btn);
        heart1 =  findViewById(R.id.heart1);
        heart2 =  findViewById(R.id.heart2);
        heart3 =  findViewById(R.id.heart3);

        //build the grid with the wanted size.
        RecyclerView grid = findViewById(R.id.activity_main_grid);
        grid.setLayoutManager(new GridLayoutManager(this, size_y));

        hunterGame = new HunterGame(size_x, size_y);
        gameGridRecyclerAdapter = new GameGridRecyclerAdapter(hunterGame.getGameGrid().getCells());
        grid.setAdapter(gameGridRecyclerAdapter);

        timer = findViewById(R.id.activity_main_timer);
        timer.setTextColor(Color.parseColor("#000000")); // timer color
        countDownTimer = new CountDownTimer(TIMER_LENGTH, 1000) {
            @SuppressLint("LongLogTag")
            public void onTick(long millisUntilFinished) {// increase the counter on each tivk.

                if( secondsElapsed%2==0){
                    hunterGame.getGameGrid().randomCatMove();
                }else{
                    hunterGame.getGameGrid().catDown();
                }
                gameGridRecyclerAdapter.setCells(hunterGame.getGameGrid().getCells());
                secondsElapsed += 1;
                timer.setText(String.format("%03d", secondsElapsed));
                updateHearts(hunterGame,heart1,heart2,heart3);

                if(hunterGame.getGameGrid().getHearts()==0){
                    restartTheGame();
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
                hunterGame.getGameGrid().up();
                gameGridRecyclerAdapter.setCells(hunterGame.getGameGrid().getCells());
                updateHearts(hunterGame,heart1,heart2,heart3);

            }
        });
        down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hunterGame.getGameGrid().down();
                gameGridRecyclerAdapter.setCells(hunterGame.getGameGrid().getCells());
                updateHearts(hunterGame,heart1,heart2,heart3);

            }
        });
        left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hunterGame.getGameGrid().left();
                gameGridRecyclerAdapter.setCells(hunterGame.getGameGrid().getCells());
                updateHearts(hunterGame,heart1,heart2,heart3);

            }
        });
        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hunterGame.getGameGrid().right();
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
}
