package com.example.krebs.bouncing_th1ng;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;

public class HelpActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        configuregoplaybutton();
    }
    private void configuregoplaybutton(){

        ImageButton newgameButton = (ImageButton) findViewById(R.id.goplay);
        newgameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             startActivity(new Intent(HelpActivity.this, BouncingGame.class));
            }
        });
    }
}

