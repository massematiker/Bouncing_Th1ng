package com.example.krebs.bouncing_th1ng;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

public class HauptmenueActivity extends Activity {
    String name;
    EditText nameinput;

    private SharedPreferences gamePrefs;
    public static final String GAME_PREFS = "ArithmeticFile";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hauptmenue);
        configurenewgamebuttonhaupt();
        configurehighscorebutton();



    }
    private void configurenewgamebuttonhaupt(){
        ImageButton newgameButton = (ImageButton) findViewById(R.id.newgamebuttonhaupt);
        newgameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nameinput = (EditText) findViewById(R.id.nameinput);
                name = nameinput.getText().toString();
                startActivity(new Intent(HauptmenueActivity.this, BouncingGame.class));

                // get The Name and save
                gamePrefs = getSharedPreferences(GAME_PREFS, 0);
                SharedPreferences.Editor nameEdit = gamePrefs.edit();

                nameEdit.putString("name",name);
                nameEdit.commit();

            }
        });
    }

    private void  configurehighscorebutton(){
        ImageButton highscorebutton = (ImageButton ) findViewById(R.id.highscorebutton);
        highscorebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HauptmenueActivity.this, HighscoreActivity.class));
            }
        });
    }

}
