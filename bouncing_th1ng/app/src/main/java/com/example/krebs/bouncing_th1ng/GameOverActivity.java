package com.example.krebs.bouncing_th1ng;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class GameOverActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);
        configureNewGameButton();
        configureMenuButton();
        configureHighScoreButton1();
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

}
