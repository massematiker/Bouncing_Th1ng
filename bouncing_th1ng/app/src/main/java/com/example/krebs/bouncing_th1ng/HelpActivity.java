package com.example.krebs.bouncing_th1ng;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class HelpActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        configureGoPlayButton();
    }
    private void configureGoPlayButton(){

        ImageButton newGameButton = (ImageButton) findViewById(R.id.goplay);
        newGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             startActivity(new Intent(HelpActivity.this, BouncingGame.class));
            }
        });
    }
}

