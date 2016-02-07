package com.wordpress.thebomby;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wordpress.thebomby.device.BombAccessor;
import com.wordpress.thebomby.device.BombListener;

import java.util.Random;

public class GameActivity extends Activity implements BombListener {

    GameState state;
    TextView wordText;

    RelativeLayout restartView;

    TextView skipButton;
    ImageView checkWhite;
    ImageView checkBlue;

    private ImageView[] explosion = new ImageView[7];
    Handler timerHandler;
    Handler animationHandler;
    Handler nextHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        String language = getIntent().getStringExtra(MainActivity.KEY_LANGUAGE);
        state = new GameState(language);

        wordText = (TextView) findViewById(R.id.wordText);
        restartView = (RelativeLayout) findViewById(R.id.restartView);

        skipButton = (TextView) findViewById(R.id.skipButton);

        explosion[0] = (ImageView)findViewById(R.id.vaike_pauk);
        explosion[1] = (ImageView)findViewById(R.id.keskmine_pauk);
        explosion[2] = (ImageView)findViewById(R.id.suur_pauk);
        explosion[3] = (ImageView)findViewById(R.id.suur_pauk);
        explosion[4] = (ImageView)findViewById(R.id.keskmine_pauk);
        explosion[5] = (ImageView)findViewById(R.id.vaike_pauk);
        explosion[6] = (ImageView)findViewById(R.id.restart);

        checkWhite = (ImageView)findViewById(R.id.checkWhite);
        checkBlue = (ImageView)findViewById(R.id.checkBlue);

        checkWhite.setVisibility(View.VISIBLE);
        checkBlue.setVisibility(View.INVISIBLE);

        startBomb();
        updateWord();
        startTimer();
    }

    private void updateWord() {
        String word = state.nextWord();
        wordText.setText(word);
        BombAccessor.getBomb().showWord(word);
    }

    private void startBomb() {
        BombAccessor.getBomb().start(this);
    }

    @Override
    public void nextWord() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                updateWord();
                checkBlue.setVisibility(View.VISIBLE);
            }
        });
        nextHandler = new Handler();
        nextHandler.postDelayed(nextRunnable, 500);
    }

    @Override
    public void skipWord() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                updateWord();
                skipButton.setTextColor(0xAAAAAAAA);
            }
        });
        nextHandler = new Handler();
        nextHandler.postDelayed(skipRunnable, 500);
    }

    public void startTimer() {
        timerHandler = new Handler();
        timerHandler.postDelayed(timerRunnable, 30000L);
    }

    private Runnable timerRunnable = new Runnable() {

        @Override
        public void run() {
            BombAccessor.getBomb().timerDone();
            wordText.setVisibility(View.GONE);
            checkBlue.setVisibility(View.GONE);
            checkWhite.setVisibility(View.GONE);
            skipButton.setVisibility(View.GONE);

            animateExplosion();
            timerHandler.removeCallbacks(timerRunnable);

        }
    };

    Runnable skipRunnable = new Runnable() {
        @Override
        public void run() {
            skipButton.setTextColor(0xFF1ABAF0);
            timerHandler.removeCallbacks(skipRunnable);
        }
    };

    Runnable nextRunnable = new Runnable() {
        @Override
        public void run() {
            checkBlue.setVisibility(View.INVISIBLE);
            timerHandler.removeCallbacks(nextRunnable);

        }
    };

    private void animateExplosion() {
        restartView.setVisibility(View.VISIBLE);

        animationHandler = new Handler();
        animationHandler.postDelayed(animationRunnable, 100);
    }

    Runnable animationRunnable = new Runnable() {

        int currentFrame = 0;

        @Override
        public void run() {
            int visibility = explosion[currentFrame].getVisibility();
            int flippedVisibility = visibility == View.VISIBLE ? View.INVISIBLE : View.VISIBLE;
            explosion[currentFrame++].setVisibility(flippedVisibility);
            if(currentFrame < explosion.length -1) {
                animationHandler.postDelayed(animationRunnable, 100);
            } else {
                restartView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                        restartView.setVisibility(View.INVISIBLE);
                    }
                });
            }
        }
    };
}