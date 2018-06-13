package com.example.krebs.bouncing_th1ng;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;

public class HauptmenueActivity extends Activity {
    private String name;
    private EditText nameInput;
    private boolean playTouch;
    private boolean helpMenu;
    private CheckBox checkBoxTouch;
    private CheckBox checkBoxHelp;

    private SharedPreferences gamePrefs;
    public static final String GAME_PREFS = "ArithmeticFile";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hauptmenue);
        configureNewGameButtonMain();
        ConfigureHighScoreButton();

        gamePrefs = getSharedPreferences(GAME_PREFS, 0);
        SharedPreferences.Editor nameEdit = gamePrefs.edit();
        nameInput = (EditText) findViewById(R.id.nameinput);
        nameInput.setText(gamePrefs.getString("name","name"));


    }

    /**
     * here is configured what happens if the NewGameButton is pressed
     */
    private void configureNewGameButtonMain(){

        ImageButton newgameButton = (ImageButton) findViewById(R.id.newgamebuttonhaupt);
        newgameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //writes the checkboxes to a boolean
                checkBoxTouch = (CheckBox) findViewById(R.id.cbTouch);
                playTouch = checkBoxTouch.isChecked();
                checkBoxHelp = (CheckBox) findViewById(R.id.cbHilfeScreen);
                helpMenu = checkBoxHelp.isChecked();


                nameInput = (EditText) findViewById(R.id.nameinput);
                name = nameInput.getText().toString();


                // get The Name and save
                gamePrefs = getSharedPreferences(GAME_PREFS, 0);
                SharedPreferences.Editor nameEdit = gamePrefs.edit();

                nameEdit.putString("name",name);
                nameEdit.putBoolean("touch",playTouch);
                nameEdit.commit();


                if (helpMenu) startActivity(new Intent(HauptmenueActivity.this, HelpActivity.class));
                else startActivity(new Intent(HauptmenueActivity.this, BouncingGame.class));
            }
        });
    }
    /**
     * here is configured what happens if the HighScoreButton is pressed
     */
    private void  ConfigureHighScoreButton(){
        ImageButton highscorebutton = (ImageButton ) findViewById(R.id.highscorebutton);
        highscorebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HauptmenueActivity.this, HighscoreActivity.class));
            }
        });
    }

}