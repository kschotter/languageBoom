package com.wordpress.thebomby;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.wordpress.thebomby.device.Bomb;
import com.wordpress.thebomby.device.MockBomb;

import java.util.Timer;
import java.util.TimerTask;

public class GameActivity extends Activity {

    GameState state = new GameState();
    Bomb bomb = new MockBomb();
    TextView wordText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        wordText = (TextView) findViewById(R.id.wordText);
        updateWord();
        startBomb();
    }

    private void updateWord() {
        wordText.setText(state.nextWord());
    }

    private void startBomb() {
        Timer timer = new Timer();

        TimerTask t = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        boolean explode = Math.random() > 0.7;
                        boolean nextWord = Math.random() > 0.5;
                        if(explode) {
                            //explode();
                        }
                        if(nextWord) {
                            updateWord();
                        }

                    }
                });
            }
        };
        timer.scheduleAtFixedRate(t, 1000L, 1000L);
    }
}
