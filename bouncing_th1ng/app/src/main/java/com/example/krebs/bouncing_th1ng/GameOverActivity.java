package com.example.krebs.bouncing_th1ng;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class GameOverActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);
        configurenewgamebutton();
        configuremenubutton();
    }

    private void configurenewgamebutton(){
        Button newgameButton = (Button) findViewById(R.id.newgamebutton);
        newgameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GameOverActivity.this, BouncingGame.class));
            }
        });
    }
    private void configuremenubutton(){
        Button menuButton = (Button) findViewById(R.id.menubutton);
        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GameOverActivity.this, HauptmenueActivity.class));
            }
        });
    }
}
