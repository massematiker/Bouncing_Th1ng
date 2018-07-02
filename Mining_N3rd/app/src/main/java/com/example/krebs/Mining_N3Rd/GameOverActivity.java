package com.example.krebs.Mining_N3Rd;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;


import com.example.krebs.Mining_N3Rd.R;

public class GameOverActivity extends Activity {

    private SharedPreferences gamePrefs;
    public static final String GAME_PREFS = "ArithmeticFile";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);
        configureNewGameButton();
        configureMenuButton();
        configureHighScoreButton1();
        configureScoreView();

    }

    private void configureNewGameButton(){
        ImageButton newgameButton = (ImageButton) findViewById(R.id.newgamebutton);
        newgameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GameOverActivity.this, BouncingGame.class));
            }
        });
    }
    private void configureMenuButton(){
        ImageButton menuButton = (ImageButton) findViewById(R.id.menubutton);
        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GameOverActivity.this, HauptmenueActivity.class));
            }
        });
    }
    private void  configureHighScoreButton1(){
        ImageButton highscorebutton1 = (ImageButton) findViewById(R.id.highscorebutton1);
        highscorebutton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GameOverActivity.this, HighscoreActivity.class));
            }
        });
    }

    private void  configureScoreView(){
        TextView score = (TextView) findViewById(R.id.score);
        gamePrefs = getSharedPreferences(GAME_PREFS, 0);
        score.setText(String.valueOf(gamePrefs.getInt("currentScore",0)));

    }

}
